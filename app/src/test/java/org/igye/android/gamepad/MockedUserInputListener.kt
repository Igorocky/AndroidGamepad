package org.igye.android.gamepad

import junit.framework.TestCase
import java.util.concurrent.ConcurrentLinkedQueue

class MockedUserInputListener {
    private var userInput = ConcurrentLinkedQueue<UserInput>()

    fun onUserInput(userInput: UserInput): Boolean {
        this.userInput.add(userInput)
        return true
    }

    fun assertUserInput(vararg expectedSeq: UserInput) {
        val expected = expectedSeq.toList()
        val actual = userInput.toList()
        userInput.clear()
        TestCase.assertEquals(expected, actual)
    }

    fun assertNoUserInput() {
        TestCase.assertTrue(userInput.isEmpty())
    }

}