package org.igye.android.gamepad

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.io.File
import kotlin.random.Random

object Utils {
    private val gson = Gson()

    fun <E> isEmpty(col: Collection<E>?): Boolean = col?.isEmpty()?:true
    fun <E> isNotEmpty(col: Collection<E>?): Boolean = !isEmpty(col)

    fun nextRandomIdxByCounts(counts:IntArray): Int {
        if (counts.isEmpty()) {
            throw AndroidGamepadException("counts.isEmpty()")
        }
        val minCnt = counts.minOrNull()!!
        val indexesWithMinCnt = counts.asSequence().mapIndexed{ idx, e -> Pair(idx,e) }.filter { it.second == minCnt }.map { it.first }.toList()
        return indexesWithMinCnt[Random.nextInt(0,indexesWithMinCnt.size)]
    }

    fun nextSequentialIdxByCounts(counts:IntArray): Int {
        if (counts.isEmpty()) {
            throw AndroidGamepadException("counts.isEmpty()")
        }
        val minCnt = counts.minOrNull()!!
        return counts.asSequence().mapIndexed{ idx, e -> Pair(idx,e) }.filter { it.second == minCnt }.first().first
    }

    fun digitToSoundId(digit: Char, gameSounds: GameSoundsI): Int {
        return when(digit) {
            '0' -> gameSounds._0
            '1' -> gameSounds._1
            '2' -> gameSounds._2
            '3' -> gameSounds._3
            '4' -> gameSounds._4
            '5' -> gameSounds._5
            '6' -> gameSounds._6
            '7' -> gameSounds._7
            '8' -> gameSounds._8
            '9' -> gameSounds._9
            else -> gameSounds.on_error
        }
    }

    fun sayUserInput(userInput: UserInput, gs: GameSoundsI) {
        when(userInput) {
            UserInput.A -> gs.play(gs.alpha)
            UserInput.B -> gs.play(gs.bravo)
            UserInput.C -> gs.play(gs.charlie)
            UserInput.D -> gs.play(gs.delta)
            UserInput.E -> gs.play(gs.echo)
            UserInput.F -> gs.play(gs.foxtrot)
            UserInput.G -> gs.play(gs.golf)
            UserInput.H -> gs.play(gs.hotel)
            UserInput.I -> gs.play(gs.india)
            UserInput.J -> gs.play(gs.juliet)
            UserInput.K -> gs.play(gs.kilo)
            UserInput.L -> gs.play(gs.lima)
            UserInput.M -> gs.play(gs.mike)
            UserInput.N -> gs.play(gs.november)
            UserInput.O -> gs.play(gs.oscar)
            UserInput.P -> gs.play(gs.papa)
            UserInput.Q -> gs.play(gs.quebec)
            UserInput.R -> gs.play(gs.romeo)
            UserInput.S -> gs.play(gs.sierra)
            UserInput.T -> gs.play(gs.tango)
            UserInput.U -> gs.play(gs.uniform)
            UserInput.V -> gs.play(gs.victor)
            UserInput.W -> gs.play(gs.whiskey)
            UserInput.X -> gs.play(gs.xRay)
            UserInput.Y -> gs.play(gs.yankee)
            UserInput.Z -> gs.play(gs.zulu)
            UserInput._0 -> gs.play(gs._0)
            UserInput._1 -> gs.play(gs._1)
            UserInput._2 -> gs.play(gs._2)
            UserInput._3 -> gs.play(gs._3)
            UserInput._4 -> gs.play(gs._4)
            UserInput._5 -> gs.play(gs._5)
            UserInput._6 -> gs.play(gs._6)
            UserInput._7 -> gs.play(gs._7)
            UserInput._8 -> gs.play(gs._8)
            UserInput._9 -> gs.play(gs._9)
            else -> gs.play(gs.on_error)
        }
    }


    fun createMethodMap(jsInterfaces: List<Any>): Map<String, (defaultDispatcher:CoroutineDispatcher, String) -> Deferred<String>> {
        val resultMap = HashMap<String, (defaultDispatched:CoroutineDispatcher, String) -> Deferred<String>>()
        jsInterfaces.forEach{ jsInterface ->
            jsInterface.javaClass.methods.asSequence()
                .filter { it.getAnnotation(BeMethod::class.java) != null }
                .forEach { method ->
                    resultMap.put(method.name) { defaultDispatcher,argStr ->
                        CoroutineScope(defaultDispatcher).async {
                            var deferred: Deferred<*>? = null
                            val parameterTypes = method.parameterTypes
                            if (parameterTypes.isNotEmpty()) {
                                val argsDto = gson.fromJson(argStr, parameterTypes[0])
                                deferred = method.invoke(jsInterface, argsDto) as Deferred<*>
                            } else {
                                deferred = method.invoke(jsInterface) as Deferred<*>
                            }
                            gson.toJson(deferred.await())
                        }
                    }
                }
        }
        return resultMap.toMap()
    }

    private fun createDirIfNotExists(dir: File): File {
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }
}