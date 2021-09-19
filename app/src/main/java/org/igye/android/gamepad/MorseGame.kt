package org.igye.android.gamepad

import kotlinx.coroutines.delay
import org.igye.android.gamepad.UserInput.*

class MorseGame(
    gameSounds: GameSoundsI,
    nextElemSelectorFactory: (List<UserInput>) -> NextElemSelector<UserInput> = {RandomElemSelector(it)}
): Game {
    private val gs = gameSounds
    val possibleCards = listOf(
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
        _0, _1, _2, _3, _4, _5, _6, _7, _8, _9
    )
    val possibleCardsSet = possibleCards.toSet()
    private val allCards: NextElemSelector<UserInput> = nextElemSelectorFactory(possibleCards)
    private var currCard: UserInput = allCards.nextElem()
    override suspend fun sayGameTitle() {
        gs.play(gs.morse)
    }

    override suspend fun onUserInput(userInput: UserInput) {
        if (userInput == currCard) {
            gs.play(gs.on_enter2)
            delay(1000)
            currCard = allCards.nextElem()
            sayCurrCard()
        } else if (userInput == RIGHT) {
            sayCurrCard()
        } else {
            gs.play(gs.on_error)
            if (possibleCards.contains(userInput)) {
                delay(500)
                sayCurrCard()
            }
        }
    }

    fun getCounts() = allCards.getCounts()

    private suspend fun sayCurrCard() {
        when(currCard) {
            A -> gs.play(gs.alpha)
            B -> gs.play(gs.bravo)
            C -> gs.play(gs.charlie)
            D -> gs.play(gs.delta)
            E -> gs.play(gs.echo)
            F -> gs.play(gs.foxtrot)
            G -> gs.play(gs.golf)
            H -> gs.play(gs.hotel)
            I -> gs.play(gs.india)
            J -> gs.play(gs.juliet)
            K -> gs.play(gs.kilo)
            L -> gs.play(gs.lima)
            M -> gs.play(gs.mike)
            N -> gs.play(gs.november)
            O -> gs.play(gs.oscar)
            P -> gs.play(gs.papa)
            Q -> gs.play(gs.quebec)
            R -> gs.play(gs.romeo)
            S -> gs.play(gs.sierra)
            T -> gs.play(gs.tango)
            U -> gs.play(gs.uniform)
            V -> gs.play(gs.victor)
            W -> gs.play(gs.whiskey)
            X -> gs.play(gs.xRay)
            Y -> gs.play(gs.yankee)
            Z -> gs.play(gs.zulu)
            _0 -> gs.play(gs._0)
            _1 -> gs.play(gs._1)
            _2 -> gs.play(gs._2)
            _3 -> gs.play(gs._3)
            _4 -> gs.play(gs._4)
            _5 -> gs.play(gs._5)
            _6 -> gs.play(gs._6)
            _7 -> gs.play(gs._7)
            _8 -> gs.play(gs._8)
            _9 -> gs.play(gs._9)
        }
    }
}