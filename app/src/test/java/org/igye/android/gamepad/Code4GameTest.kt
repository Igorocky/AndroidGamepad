package org.igye.android.gamepad

import org.igye.TestUtils
import org.igye.TestUtils.press0
import org.igye.TestUtils.press2
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
}