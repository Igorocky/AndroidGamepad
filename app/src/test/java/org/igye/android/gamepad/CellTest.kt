package org.igye.android.gamepad

import org.junit.Assert
import org.junit.Test

class CellTest {
    @Test
    fun equals_works_correctly() {
        Assert.assertTrue(Cell(x=4,y=7) == Cell(x=4,y=7))
        Assert.assertFalse(Cell(x=4,y=7) == Cell(x=3,y=7))
    }
}