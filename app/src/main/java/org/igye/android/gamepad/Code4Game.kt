package org.igye.android.gamepad

import kotlinx.coroutines.delay
import org.igye.android.gamepad.UserInput.*
import org.igye.android.gamepad.UserInput.R

class Code4Game(
    gameSounds: GameSoundsI,
    private val nextElemSelectorFactory: (List<UserInput>) -> NextElemSelector<UserInput> = {RandomElemSelector(it)}
): Game {
    private val gs = gameSounds
    val possibleCards = listOf(
        _0, _1, _2, _3, _4,//0-4
        _5, _6, _7, _8, _9,//5-9
        A, B, C, D, E,//10-14
        F, G, H, I, J,//15-19
        K, L, M, N, O,//20-24
        P, Q, R, S, T,//25-29
        U, V, W, X, Y, Z,//30-35
    )
    val group1 = arrayOf(0,4+1)
    val group2 = arrayOf(5,9+1)
    val group3 = arrayOf(10,14+1)
    val group4 = arrayOf(15,19+1)
    val group5 = arrayOf(20,24+1)
    val group6 = arrayOf(25,29+1)
    val group7 = arrayOf(30,35+1)
    val groups = listOf(
        possibleCards.subList(group1[0],group1[1]),
        possibleCards.subList(group2[0],group2[1]),
        possibleCards.subList(group1[0],group2[1]),

        possibleCards.subList(group3[0],group3[1]),
        possibleCards.subList(group4[0],group4[1]),
        possibleCards.subList(group3[0],group4[1]),

        possibleCards.subList(group1[0],group4[1]),

        possibleCards.subList(group5[0],group5[1]),
        possibleCards.subList(group6[0],group6[1]),
        possibleCards.subList(group5[0],group6[1]),

        possibleCards.subList(group7[0],group7[1]),
        possibleCards.subList(group5[0],group7[1]),

        possibleCards.subList(group1[0],group7[1]),

    )
    var currGroupIdx = 0
    val possibleCardsSet = possibleCards.toSet()
    private var nextCardSelector: NextElemSelector<UserInput> = nextElemSelectorFactory(groups[currGroupIdx])
    private var currCard: UserInput = nextCardSelector.nextElem()
    override suspend fun sayGameTitle() {
        gs.play(gs.code4)
    }

    override suspend fun onUserInput(userInput: UserInput) {
        if (userInput == currCard) {
            gs.play(gs.on_enter2)
            delay(300)
            currCard = nextCardSelector.nextElem()
            if (nextCardSelector.getCounts().toSet().size == 1) {
                gs.play(gs.on_go_to_end)
                gs.play(gs.on_go_to_end)
            }
            sayQuestion()
        } else if (userInput == LEFT) {
            sayQuestion()
        } else if (userInput == DOWN) {
            changeGroup(1)
        } else if (userInput == UP) {
            changeGroup(-1)
        } else {
            sayAnswer()
        }
    }

    private suspend fun changeGroup(delta: Int) {
        currGroupIdx = (groups.size + currGroupIdx + delta) % groups.size
        nextCardSelector = nextElemSelectorFactory(groups[currGroupIdx])
        currCard = nextCardSelector.nextElem()
        gs.play(gs.on_next)
        gs.play(*currGroupIdx.toString().map { Utils.digitToSoundId(it, gs) }.toIntArray())
    }

    fun getCounts() = nextCardSelector.getCounts()

    private suspend fun sayQuestion() {
        Utils.sayUserInput(currCard, gs)
    }

    private suspend fun sayAnswer() {
        Code4.sayCode(Code4.userInputToCode(currCard)!!, gs)
    }
}