package org.igye.android.gamepad

import kotlinx.coroutines.*
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_A
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_B
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_DOWN
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_LEFT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_LEFT_SHIFT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_RIGHT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_RIGHT_SHIFT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_START
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_UP
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_X
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_Y
import org.igye.android.gamepad.UserInput.*
import java.io.Closeable
import java.util.concurrent.ConcurrentLinkedQueue

class ControllerEventListener(
    private val userInputListener: suspend (UserInput) -> Unit,
    private val gameSounds: GameSoundsI,
    private val singleThreadContext: CoroutineDispatcher = newSingleThreadContext("ControllerEventListenerContext"),
): Closeable {
    private val controllerEvents = ConcurrentLinkedQueue<ControllerEvent>()
    private var morseNode: MorseTreeNode = Morse.root

    suspend fun onControllerEvent(controllerEvent: ControllerEvent): Unit = coroutineScope {
        launch {
            controllerEvents.add(controllerEvent)
            processControllerEvents()
        }
    }

    private suspend fun processControllerEvents() = withContext(singleThreadContext) {
        while (controllerEvents.isNotEmpty() && isActive) {
            val keyCode = getMin(controllerEvents).keyCode
            if (keyCode == GAMEPAD_BUTTON_X || keyCode == GAMEPAD_BUTTON_B) {
                nextMorseNode(keyCode)
            } else if (keyCode == GAMEPAD_BUTTON_A) {
                selectCurrNode()
            } else if (keyCode == GAMEPAD_BUTTON_Y) {
                morseNode = Morse.root
                gameSounds.play(gameSounds.on_escape)
            } else {
                var userInput = keyCodeToUserInput(keyCode)
                if (userInput == null) {
                    gameSounds.play(gameSounds.on_error)
                } else {
                    userInputListener(userInput)
                }
            }
        }
    }

    private fun keyCodeToUserInput(keyCode: Int): UserInput? {
        return when (keyCode) {
            GAMEPAD_BUTTON_START -> null
            GAMEPAD_BUTTON_LEFT -> LEFT
            GAMEPAD_BUTTON_RIGHT -> RIGHT
            GAMEPAD_BUTTON_UP -> UP
            GAMEPAD_BUTTON_DOWN -> DOWN
            GAMEPAD_BUTTON_LEFT_SHIFT -> ESCAPE
            GAMEPAD_BUTTON_RIGHT_SHIFT -> ENTER
            else -> null
        }
    }

    private suspend fun nextMorseNode(keyCode: Int) {
        val newNode = if (keyCode == GAMEPAD_BUTTON_X) {
            morseNode.dashChild
        } else {
            morseNode.dotChild
        }
        if (newNode == null) {
            morseNode = Morse.root
            gameSounds.play(gameSounds.on_error)
        } else {
            morseNode = newNode
        }
    }

    private suspend fun selectCurrNode() {
        val selectedInput = morseNode.userInput
        morseNode = Morse.root
        if (selectedInput != null) {
            gameSounds.play(gameSounds.on_next)
            userInputListener(selectedInput!!)
        } else {
            gameSounds.play(gameSounds.on_error)
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