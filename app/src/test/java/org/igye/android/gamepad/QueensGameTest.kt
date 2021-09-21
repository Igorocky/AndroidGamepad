package org.igye.android.gamepad

import junit.framework.TestCase
import org.junit.Test

class QueensGameTest {
    @Test
    fun all_positions_are_generated_correctly() {
        //given
        val gs = MockGameSounds()
        val game = QueensGame(gs)

        //when
        val allPositions = game.generateAllPositions(8,8)

        //then
        TestCase.assertEquals(92, allPositions.size)
    }

    @Test
    fun all_cards_are_generated_correctly() {
        //given
        val gs = MockGameSounds()
        val game = QueensGame(gs)
        val allPositions: Set<Set<Cell>> = game.generateAllPositions(8,8)
        val questionLength = 4

        //when
        val allCards = game.generateAllCards(8,8, questionLength)

        //then
        TestCase.assertNull(allCards.find { it.question.size != questionLength })
        TestCase.assertNull(allCards.find { it.answers.isEmpty() })
        TestCase.assertTrue(allCards.size <= allPositions.size)
        val positionsFromCards: Sequence<Set<Cell>> = allCards.asSequence().flatMap { card ->
            card.answers.asSequence().map { ans -> HashSet(card.question + ans) }
        }
        TestCase.assertNull(positionsFromCards.find { it.size != 8 })

        TestCase.assertNull(positionsFromCards.find { !allPositions.contains(it) })
    }

    @Test
    fun questionToSounds_produces_correct_sounds_sequence() {
        //given
        val gs = MockGameSounds()
        val game = QueensGame(gs)
        val card = QueensGame.Card(
            question = listOf(Cell(x = 0, y = 3), Cell(x = 1, y = 0), Cell(x = 2, y = 4), Cell(x = 3, y = 7)),
            answers = listOf(
                listOf(Cell(x = 4, y = 1), Cell(x = 5, y = 6), Cell(x = 6, y = 2), Cell(x = 7, y = 5)),
                listOf(Cell(x = 4, y = 5), Cell(x = 5, y = 2), Cell(x = 6, y = 6), Cell(x = 7, y = 1))
            )
        )

        //when
        val sounds: List<Int> = game.questionToSounds(card)

        //then
        gs.assertEquals(
            listOf(gs.question, gs.pause400, gs.a, gs._4, gs.pause400, gs.b, gs._1, gs.pause400, gs.c, gs._5, gs.pause400, gs.d, gs._8),
            sounds
        )
    }

    @Test
    fun answersToSound_produces_correct_sounds_sequence() {
        //given
        val gs = MockGameSounds()
        val game = QueensGame(gs)
        val card = QueensGame.Card(
            question = listOf(Cell(x = 0, y = 3), Cell(x = 1, y = 0), Cell(x = 2, y = 4), Cell(x = 3, y = 7)),
            answers = listOf(
                listOf(Cell(x = 4, y = 1), Cell(x = 5, y = 6), Cell(x = 6, y = 2), Cell(x = 7, y = 5)),
                listOf(Cell(x = 4, y = 5), Cell(x = 5, y = 2), Cell(x = 6, y = 6), Cell(x = 7, y = 1))
            )
        )

        //when
        val sounds: List<Int> = game.answersToSound(card)

        //then
        gs.assertEquals(
            listOf(
                gs.answer, gs._1,
                gs.e, gs._2, gs.pause400, gs.f, gs._7, gs.pause400, gs.g, gs._3, gs.pause400, gs.h, gs._6,
                gs.answer, gs._2,
                gs.e, gs._6, gs.pause400, gs.f, gs._3, gs.pause400, gs.g, gs._7, gs.pause400, gs.h, gs._2),
            sounds
        )
    }
}