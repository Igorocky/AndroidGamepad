package org.igye.android.gamepad

class GameSelector(
    gameSounds: GameSoundsI,
    games: List<() -> Game> = listOf({CellsGame(gameSounds)}, {MorseGame(gameSounds)}),
): Game {
    private val gs = gameSounds
    private val gameFactories: SequentialElemSelector<() -> Game> = SequentialElemSelector(games)
    private var currGame: Game = gameFactories.nextElem()()
    override fun sayGameTitle() {
    }

    override fun onUserInput(userInput: UserInput): Boolean {
        if (userInput == UserInput.ESCAPE) {
            currGame = gameFactories.nextElem()()
            gs.play(gs.on_backspace)
            currGame.sayGameTitle()
            return true
        } else {
            return currGame.onUserInput(userInput)
        }
    }
}