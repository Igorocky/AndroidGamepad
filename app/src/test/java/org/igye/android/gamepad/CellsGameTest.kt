package org.igye.android.gamepad

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CellsGameTest {

    @Test
    fun manyNextCellInvocations() {
        //given
        val cellsGame = CellsGame(MockGameSounds())
        val timesToRepeat = 10000

        //when
        runBlocking {
            repeat(timesToRepeat) {
                cellsGame.onUserInput(UserInput.UP)
            }
        }

        //then
        val counts = cellsGame.getCounts()
        var minCnt = counts.minOrNull()!!
        var maxCnt = counts.maxOrNull()!!
        Assert.assertTrue(minCnt + 1 == maxCnt)
        Assert.assertTrue(minCnt == timesToRepeat / 64)
    }
}