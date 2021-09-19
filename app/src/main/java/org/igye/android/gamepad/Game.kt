package org.igye.android.gamepad

interface Game {
    suspend fun onUserInput(userInput: UserInput)
}