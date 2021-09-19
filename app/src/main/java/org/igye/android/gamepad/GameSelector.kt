package org.igye.android.gamepad

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameSelector(
    gameSounds: GameSoundsI,
    cellsGameFactory: () -> CellsGame = {CellsGame(gameSounds)},
    morseGameFactory: () -> MorseGame = {MorseGame(gameSounds)},
) {
    private val gs = gameSounds
    private val gameFactories: SequentialElemSelector<() -> Game> = SequentialElemSelector(listOf(
        cellsGameFactory,
        morseGameFactory,
    ))
    private var currGame: Game = gameFactories.nextElem()()
    private val controllerEventListener = ControllerEventListener(
        gameSounds = gameSounds,
        userInputListener = this::onUserInput
    )

    suspend fun onControllerEvent(controllerEvent: ControllerEvent): Unit = coroutineScope {
        launch {
            controllerEventListener.onControllerEvent(controllerEvent)
        }
    }

    private suspend fun onUserInput(userInput: UserInput) {
        if (userInput == UserInput.ESCAPE) {
            currGame = gameFactories.nextElem()()
            gs.play(gs.on_backspace)
            delay(500)
            sayCurrGameTitle()
        } else {
            currGame.onUserInput(userInput)
        }
    }

    private suspend fun sayCurrGameTitle() {
        when (currGame) {
            is CellsGame -> gs.play(gs.cells)
            is MorseGame -> gs.play(gs.morse)
        }
    }
}