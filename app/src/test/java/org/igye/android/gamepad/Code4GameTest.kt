package org.igye.android.gamepad

import org.igye.TestUtils
import org.igye.android.gamepad.UserInput.*
import org.junit.Test

class Code4GameTest {
    @Test
    fun test_few_simple_inputs() {
        //given
        val gs = MockGameSounds()
        val gameSelector = GameSelector(
            gameSounds = gs,
            games = listOf(
                { Code4Game(gameSounds = gs, nextElemSelectorFactory = {SequentialElemSelector(it)}) },
            ),
        )

        //when
        gameSelector.onUserInput(ESCAPE)
        //then
        gs.assertPlayed(gs.on_backspace, gs.code4)

        //when
        gameSelector.onUserInput(_0)
        //then
        gs.assertPlayed(gs.on_enter2, gs._1)

        //when
        gameSelector.onUserInput(P)
        //then
        gs.assertPlayed(gs.on_error, gs._0, gs._0, gs._1)

        //when
        gameSelector.onUserInput(_2)
        //then
        gs.assertPlayed(gs.on_error, gs._0, gs._0, gs._1)

        //when
        gameSelector.onUserInput(_1)
        //then
        gs.assertPlayed(gs.on_enter2, gs._2)
    }

    @Test
    fun keyboard_is_blocked_on_incorrect_user_input() {
        //given
        val gs = MockGameSounds()
        val unblockedSound = gs.on_next
        val blockedSound = gs.on_go_to_end
        val gameSelector = GameSelector(
            gameSounds = gs,
            games = listOf(
                { Code4Game(gameSounds = gs, nextElemSelectorFactory = {SequentialElemSelector(it)}) },
            ),
        )
        val controllerListener = Code4ControllerEventListener(
            userInputListener = gameSelector::onUserInput,
            gameSounds = gs
        )

        TestUtils.runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press0())
        }) {
            gs.assertPlayed(gs.on_enter2, gs._1)
        }

        TestUtils.runTestCase(controllerListener, {
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press0())
            controllerListener.onControllerEvent(press2())
        }) {
            gs.assertPlayed(gs.on_error, gs._0, gs._0, gs._1, blockedSound)
        }

        controllerListener.close()
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