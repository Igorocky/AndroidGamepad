package org.igye.gamepad

import org.igye.android.gamepad.Cell
import org.igye.android.gamepad.ChessUtils
import org.junit.Assert
import org.junit.Test

class ChessUtilsTest {
    @Test
    fun cellNumToCell() {
        Assert.assertEquals(Cell(x=0,y=0), ChessUtils.cellNumToCell(0))
        Assert.assertEquals(Cell(x=0,y=5), ChessUtils.cellNumToCell(5))
        Assert.assertEquals(Cell(x=1,y=0), ChessUtils.cellNumToCell(8))
        Assert.assertEquals(Cell(x=7,y=7), ChessUtils.cellNumToCell(63))
    }

    @Test
    fun cellToNum() {
        Assert.assertEquals(ChessUtils.cellNumToCell(0), Cell(x=0,y=0))
        Assert.assertEquals(ChessUtils.cellNumToCell(5), Cell(x=0,y=5))
        Assert.assertEquals(ChessUtils.cellNumToCell(8), Cell(x=1,y=0))
        Assert.assertEquals(ChessUtils.cellNumToCell(63), Cell(x=7,y=7))
    }
}