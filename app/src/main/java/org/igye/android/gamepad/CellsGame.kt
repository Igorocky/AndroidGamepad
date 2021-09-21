package org.igye.android.gamepad

class CellsGame(
    gameSounds: GameSoundsI,
    nextElemSelectorFactory: (List<Cell>) -> NextElemSelector<Cell> = {RandomElemSelector(it)}
): Game {
    private val gs = gameSounds
    private val allCells: NextElemSelector<Cell> = nextElemSelectorFactory(
        generateSequence(0) { it+1 }.take(64).map(ChessUtils::cellNumToCell).toList()
    )
    private var currCell = allCells.nextElem()
    override fun sayGameTitle() {
        gs.play(gs.cells)
    }

    override fun onUserInput(userInput: UserInput): Boolean {
        if (userInput == UserInput.RIGHT) {
            ChessUtils.sayCell(currCell, gs)
        } else if (userInput == UserInput.UP) {
            gs.play(gs.on_enter2)
            currCell = allCells.nextElem()
            ChessUtils.sayCell(currCell, gs)
        } else {
            gs.play(gs.on_error)
        }
        return true
    }

    fun getCounts() = allCells.getCounts()
}