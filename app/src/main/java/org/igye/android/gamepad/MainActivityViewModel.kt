package org.igye.android.gamepad

import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val appContext: Context,
    private val gameSelector: GameSelector,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): WebViewViewModel("ViewSelector") {

    fun onKeyDown(userInput: UserInput) = viewModelScope.launch(defaultDispatcher) {
        gameSelector.onUserInput(userInput)
    }

    override fun getWebView1(): WebView {
        return getWebView(appContext, listOf(this))
    }
}
