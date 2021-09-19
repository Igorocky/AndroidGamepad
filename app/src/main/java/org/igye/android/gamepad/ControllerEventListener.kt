package org.igye.android.gamepad

interface ControllerEventListener {
    suspend fun onControllerEvent(controllerEvent: ControllerEvent)
}