package org.igye.android.gamepad

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
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
            controllerEventListenerFactory = { Code4ControllerEventListener(userInputListener = it, gameSounds = gs) }
        )

        //when
        runBlocking { gameSelector.onControllerEvent(pressLeftShift()) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.code4)

        //when
        runBlocking { gameSelector.onControllerEvent(press0()) }
        runBlocking { gameSelector.onControllerEvent(press2()) }
        runBlocking { gameSelector.onControllerEvent(press2()) }
        //then
        gs.assertPlayed(gs.on_enter2, gs.bravo)

        //when
        runBlocking { gameSelector.onControllerEvent(press1()) }
        runBlocking { gameSelector.onControllerEvent(press2()) }
        runBlocking { gameSelector.onControllerEvent(press0()) }
        //then
        gs.assertPlayed(gs.on_error, gs._0, gs._2, gs._3)

        //when
        runBlocking { gameSelector.onControllerEvent(press2()) }
        runBlocking { gameSelector.onControllerEvent(press2()) }
        //then
        gs.assertPlayed(gs.on_error)

        //when
        runBlocking { gameSelector.onControllerEvent(press0()) }
        runBlocking { gameSelector.onControllerEvent(press2()) }
        runBlocking { gameSelector.onControllerEvent(press3()) }
        //then
        gs.assertPlayed(gs.on_enter2, gs.charlie)
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