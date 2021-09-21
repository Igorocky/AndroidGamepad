package org.igye.android.gamepad

import kotlin.random.Random

class QueensGame(
    gameSounds: GameSoundsI,
    nextElemSelectorFactory: (List<Card>) -> NextElemSelector<Card> = {RandomElemSelector(it)}
): Game {
    private val gs = gameSounds
    private val allCards: NextElemSelector<Card> = nextElemSelectorFactory(
        generateAllCards(fieldWidth = 8, fieldHeight = 8, questionLength = 4)
    )
    private var currCard = allCards.nextElem()
    override fun sayGameTitle() {
        gs.play(gs.queens)
    }

    override fun onUserInput(userInput: UserInput): Boolean {
        if (userInput == UserInput.RIGHT) {
            sayQuestion(currCard)
        } else if (userInput == UserInput.LEFT) {
            sayAnswers(currCard)
        } else if (userInput == UserInput.UP) {
            currCard = allCards.nextElem()
            gs.play(gs.on_enter2)
            sayQuestion(currCard)
        } else {
            gs.play(gs.on_error)
        }
        return true
    }

    fun cellsToListOfSoundIds(cells:List<Cell>):List<Int> = cells.flatMapIndexed {i,c ->
        val celsList = ChessUtils.cellToSoundIds(c,gs)
        if (i != cells.size -1) celsList + gs.pause400 else celsList
    }

    fun sayQuestion(card: Card) {
        gs.play(questionToSounds(card))
    }

    fun questionToSounds(card: Card): List<Int> {
        return listOf(
            gs.question,
            gs.pause400,
            *cellsToListOfSoundIds(card.question).toTypedArray()
        )
    }

    fun sayAnswers(card: Card) {
        gs.play(answersToSound(card))
    }

    fun answersToSound(card: Card): List<Int> {
        return card.answers.flatMapIndexed { i, ans -> answerToSounds(i+1,ans)  }
    }

    fun answerToSounds(ansNum:Int, ans:List<Cell>): List<Int> {
        return listOf(
            gs.answer,
            intToSoundId(ansNum),
            *cellsToListOfSoundIds(ans).toTypedArray()
        )
    }

    fun intToSoundId(i:Int) = when(i) {
        0 -> gs._0
        1 -> gs._1
        2 -> gs._2
        3 -> gs._3
        4 -> gs._4
        5 -> gs._5
        6 -> gs._6
        7 -> gs._7
        8 -> gs._8
        9 -> gs._9
        else -> gs.on_error
    }

    fun generateAllCards(fieldWidth:Int, fieldHeight:Int, questionLength:Int): List<Card> {
        val allPositions: Set<Set<Cell>> = generateAllPositions(fieldWidth, fieldHeight)

        fun randIndexes(len:Int, idxUntil:Int):Set<Int> {
            val res = HashSet<Int>()
            while (res.size < len) {
                res.add(Random.nextInt(0,idxUntil))
            }
            return res
        }

        val questions: Set<List<Cell>> = allPositions.asSequence().map {
            val rndIndexes = randIndexes(questionLength,fieldWidth)
            it.filterIndexed {i,cell -> rndIndexes.contains(i)}
        }.toSet()

        return questions.asSequence().map { question: List<Cell> -> Card(
            question = question.sortedBy { it.x },
            answers = allPositions.asSequence()
                .filter { it.containsAll(question) }
                .map { it.filter { !question.contains(it) }.sortedBy { it.x } }
                .sortedBy { it[0].y }
                .toList()
        ) }.toList()
    }

    fun generateAllPositions(fieldWidth:Int, fieldHeight:Int): Set<Set<Cell>> {
        fun intersect(a:Cell,b:Cell): Boolean {
            val dx = a.x - b.x
            val dy = a.y - b.y
            return a.x == b.x || a.y == b.y || dx == dy || dx == -dy
        }

        fun intersectsAny(ys:List<Int>,y:Int): Boolean {
            val newCell = Cell(ys.size, y)
            return ys.asSequence().mapIndexed { i, y -> Cell(i,y)  }.find { intersect(it, newCell) } != null
        }

        fun generate(prev:List<Int>):Sequence<List<Int>> {
            if (prev.size == fieldWidth) {
                return sequenceOf(prev)
            } else {
                return generateSequence(0) { it+1 }
                    .take(fieldHeight)
                    .filter { !intersectsAny(prev, it) }
                    .flatMap { y -> generate(prev + y) }
            }
        }

        return generate(emptyList()).map { it.asSequence().mapIndexed{ i,y -> Cell(i,y) }.toSet() }.toSet()
    }

    data class Card(val question:List<Cell>, val answers:List<List<Cell>>)
}