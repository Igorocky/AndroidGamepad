package org.igye.android.gamepad

import kotlinx.coroutines.runBlocking
import org.junit.Test

class GameSelectorTest {
    @Test
    fun test_switching_between_games() {
        //given
        val gs = MockGameSounds()
        val gameSelector = GameSelector(
            gameSounds = gs,
            games = listOf(
                { CellsGame(gameSounds = gs, nextElemSelectorFactory = {SequentialElemSelector(it)}) },
                { MorseGame(gameSounds = gs, nextElemSelectorFactory = {SequentialElemSelector(it)}) }
            ),
            controllerEventListenerFactory = { MorseControllerEventListener(userInputListener = it, gameSounds = gs) }
        )

        //when
        runBlocking { gameSelector.onControllerEvent(pressLeftShift()) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.morse)

        //when
        runBlocking { gameSelector.onControllerEvent(pressLeftShift()) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.cells)

        //when
        runBlocking { gameSelector.onControllerEvent(pressRight()) }
        //then
        gs.assertPlayed(gs.a, gs._1)

        //when
        runBlocking { gameSelector.onControllerEvent(pressUp()) }
        //then
        gs.assertPlayed(gs.on_enter2, gs.a, gs._2)

        //when
        runBlocking { gameSelector.onControllerEvent(pressLeftShift()) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.morse)

        //when
        runBlocking {
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(pressDash())
            gameSelector.onControllerEvent(confirmMorseSymbol())
        }
        //then
        gs.assertPlayed(gs.on_next, gs.on_enter2, gs.bravo)

        //when
        runBlocking {
            gameSelector.onControllerEvent(pressDash())
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(confirmMorseSymbol())
        }
        //then
        gs.assertPlayed(gs.on_next, gs.on_enter2, gs.charlie)

        //when
        runBlocking {
            gameSelector.onControllerEvent(pressDash())
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(pressDot())
            gameSelector.onControllerEvent(confirmMorseSymbol())
        }
        //then
        gs.assertPlayed(gs.on_next, gs.on_error, gs.charlie)

        //when
        runBlocking { gameSelector.onControllerEvent(pressLeftShift()) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.cells)
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
    private fun pressX(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_X)
    }
    private fun pressA(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_A)
    }
    private fun pressB(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_B)
    }
    private fun pressY(): ControllerEvent {
        return press(Constants.GAMEPAD_BUTTON_Y)
    }
    private fun pressDash(): ControllerEvent {
        return pressX()
    }
    private fun pressDot(): ControllerEvent {
        return pressB()
    }
    private fun confirmMorseSymbol(): ControllerEvent {
        return pressA()
    }
    private fun cancelMorseSymbol(): ControllerEvent {
        return pressY()
    }

}