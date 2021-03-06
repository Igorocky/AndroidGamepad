package org.igye.android.gamepad

import junit.framework.TestCase

class MockGameSounds: GameSoundsI {
    private val playedSounds = ArrayList<Int>()

    override fun play(vararg seq: Int): Long {
        seq.forEach(playedSounds::add)
        return 0
    }

    fun getPlayedSounds(): ArrayList<Int> {
        val result = ArrayList(playedSounds)
        playedSounds.clear()
        return result
    }

    fun showPlayedSounds(): List<String?> {
        return playedSounds.map(idToName::get)
    }

    fun assertPlayed(vararg expectedSeq: Int) {
        assertEquals(expectedSeq.toList(), getPlayedSounds())
    }

    fun assertEquals(expected: List<Int>, actual: List<Int>) {
        val expected = expected.map(idToName::get)
        val actual = actual.map(idToName::get)
        TestCase.assertEquals(expected, actual)
    }

    fun assertPlayedNothing() {
        TestCase.assertTrue(playedSounds.isEmpty())
    }

    private var id = 0
    private val idToName: MutableMap<Int,String> = HashMap()
    private fun nextId(name:String): Int {
        val res = id++
        idToName.put(res, name)
        return res
    }

    override val on_backspace: Int = nextId("on_backspace")
    override val on_enter: Int = nextId("on_enter")
    override val on_enter2: Int = nextId("on_enter2")
    override val on_enter_short: Int = nextId("on_enter_short")
    override val on_error: Int = nextId("on_error")
    override val on_escape: Int = nextId("on_escape")
    override val on_go_to_end: Int = nextId("on_go_to_end")
    override val on_go_to_end_teleport: Int = nextId("on_go_to_end_teleport")
    override val on_go_to_start: Int = nextId("on_go_to_start")
    override val on_go_to_start2: Int = nextId("on_go_to_start2")
    override val on_go_to_start3: Int = nextId("on_go_to_start3")
    override val on_next: Int = nextId("on_next")
    override val on_prev: Int = nextId("on_prev")
    override val pause200: Int = nextId("pause200")
    override val pause400: Int = nextId("pause400")

    override val _0: Int = nextId("zero")
    override val _1: Int = nextId("one")
    override val _2: Int = nextId("two")
    override val _3: Int = nextId("three")
    override val _4: Int = nextId("four")
    override val _5: Int = nextId("five")
    override val _6: Int = nextId("six")
    override val _7: Int = nextId("seven")
    override val _8: Int = nextId("eight")
    override val _9: Int = nextId("nine")
    override val a: Int = nextId("a")
    override val b: Int = nextId("b")
    override val c: Int = nextId("c")
    override val d: Int = nextId("d")
    override val e: Int = nextId("e")
    override val f: Int = nextId("f")
    override val g: Int = nextId("g")
    override val h: Int = nextId("h")

    override val alpha: Int = nextId("alpha")
    override val bravo: Int = nextId("bravo")
    override val charlie: Int = nextId("charlie")
    override val delta: Int = nextId("delta")
    override val echo: Int = nextId("echo")
    override val foxtrot: Int = nextId("foxtrot")
    override val golf: Int = nextId("golf")
    override val hotel: Int = nextId("hotel")
    override val india: Int = nextId("india")
    override val juliet: Int = nextId("juliet")
    override val kilo: Int = nextId("kilo")
    override val lima: Int = nextId("lima")
    override val mike: Int = nextId("mike")
    override val november: Int = nextId("november")
    override val oscar: Int = nextId("oscar")
    override val papa: Int = nextId("papa")
    override val quebec: Int = nextId("quebec")
    override val romeo: Int = nextId("romeo")
    override val sierra: Int = nextId("sierra")
    override val tango: Int = nextId("tango")
    override val uniform: Int = nextId("uniform")
    override val victor: Int = nextId("victor")
    override val whiskey: Int = nextId("whiskey")
    override val xRay: Int = nextId("xRay")
    override val yankee: Int = nextId("yankee")
    override val zulu: Int = nextId("zulu")

    override val cells: Int = nextId("cells")
    override val morse: Int = nextId("morse")
    override val code4: Int = nextId("code4")
    override val queens: Int = nextId("queens")
    override val question: Int = nextId("question")
    override val answer: Int = nextId("answer")
}