package org.igye.android.gamepad

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

interface GameSoundsI {
    suspend fun play(vararg seq: Int)

    val on_backspace: Int
    val on_enter: Int
    val on_enter2: Int
    val on_error: Int
    val on_escape: Int
    val on_go_to_end: Int
    val on_go_to_end_teleport: Int
    val on_go_to_start: Int
    val on_go_to_start2: Int
    val on_go_to_start3: Int
    val on_next: Int
    val on_prev: Int

    val zero: Int
    val one: Int
    val two: Int
    val three: Int
    val four: Int
    val five: Int
    val six: Int
    val seven: Int
    val eight: Int
    val nine: Int
    val a: Int
    val b: Int
    val c: Int
    val d: Int
    val e: Int
    val f: Int
    val g: Int
    val h: Int

    val alpha: Int
    val bravo: Int
    val charlie: Int
    val delta: Int
    val echo: Int
    val foxtrot: Int
    val golf: Int
    val hotel: Int
    val india: Int
    val juliet: Int
    val kilo: Int
    val lima: Int
    val mike: Int
    val november: Int
    val oscar: Int
    val papa: Int
    val quebec: Int
    val romeo: Int
    val sierra: Int
    val tango: Int
    val uniform: Int
    val victor: Int
    val whiskey: Int
    val xRay: Int
    val yankee: Int
    val zulu: Int

    val cells: Int
    val morse: Int
}

class GameSounds(
    private val appContext: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): GameSoundsI {
    private val soundPool: SoundPool
    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()
    }
    private val durations = HashMap<Int,Int>()

//    val temp: Int = R.raw.on_error

    override val on_backspace: Int = loadSound(R.raw.on_backspace)
    override val on_enter: Int = loadSound(R.raw.on_enter)
    override val on_enter2: Int = loadSound(R.raw.on_enter2)
    override val on_error: Int = loadSound(R.raw.on_error)
    override val on_escape: Int = loadSound(R.raw.on_escape)
    override val on_go_to_end: Int = loadSound(R.raw.on_go_to_end)
    override val on_go_to_end_teleport: Int = loadSound(R.raw.on_go_to_end_teleport)
    override val on_go_to_start: Int = loadSound(R.raw.on_go_to_start)
    override val on_go_to_start2: Int = loadSound(R.raw.on_go_to_start2)
    override val on_go_to_start3: Int = loadSound(R.raw.on_go_to_start3)
    override val on_next: Int = loadSound(R.raw.on_next)
    override val on_prev: Int = loadSound(R.raw.on_prev)

    override val zero: Int = loadSound(R.raw.zero)
    override val one: Int = loadSound(R.raw.one)
    override val two: Int = loadSound(R.raw.two)
    override val three: Int = loadSound(R.raw.three)
    override val four: Int = loadSound(R.raw.four)
    override val five: Int = loadSound(R.raw.five)
    override val six: Int = loadSound(R.raw.six)
    override val seven: Int = loadSound(R.raw.seven)
    override val eight: Int = loadSound(R.raw.eight)
    override val nine: Int = loadSound(R.raw.nine)
    override val a: Int = loadSound(R.raw.a)
    override val b: Int = loadSound(R.raw.b)
    override val c: Int = loadSound(R.raw.c)
    override val d: Int = loadSound(R.raw.d)
    override val e: Int = loadSound(R.raw.e)
    override val f: Int = loadSound(R.raw.f)
    override val g: Int = loadSound(R.raw.g)
    override val h: Int = loadSound(R.raw.h)

    override val alpha: Int = loadSound(R.raw.alpha)
    override val bravo: Int = loadSound(R.raw.bravo)
    override val charlie: Int = loadSound(R.raw.charlie)
    override val delta: Int = loadSound(R.raw.delta)
    override val echo: Int = loadSound(R.raw.echo)
    override val foxtrot: Int = loadSound(R.raw.foxtrot)
    override val golf: Int = loadSound(R.raw.golf)
    override val hotel: Int = loadSound(R.raw.hotel)
    override val india: Int = loadSound(R.raw.india)
    override val juliet: Int = loadSound(R.raw.juliet)
    override val kilo: Int = loadSound(R.raw.kilo)
    override val lima: Int = loadSound(R.raw.lima)
    override val mike: Int = loadSound(R.raw.mike)
    override val november: Int = loadSound(R.raw.november)
    override val oscar: Int = loadSound(R.raw.oscar)
    override val papa: Int = loadSound(R.raw.papa)
    override val quebec: Int = loadSound(R.raw.quebec)
    override val romeo: Int = loadSound(R.raw.romeo)
    override val sierra: Int = loadSound(R.raw.sierra)
    override val tango: Int = loadSound(R.raw.tango)
    override val uniform: Int = loadSound(R.raw.uniform)
    override val victor: Int = loadSound(R.raw.victor)
    override val whiskey: Int = loadSound(R.raw.whiskey)
    override val xRay: Int = loadSound(R.raw.x_ray)
    override val yankee: Int = loadSound(R.raw.yankee)
    override val zulu: Int = loadSound(R.raw.zulu)

    override val cells: Int = loadSound(R.raw.cells)
    override val morse: Int = loadSound(R.raw.morse)



    override suspend fun play(vararg seq: Int) = withContext(defaultDispatcher) {
        for (soundId in seq) {
            if (durations.containsKey(soundId)) {
                soundPool.play(soundId,1.0f,1.0f,0, 0, 1.0f)
                delay(durations[soundId]!!.toLong())
            }
        }
    }

    private fun loadSound(resourceId: Int): Int {
        val player = MediaPlayer.create(appContext, resourceId)
        val soundId = soundPool.load(appContext, resourceId, 1)
        durations.put(soundId, player.duration)
        player.release()
        return soundId
    }

}