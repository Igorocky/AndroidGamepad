package org.igye.android.gamepad

import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val appContext: Context,
    gameSounds: GameSounds,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): WebViewViewModel("ViewSelector") {
    private val gs = gameSounds


    fun onKeyDown(keyCode: Int) = viewModelScope.launch(defaultDispatcher) {
        if (keyCode == Constants.GAMEPAD_BUTTON_UP) {
            gs.play(arrayOf(gs.a,gs.b,gs.c))
        } else if (keyCode == Constants.GAMEPAD_BUTTON_DOWN) {
            gs.play(arrayOf(gs.c,gs.b,gs.a))
        }
    }

    override fun getWebView1(): WebView {
        return getWebView(appContext, listOf(this))
    }
}
