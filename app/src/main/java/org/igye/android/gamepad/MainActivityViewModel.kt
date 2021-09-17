package org.igye.android.gamepad

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.webkit.WebView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainActivityViewModel(
    private val appContext: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): WebViewViewModel("ViewSelector") {
    private val soundPool: SoundPool
    private val onEnterSoundId: Int

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()
        onEnterSoundId = soundPool.load(appContext, R.raw.on_enter, 1)
    }

    fun onKeyDown(keyCode: Int) = viewModelScope.launch(defaultDispatcher) {
        log.info("event.keyCode=${keyCode}")
        soundPool.play(onEnterSoundId,1.0f,1.0f,0, 0, 1.0f)
    }

    override fun getWebView1(): WebView {
        return getWebView(appContext, listOf(this))
    }
}
