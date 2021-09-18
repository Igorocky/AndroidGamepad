package org.igye.android.gamepad

interface GameI {
    suspend fun onUserInput(userInput: UserInput)
}