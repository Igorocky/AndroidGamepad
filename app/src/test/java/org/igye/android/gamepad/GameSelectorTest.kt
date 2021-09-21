package org.igye.android.gamepad

import kotlinx.coroutines.runBlocking
import org.igye.android.gamepad.UserInput.*
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
        )

        //when
        runBlocking { gameSelector.onUserInput(ESCAPE) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.morse)

        //when
        runBlocking { gameSelector.onUserInput(ESCAPE) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.cells)

        //when
        runBlocking { gameSelector.onUserInput(RIGHT) }
        //then
        gs.assertPlayed(gs.a, gs._1)

        //when
        runBlocking { gameSelector.onUserInput(UP) }
        //then
        gs.assertPlayed(gs.on_enter2, gs.a, gs._2)

        //when
        runBlocking { gameSelector.onUserInput(ESCAPE) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.morse)

        //when
        runBlocking {
            gameSelector.onUserInput(A)
        }
        //then
        gs.assertPlayed(gs.on_next, gs.on_enter2, gs.bravo)

        //when
        runBlocking {
            gameSelector.onUserInput(B)
        }
        //then
        gs.assertPlayed(gs.on_next, gs.on_enter2, gs.charlie)

        //when
        runBlocking {
            gameSelector.onUserInput(B)
        }
        //then
        gs.assertPlayed(gs.on_next, gs.on_error, gs.charlie)

        //when
        runBlocking { gameSelector.onUserInput(ESCAPE) }
        //then
        gs.assertPlayed(gs.on_backspace, gs.cells)
    }

}