package org.igye.android.gamepad

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CellsGameTest {

    @Test
    fun manyNextCellInvcations() {
        //given
        val cellsGame = CellsGame(mockGameSounds)
        val timesToRepeat = 10000

        //when
        runBlocking {
            repeat(timesToRepeat) {
                cellsGame.onUserInput(UserInput(keyCode = Constants.GAMEPAD_BUTTON_UP, eventTime = 1L))
            }
        }

        //then
        val counts = cellsGame.getCounts()
        var minCnt = counts.values.minOrNull()!!
        var maxCnt = counts.values.maxOrNull()!!
        Assert.assertTrue(minCnt + 1 == maxCnt)
        Assert.assertTrue(minCnt == timesToRepeat / 64)
    }

    private val mockGameSounds = object : GameSoundsI {
        override suspend fun play(vararg seq: Int) {
        }

        override suspend fun sayCell(cell: Cell) {
        }

        override val _1: Int = 1
        override val _2: Int = 1
        override val _3: Int = 1
        override val _4: Int = 1
        override val _5: Int = 1
        override val _6: Int = 1
        override val _7: Int = 1
        override val _8: Int = 1
        override val a: Int = 1
        override val b: Int = 1
        override val c: Int = 1
        override val d: Int = 1
        override val e: Int = 1
        override val f: Int = 1
        override val g: Int = 1
        override val h: Int = 1
        override val on_backspace: Int = 1
        override val on_enter: Int = 1
        override val on_enter2: Int = 1
        override val on_error: Int = 1
        override val on_escape: Int = 1
        override val on_go_to_end: Int = 1
        override val on_go_to_end_teleport: Int = 1
        override val on_go_to_start: Int = 1
        override val on_go_to_start2: Int = 1
        override val on_go_to_start3: Int = 1
        override val on_next: Int = 1
        override val on_prev: Int = 1
    }

}