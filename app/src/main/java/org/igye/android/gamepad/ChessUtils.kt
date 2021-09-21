package org.igye.android.gamepad

object ChessUtils {
    fun cellNumToCell(cellNum:Int): Cell {
        return Cell(x=cellNum/8, y=cellNum%8)
    }

    fun cellToNum(cell:Cell): Int {
        return cell.x*8+cell.y
    }

    fun cellToSoundIds(cell: Cell, gs: GameSoundsI):List<Int> {
        return listOf(xCoordToSoundId(cell.x,gs), yCoordToSoundId(cell.y,gs))
    }

    fun sayCell(cell: Cell, gs: GameSoundsI) {
        gs.play(*cellToSoundIds(cell, gs).toIntArray())
    }

    private fun yCoordToSoundId(y: Int, gs: GameSoundsI): Int {
        return when (y) {
            0 -> gs._1
            1 -> gs._2
            2 -> gs._3
            3 -> gs._4
            4 -> gs._5
            5 -> gs._6
            6 -> gs._7
            7 -> gs._8
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