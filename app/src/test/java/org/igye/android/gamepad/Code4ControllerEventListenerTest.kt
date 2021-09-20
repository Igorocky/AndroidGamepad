package org.igye.android.gamepad

import kotlinx.coroutines.runBlocking
import org.igye.android.gamepad.UserInput.*
import org.junit.Test

class Code4ControllerEventListenerTest {
    @Test
    fun test_few_simple_inputs() {
        //given
        val gs = MockGameSounds()
        val unblockedSound = gs.on_next
        val blockedSound = gs.on_go_to_end
        val userInput = MockedUserInputListener()
        val controllerListener = Code4ControllerEventListener(userInputListener = userInput::onUserInput, gameSounds = gs)

        //when
        runBlocking { controllerListener.onControllerEvent(press0()) }
        runBlocking { controllerListener.onControllerEvent(press2()) }
        runBlocking { controllerListener.onControllerEvent(press1()) }
        //then
        gs.assertPlayedNothing()
        userInput.assertUserInput(_9)

        //when
        runBlocking { controllerListener.onControllerEvent(press0()) }
        runBlocking { controllerListener.onControllerEvent(press3()) }
        runBlocking { controllerListener.onControllerEvent(press0()) }
        //then
        gs.assertPlayedNothing()
        userInput.assertUserInput(C)

        //when
        runBlocking { controllerListener.onControllerEvent(press2()) }
        runBlocking { controllerListener.onControllerEvent(press2()) }
        runBlocking { controllerListener.onControllerEvent(press2()) }
        //then
        gs.assertPlayed(blockedSound,blockedSound)
        userInput.assertNoUserInput()

        //when
        runBlocking { controllerListener.onControllerEvent(press0()) }
        runBlocking { controllerListener.onControllerEvent(press3()) }
        runBlocking { controllerListener.onControllerEvent(press0()) }
        //then
        gs.assertPlayed(blockedSound,blockedSound,blockedSound)
        userInput.assertNoUserInput()

        //when
        runBlocking { controllerListener.onControllerEvent(pressRightShift()) }
        //then
        gs.assertPlayed(blockedSound)
        userInput.assertNoUserInput()

        //when
        runBlocking { controllerListener.onControllerEvent(pressStart()) }
        //then
        gs.assertPlayed(unblockedSound)
        userInput.assertNoUserInput()

        //when
        runBlocking { controllerListener.onControllerEvent(press0()) }
        runBlocking { controllerListener.onControllerEvent(press3()) }
        runBlocking { controllerListener.onControllerEvent(press0()) }
        //then
        gs.assertPlayedNothing()
        userInput.assertUserInput(C)

        //when
        runBlocking { controllerListener.onControllerEvent(press0()) }
        runBlocking { controllerListener.onControllerEvent(press3()) }
        runBlocking { controllerListener.onControllerEvent(pressRightShift()) }
        //then
        gs.assertPlayed(blockedSound)
        userInput.assertNoUserInput()

        //when
        runBlocking { controllerListener.onControllerEvent(pressStart()) }
        //then
        gs.assertPlayed(unblockedSound)
        userInput.assertNoUserInput()

        //when
        runBlocking { controllerListener.onControllerEvent(pressRightShift()) }
        //then
        gs.assertPlayedNothing()
        userInput.assertUserInput(ENTER)
    }

    private var time: Long = 0
    private fun press(keyCode:Int): ControllerEvent {
        return ControllerEvent(keyCode = keyCode, eventTime = time++)
    }
    private fun pressLeftShift(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_LEFT_SHIFT)
    }
    private fun pressRightShift(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_RIGHT_SHIFT)
    }
    private fun pressStart(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_START)
    }
    private fun pressDown(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_DOWN)
    }
    private fun pressUp(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_UP)
    }
    private fun pressRight(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_RIGHT)
    }
    private fun pressLeft(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_RIGHT)
    }
    private fun press0(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_B)
    }
    private fun press1(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_A)
    }
    private fun press2(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_X)
    }
    private fun press3(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_Y)
    }
}