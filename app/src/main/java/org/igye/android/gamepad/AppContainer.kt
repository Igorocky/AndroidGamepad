package org.igye.android.gamepad

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppContainer(
    private val appContext: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    val gameSounds = GameSounds(appContext, defaultDispatcher)

    fun createMainActivityViewModel(): MainActivityViewModel {
        return MainActivityViewModel(
            appContext = appContext,
            gameSounds = gameSounds
        )
    }
}