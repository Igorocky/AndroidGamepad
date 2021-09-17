package org.igye.android.gamepad

import android.app.Application

class AndroidGamepadApp: Application() {
    private val log = LoggerImpl("AndroidGamepadApp")
    val appContainer by lazy { AppContainer(appContext = applicationContext) }
}