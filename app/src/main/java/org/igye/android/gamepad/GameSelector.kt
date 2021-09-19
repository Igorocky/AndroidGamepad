package org.igye.android.gamepad

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameSelector(
    gameSounds: GameSoundsI,
    controllerEventListenerFactory: (suspend (UserInput) -> Unit) -> ControllerEventListener,
    games: List<() -> Game> = listOf({CellsGame(gameSounds)}, {MorseGame(gameSounds)}),
) {
    private val gs = gameSounds
    private val gameFactories: SequentialElemSelector<() -> Game> = SequentialElemSelector(games)
    private var currGame: Game = gameFactories.nextElem()()
    private val controllerEventListener = controllerEventListenerFactory(this::onUserInput)

    suspend fun onControllerEvent(controllerEvent: ControllerEvent): Unit = coroutineScope {
        launch {
            controllerEventListener.onControllerEvent(controllerEvent)
        }
    }

    private suspend fun onUserInput(userInput: UserInput) {
        if (userInput == UserInput.ESCAPE) {
            currGame = gameFactories.nextElem()()
            gs.play(gs.on_backspace)
            currGame.sayGameTitle()
        } else {
            currGame.onUserInput(userInput)
        }
    }
}