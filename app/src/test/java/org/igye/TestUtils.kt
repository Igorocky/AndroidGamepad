package org.igye

import org.igye.android.gamepad.Code4ControllerEventListener
import org.igye.android.gamepad.Constants
import org.igye.android.gamepad.ControllerEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object TestUtils {
    fun runTestCase(controllerListener: Code4ControllerEventListener, setup:() -> Unit, then:() -> Unit) {
        val latch = CountDownLatch(1)
        controllerListener.onNegativeKeyCode.set { latch.countDown() }
        setup()
        controllerListener.onControllerEvent(press(-1))
        latch.await(1000000, TimeUnit.MILLISECONDS)
        then()
    }
    private var time: Long = 0
    fun press(keyCode:Int): ControllerEvent {
        time += 300
        return ControllerEvent(keyCode = keyCode, eventTime = time)
    }
    fun pressLeftShift(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_LEFT_SHIFT)
    }
    fun pressRightShift(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_RIGHT_SHIFT)
    }
    fun pressStart(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_START)
    }
    fun pressDown(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_DOWN)
    }
    fun pressUp(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_UP)
    }
    fun pressRight(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_RIGHT)
    }
    fun pressLeft(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_RIGHT)
    }
    fun press0(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_B)
    }
    fun press1(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_A)
    }
    fun press2(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_X)
    }
    fun press3(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_Y)
    }
}