package org.igye.android.gamepad

import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_RIGHT
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_UP
import kotlin.random.Random

class CellsGame(gameSounds: GameSoundsI): GameI {
    private val gs = gameSounds
    private val allCells: List<Cell> = generateSequence(0) { it+1 }.take(63).map(ChessUtils::cellNumToCell).toList()
    private val counts: MutableMap<Cell, Int> = allCells.asSequence().map { it to 0 }.toMap().toMutableMap()
    private var currCell = allCells[Random.nextInt(0,allCells.size)]
    init {
        counts[currCell] = 1
    }

    override suspend fun onUserInput(userInput: UserInput) {
        if (userInput.keyCode == GAMEPAD_BUTTON_RIGHT) {
            gs.sayCell(currCell)
        } else if (userInput.keyCode == GAMEPAD_BUTTON_UP) {
            gs.play(gs.on_enter2)
            nextCard()
            gs.sayCell(currCell)
        } else {
            gs.play(gs.on_error)
        }
    }

    fun getCounts() = counts.toMap()

    private fun nextCard() {
        val minCnt = counts.values.minOrNull()!!
        val cellsWithMinCnt: List<Cell> = counts.asSequence().filter { e -> e.value == minCnt }.map { it.key }.toList()
        currCell = cellsWithMinCnt[Random.nextInt(0,cellsWithMinCnt.size)]
        counts[currCell] = counts[currCell]!! + 1
    }
}