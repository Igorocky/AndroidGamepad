package org.igye.android.gamepad

import org.igye.android.gamepad.UserInput.*

data class MorseTreeNode(val userInput: UserInput?, val dotChild: MorseTreeNode?, val dashChild: MorseTreeNode?)

object Morse {

    val root: MorseTreeNode = createMorseTreeNode("")!!

    private fun createMorseTreeNode(path:String): MorseTreeNode? {
        val dotChildPath = path + "."
        val dashChildPath = path + "-"
        val userInput = codeToUserInput(path)
        val dotChildren = userInputsByPrefix(dotChildPath)
        val dashChildren = userInputsByPrefix(dashChildPath)
        if (userInput == null && dotChildren.isEmpty() && dashChildren.isEmpty()) {
            return null
        } else {
            return MorseTreeNode(
                userInput = userInput,
                dotChild = createMorseTreeNode(dotChildPath),
                dashChild = createMorseTreeNode(dashChildPath)
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
            "-----" -> _0
            ".----" -> _1
            "..---" -> _2
            "...--" -> _3
            "....-" -> _4
            "....." -> _5
            "-...." -> _6
            "--..." -> _7
            "---.." -> _8
            "----." -> _9
            ".-" -> A
            "-..." -> B
            "-.-." -> C
            "-.." -> D
            "." -> E
            "..-." -> F
            "--." -> G
            "...." -> H
            ".." -> I
            ".---" -> J
            "-.-" -> K
            ".-.." -> L
            "--" -> M
            "-." -> N
            "---" -> O
            ".--." -> P
            "--.-" -> Q
            ".-." -> R
            "..." -> S
            "-" -> T
            "..-" -> U
            "...-" -> V
            ".--" -> W
            "-..-" -> X
            "-.--" -> Y
            "--.." -> Z
            else -> null
        }
    }

    private fun userInputToCode(userInput: UserInput): String? {
        return when (userInput) {
            _0 -> "-----"
            _1 -> ".----"
            _2 -> "..---"
            _3 -> "...--"
            _4 -> "....-"
            _5 -> "....."
            _6 -> "-...."
            _7 -> "--..."
            _8 -> "---.."
            _9 -> "----."
            A -> ".-"
            B -> "-..."
            C -> "-.-."
            D -> "-.."
            E -> "."
            F -> "..-."
            G -> "--."
            H -> "...."
            I -> ".."
            J -> ".---"
            K -> "-.-"
            L -> ".-.."
            M -> "--"
            N -> "-."
            O -> "---"
            P -> ".--."
            Q -> "--.-"
            R -> ".-."
            S -> "..."
            T -> "-"
            U -> "..-"
            V -> "...-"
            W -> ".--"
            X -> "-..-"
            Y -> "-.--"
            Z -> "--.."
            else -> null
        }
    }

}