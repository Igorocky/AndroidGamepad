package org.igye.android.gamepad

interface Game {
    suspend fun sayGameTitle()
    suspend fun onUserInput(userInput: UserInput)
}