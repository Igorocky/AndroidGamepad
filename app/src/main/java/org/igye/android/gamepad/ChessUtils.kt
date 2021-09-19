package org.igye.android.gamepad

object ChessUtils {
    fun cellNumToCell(cellNum:Int): Cell {
        return Cell(x=cellNum/8, y=cellNum%8)
    }

    fun cellToNum(cell:Cell): Int {
        return cell.x*8+cell.y
    }

    suspend fun sayCell(cell: Cell, gs: GameSoundsI) {
        gs.play(xCoordToSoundId(cell.x,gs), yCoordToSoundId(cell.y,gs))
    }

    private fun yCoordToSoundId(y: Int, gs: GameSoundsI): Int {
        return when (y) {
            0 -> gs.one
            1 -> gs.two
            2 -> gs.three
            3 -> gs.four
            4 -> gs.five
            5 -> gs.six
            6 -> gs.seven
            7 -> gs.eight
            else -> gs.on_error
        }
    }

    private fun xCoordToSoundId(x: Int, gs: GameSoundsI): Int {
        return when (x) {
            0 -> gs.a
            1 -> gs.b
            2 -> gs.c
            3 -> gs.d
            4 -> gs.e
            5 -> gs.f
            6 -> gs.g
            7 -> gs.h
            else -> gs.on_error
        }
    }
}