package org.igye.android.gamepad

import org.igye.android.gamepad.UserInput.*

data class Code4TreeNode(
    val userInput: UserInput?,
    val child0: Code4TreeNode?,
    val child1: Code4TreeNode?,
    val child2: Code4TreeNode?,
    val child3: Code4TreeNode?,
    )

object Code4 {
    val root: Code4TreeNode = createCode4TreeNode("")!!

    suspend fun sayCode(code: String, gameSounds: GameSoundsI) {
        gameSounds.play(*(code.map { Utils.digitToSoundId(it, gameSounds) }.toIntArray()))
    }

    private fun createCode4TreeNode(path:String): Code4TreeNode? {
        val child0Path = path + "0"
        val child1Path = path + "1"
        val child2Path = path + "2"
        val child3Path = path + "3"
        val userInput = codeToUserInput(path)
        val children0 = userInputsByPrefix(child0Path)
        val children1 = userInputsByPrefix(child1Path)
        val children2 = userInputsByPrefix(child2Path)
        val children3 = userInputsByPrefix(child3Path)
        if (userInput == null && children0.isEmpty() && children1.isEmpty() && children2.isEmpty() && children3.isEmpty()) {
            return null
        } else {
            return Code4TreeNode(
                userInput = userInput,
                child0 = createCode4TreeNode(child0Path),
                child1 = createCode4TreeNode(child1Path),
                child2 = createCode4TreeNode(child2Path),
                child3 = createCode4TreeNode(child3Path),
            )
        }
    }

    private fun userInputsByPrefix(prefix: String): List<String> {
        return UserInput.values().asSequence()
            .map { userInputToCode(it) }
            .filter { it != null }
            .map { it!! }
            .filter { it!!.startsWith(prefix) }
            .toList()
    }

    private fun codeToUserInput(code: String): UserInput? {
        return when (code) {
            "000" -> _0
            "001" -> _1
            "002" -> _2
            "003" -> _3
            "010" -> _4
            "011" -> _5
            "012" -> _6
            "013" -> _7
            "020" -> _8
            "021" -> _9
            "022" -> A
            "023" -> B
            "030" -> C
            "031" -> D
            "032" -> E
            "033" -> F
            "100" -> G
            "101" -> H
            "102" -> I
            "103" -> J
            "110" -> K
            "111" -> L
            "112" -> M
            "113" -> N
            "120" -> O
            "121" -> P
            "122" -> Q
            "123" -> R
            "130" -> S
            "131" -> T
            "132" -> U
            "133" -> V
            "200" -> W
            "201" -> X
            "202" -> Y
            "203" -> Z
            "210" -> null
            "211" -> null
            "212" -> null
            "213" -> null
            "220" -> null
            "221" -> null
            "222" -> null
            "223" -> null
            "230" -> null
            "231" -> null
            "232" -> null
            "233" -> null
            "300" -> null
            "301" -> null
            "302" -> null
            "303" -> null
            "310" -> null
            "311" -> null
            "312" -> null
            "313" -> null
            "320" -> null
            "321" -> null
            "322" -> null
            "323" -> null
            "330" -> null
            "331" -> null
            "332" -> null
            "333" -> null
            else -> null
        }
    }

    fun userInputToCode(userInput: UserInput): String? {
        return when (userInput) {
            _0 -> "000"
            _1 -> "001"
            _2 -> "002"
            _3 -> "003"
            _4 -> "010"
            _5 -> "011"
            _6 -> "012"
            _7 -> "013"
            _8 -> "020"
            _9 -> "021"
            A -> "022"
            B -> "023"
            C -> "030"
            D -> "031"
            E -> "032"
            F -> "033"
            G -> "100"
            H -> "101"
            I -> "102"
            J -> "103"
            K -> "110"
            L -> "111"
            M -> "112"
            N -> "113"
            O -> "120"
            P -> "121"
            Q -> "122"
            R -> "123"
            S -> "130"
            T -> "131"
            U -> "132"
            V -> "133"
            W -> "200"
            X -> "201"
            Y -> "202"
            Z -> "203"
            else -> null
        }
    }

}