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
    override suspend fun sayGameTitle() {
        gs.play(gs.cells)
    }

    override suspend fun onUserInput(userInput: UserInput) {
        if (userInput == UserInput.RIGHT) {
            ChessUtils.sayCell(currCell, gs)
        } else if (userInput == UserInput.UP) {
            gs.play(gs.on_enter2)
            currCell = allCells.nextElem()
            ChessUtils.sayCell(currCell, gs)
        } else {
            gs.play(gs.on_error)
        }
    }

    fun getCounts() = allCells.getCounts()
}