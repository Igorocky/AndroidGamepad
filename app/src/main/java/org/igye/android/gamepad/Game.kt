package org.igye.android.gamepad

interface Game {
    fun sayGameTitle()
    fun onUserInput(userInput: UserInput): Boolean
}