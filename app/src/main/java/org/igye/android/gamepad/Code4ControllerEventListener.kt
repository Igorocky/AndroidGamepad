package org.igye.android.gamepad

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
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class Code4ControllerEventListener(
    private val userInputListener: (UserInput) -> Boolean,
    private val gameSounds: GameSoundsI,
): Closeable, ControllerEventListener {
    private val isActive = AtomicBoolean(true)

    val onNegativeKeyCode = AtomicReference<(()->Unit)?>(null)

    private val controllerEvents: BlockingQueue<ControllerEvent> = LinkedBlockingQueue()
    private var currNode: Code4TreeNode = Code4.root
    private var isBlocked = false
    private val unblockedSound = gameSounds.on_next
    private val blockedSound = gameSounds.on_go_to_end

    private val timeFilters: MutableMap<Int,Long> = HashMap()

    override fun close() {
        isActive.set(false)
    }

    init {
        Thread {
            val log = LoggerImpl("Code4ControllerEventListener")
            while (isActive.get()) {
                val event = controllerEvents.poll(2000, TimeUnit.MILLISECONDS)
                if (event != null) {
                    val keyCode = event.keyCode
                    if (keyCode < 0) {
                        onNegativeKeyCode.get()?.invoke()
                        onNegativeKeyCode.set(null)
                    } else {
                        val currTime = event.eventTime
                        val prevTime = timeFilters.getOrDefault(keyCode, -1000)
                        timeFilters.put(keyCode, currTime)
                        val delta = currTime - prevTime
                        if (delta > 100) {
                            if (keyCode == Constants.REPEAT_COUNT_TOO_BIG) {
                                gameSounds.play(gameSounds.on_enter_short)
                                gameSounds.play(gameSounds.on_enter_short)
                            } else {
                                processControllerEvent(keyCode)
                            }
                        } else {
                            log.debug("delta = $delta")
                        }
                    }
                }
            }
        }.start()
    }

    override fun onControllerEvent(controllerEvent: ControllerEvent) {
        controllerEvents.add(controllerEvent)
    }

    private fun processControllerEvent(keyCode: Int) {
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
                invokeUserInputListener(userInput)
            }
        }
    }

    private fun block() {
        isBlocked = true
        gameSounds.play(blockedSound)
    }

    private fun unblock() {
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

    private fun nextNode(keyCode: Int) {
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

    private fun selectCurrNode() {
        val selectedInput = currNode.userInput
        currNode = Code4.root
        if (selectedInput == null) {
            block()
        } else {
            invokeUserInputListener(selectedInput)
        }
    }

    private fun invokeUserInputListener(userInput: UserInput) {
        gameSounds.play(gameSounds.on_next)
        if (!userInputListener(userInput)) {
            block()
        }
    }
}