package org.igye.android.gamepad

import kotlinx.coroutines.*
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_A
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_B
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_DOWN
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_LEFT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_LEFT_SHIFT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_RIGHT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_RIGHT_SHIFT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_SELECT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_START
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_UP
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_X
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_Y
import org.igye.android.gamepad.UserInput.*
import java.io.Closeable
import java.util.concurrent.ConcurrentLinkedQueue

class Code4ControllerEventListener(
    private val userInputListener: suspend (UserInput) -> Unit,
    private val gameSounds: GameSoundsI,
    private val singleThreadContext: CoroutineDispatcher = newSingleThreadContext("Code4ControllerEventListener"),
): Closeable, ControllerEventListener {
    private val controllerEvents = ConcurrentLinkedQueue<ControllerEvent>()
    private var currNode: Code4TreeNode = Code4.root
    private var isBlocked = false
    private val unblockedSound = gameSounds.on_next
    private val blockedSound = gameSounds.on_go_to_end

    override suspend fun onControllerEvent(controllerEvent: ControllerEvent): Unit = coroutineScope {
        launch {
            controllerEvents.add(controllerEvent)
            processControllerEvents()
        }
    }

    private suspend fun processControllerEvents() = withContext(singleThreadContext) {
        while (controllerEvents.isNotEmpty() && isActive) {
            val keyCode = getMin(controllerEvents).keyCode
            if (keyCode == GAMEPAD_BUTTON_START) {
                unblock()
            } else if (isBlocked) {
                gameSounds.play(blockedSound)
            } else if (keyCode == GAMEPAD_BUTTON_X || keyCode == GAMEPAD_BUTTON_B || keyCode == GAMEPAD_BUTTON_A || keyCode == GAMEPAD_BUTTON_Y) {
                nextNode(keyCode)
                if (currNode.userInput != null) {
                    selectCurrNode()
                }
            } else if (currNode !== Code4.root) {
                block()
            } else {
                var userInput = keyCodeToUserInput(keyCode)
                if (userInput == null) {
                    block()
                } else {
                    userInputListener(userInput)
                }
            }
        }
    }

    private suspend fun block() {
        isBlocked = true
        gameSounds.play(blockedSound)
    }

    private suspend fun unblock() {
        isBlocked = false
        currNode = Code4.root
        gameSounds.play(unblockedSound)
    }

    private fun keyCodeToUserInput(keyCode: Int): UserInput? {
        return when (keyCode) {
            GAMEPAD_BUTTON_LEFT -> LEFT
            GAMEPAD_BUTTON_RIGHT -> RIGHT
            GAMEPAD_BUTTON_UP -> UP
            GAMEPAD_BUTTON_DOWN -> DOWN
            GAMEPAD_BUTTON_LEFT_SHIFT -> ESCAPE
            GAMEPAD_BUTTON_RIGHT_SHIFT -> ENTER
            GAMEPAD_BUTTON_SELECT -> SELECT
            else -> null
        }
    }

    private suspend fun nextNode(keyCode: Int) {
        val newNode = if (keyCode == GAMEPAD_BUTTON_B) {
            currNode.child0
        } else if (keyCode == GAMEPAD_BUTTON_A) {
            currNode.child1
        } else if (keyCode == GAMEPAD_BUTTON_X) {
            currNode.child2
        } else {
            currNode.child3
        }
        if (newNode == null) {
            block()
        } else {
            currNode = newNode
        }
    }

    private suspend fun selectCurrNode() {
        val selectedInput = currNode.userInput
        currNode = Code4.root
        if (selectedInput == null) {
            block()
        } else {
            userInputListener(selectedInput)
        }
    }

    private fun getMin(controllerEvents: ConcurrentLinkedQueue<ControllerEvent>): ControllerEvent {
        if (controllerEvents.size == 1) {
            return controllerEvents.remove()
        } else {
            var result:ControllerEvent? = null
            for (event in controllerEvents) {
                if (result == null || event.eventTime < result.eventTime) {
                    result = event
                }
            }
            controllerEvents.remove(result)
            return result!!
        }
    }

    override fun close() {
        (singleThreadContext as ExecutorCoroutineDispatcher).close()
    }
}