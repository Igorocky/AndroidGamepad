package org.igye.android.gamepad

import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

class MainActivityViewModel(
    private val appContext: Context,
    private val gameSelector: GameSelector,
    private val gameSounds: GameSounds,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): WebViewViewModel("ViewSelector") {
    private val lastInputTime = AtomicLong(System.currentTimeMillis())
    private val warnInactivityDurationMillis = 10 * 60 * 1000
    private val shutdownInactivityDurationMillis = (1 * 60 * 1000) + warnInactivityDurationMillis
    val closeActivity: AtomicReference<() -> Unit> = AtomicReference(null)

    init {
        viewModelScope.launch(defaultDispatcher) {
            while (isActive) {
                delay(10_000)
                val inactivityDuration = System.currentTimeMillis() - lastInputTime.get()
                if (shutdownInactivityDurationMillis < inactivityDuration) {
                    closeActivity.get()?.invoke()
                } else if (warnInactivityDurationMillis < inactivityDuration) {
                    gameSounds.play(gameSounds.on_go_to_start3)
                }
            }
        }
    }

    fun onKeyDown(userInput: UserInput) = viewModelScope.launch(defaultDispatcher) {
        lastInputTime.set(System.currentTimeMillis())
        gameSelector.onUserInput(userInput)
    }

    override fun getWebView1(): WebView {
        return getWebView(appContext, listOf(this))
    }
}
