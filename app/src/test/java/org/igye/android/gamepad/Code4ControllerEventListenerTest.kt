package org.igye.android.gamepad

import org.igye.TestUtils.press0
import org.igye.TestUtils.press1
import org.igye.TestUtils.press2
import org.igye.TestUtils.press3
import org.igye.TestUtils.pressRightShift
import org.igye.TestUtils.pressStart
import org.igye.TestUtils.runTestCase
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
        val controllerListener: Code4ControllerEventListener = Code4ControllerEventListener(
            userInputListener = userInput::onUserInput,
            gameSounds = gs,
        )

        runTestCase(controllerListener, {
                controllerListener.onControllerEvent(press0())
                controllerListener.onControllerEvent(press2())
                controllerListener.onControllerEvent(press1())
        }){
            gs.assertPlayedNothing()
            userInput.assertUserInput(_9)
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press3())
            controllerListener.onControllerEvent(press0())
        }){
            gs.assertPlayedNothing()
            userInput.assertUserInput(C)
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press2())
            controllerListener.onControllerEvent(press2())
            controllerListener.onControllerEvent(press2())
        }){
            gs.assertPlayed(blockedSound,blockedSound)
            userInput.assertNoUserInput()
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press3())
            controllerListener.onControllerEvent(press0())
        }){
            gs.assertPlayed(blockedSound,blockedSound,blockedSound)
            userInput.assertNoUserInput()
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(pressRightShift())
        }){
            gs.assertPlayed(blockedSound)
            userInput.assertNoUserInput()
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(pressStart())
        }){
            gs.assertPlayed(unblockedSound)
            userInput.assertNoUserInput()
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press3())
            controllerListener.onControllerEvent(press0())
        }){
            gs.assertPlayedNothing()
            userInput.assertUserInput(C)
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press3())
            controllerListener.onControllerEvent(pressRightShift())
        }){
            gs.assertPlayed(blockedSound)
            userInput.assertNoUserInput()
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(pressStart())
        }){
            gs.assertPlayed(unblockedSound)
            userInput.assertNoUserInput()
        }

        runTestCase(controllerListener, {
            controllerListener.onControllerEvent(pressRightShift())
        }){
            gs.assertPlayedNothing()
            userInput.assertUserInput(ENTER)
        }

        controllerListener.close()
    }
}