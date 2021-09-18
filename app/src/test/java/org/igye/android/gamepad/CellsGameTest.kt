package org.igye.android.gamepad

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CellsGameTest {
    private val mockGameSounds = object : GameSoundsI {
        override suspend fun play(vararg seq: Int) {
        }

        override suspend fun sayCell(cell: Cell) {
        }
    }

    @Test
    fun manyNextCellInvcations() {
        //given
        val cellsGame = CellsGame(mockGameSounds)
        val timesToRepeat = 1000

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
}