package org.igye.android.gamepad

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GameSounds(
    private val appContext: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val soundPool: SoundPool
    private val durations = HashMap<Int,Int>()
    val _1: Int
    val _2: Int
    val _3: Int
    val _4: Int
    val _5: Int
    val _6: Int
    val _7: Int
    val _8: Int
    val a: Int
    val b: Int
    val c: Int
    val d: Int
    val e: Int
    val f: Int
    val g: Int
    val h: Int
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

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()
        _1 = loadSound(R.raw._1)
        _2 = loadSound(R.raw._2)
        _3 = loadSound(R.raw._3)
        _4 = loadSound(R.raw._4)
        _5 = loadSound(R.raw._5)
        _6 = loadSound(R.raw._6)
        _7 = loadSound(R.raw._7)
        _8 = loadSound(R.raw._8)
        a = loadSound(R.raw.a)
        b = loadSound(R.raw.b)
        c = loadSound(R.raw.c)
        d = loadSound(R.raw.d)
        e = loadSound(R.raw.e)
        f = loadSound(R.raw.f)
        g = loadSound(R.raw.g)
        h = loadSound(R.raw.h)
        on_backspace = loadSound(R.raw.on_backspace)
        on_enter = loadSound(R.raw.on_enter)
        on_enter2 = loadSound(R.raw.on_enter2)
        on_error = loadSound(R.raw.on_error)
        on_escape = loadSound(R.raw.on_escape)
        on_go_to_end = loadSound(R.raw.on_go_to_end)
        on_go_to_end_teleport = loadSound(R.raw.on_go_to_end_teleport)
        on_go_to_start = loadSound(R.raw.on_go_to_start)
        on_go_to_start2 = loadSound(R.raw.on_go_to_start2)
        on_go_to_start3 = loadSound(R.raw.on_go_to_start3)
        on_next = loadSound(R.raw.on_next)
        on_prev = loadSound(R.raw.on_prev)
    }

    suspend fun play(seq: Array<Int>) = withContext(defaultDispatcher) {
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