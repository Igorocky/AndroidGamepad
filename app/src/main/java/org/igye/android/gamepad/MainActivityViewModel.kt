package org.igye.android.gamepad

import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class MainActivityViewModel(private val appContext: Context): WebViewViewModel("ViewSelector") {

    override fun onCleared() {
        log.debug("Clearing")
        super.onCleared()
    }

    override fun getWebView1(): WebView {
        return getWebView(appContext, listOf(this))
    }
}
