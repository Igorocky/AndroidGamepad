package org.igye.android.gamepad

import junit.framework.TestCase

class MockGameSounds: GameSoundsI {
    private val playedSounds = ArrayList<Int>()

    override suspend fun play(vararg seq: Int) {
        seq.forEach(playedSounds::add)
    }

    fun getPlayedSounds(): ArrayList<Int> {
        val result = ArrayList(playedSounds)
        playedSounds.clear()
        return result
    }

    fun assertPlayed(vararg expectedSeq: Int) {
        val expected = expectedSeq.asSequence().map(idToName::get).toList()
        val actual = getPlayedSounds().asSequence().map(idToName::get).toList()
        TestCase.assertEquals(
            expected,
            actual
        )
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
    override val on_error: Int = nextId("on_error")
    override val on_escape: Int = nextId("on_escape")
    override val on_go_to_end: Int = nextId("on_go_to_end")
    override val on_go_to_end_teleport: Int = nextId("on_go_to_end_teleport")
    override val on_go_to_start: Int = nextId("on_go_to_start")
    override val on_go_to_start2: Int = nextId("on_go_to_start2")
    override val on_go_to_start3: Int = nextId("on_go_to_start3")
    override val on_next: Int = nextId("on_next")
    override val on_prev: Int = nextId("on_prev")

    override val zero: Int = nextId("zero")
    override val one: Int = nextId("one")
    override val two: Int = nextId("two")
    override val three: Int = nextId("three")
    override val four: Int = nextId("four")
    override val five: Int = nextId("five")
    override val six: Int = nextId("six")
    override val seven: Int = nextId("seven")
    override val eight: Int = nextId("eight")
    override val nine: Int = nextId("nine")
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
}