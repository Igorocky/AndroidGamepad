package org.igye.android.gamepad

import kotlinx.coroutines.*
import org.igye.android.gamepad.Constants.GAMEPAD_BUTTON_LEFT_SHIFT
import java.util.concurrent.ConcurrentHashMap

class GameSelector(
    gameSounds: GameSoundsI,
    private val gameSingleThreadContext: CoroutineDispatcher = newSingleThreadContext("GameSelectorContext")
): GameI {
    private val gs = gameSounds
    private var currGame = CellsGame(gameSounds)
    private var events = ConcurrentHashMap<UserInput,Int>()

    override suspend fun onUserInput(userInput: UserInput): Unit = coroutineScope {
        launch {
            events.put(userInput,1)
            processInput()
        }
    }

    private suspend fun processInput() = withContext(gameSingleThreadContext) {
        while (events.isNotEmpty() && isActive) {
            val userInput = events.keys().asSequence().minByOrNull { it.eventTime }!!
            events.remove(userInput)
            if (userInput.keyCode == GAMEPAD_BUTTON_LEFT_SHIFT) {
                gs.play(gs.on_backspace)
                if (currGame is CellsGame) {
                    currGame = CellsGame(gs)
                }
            } else {
                currGame.onUserInput(userInput)
            }
        }
    }
}