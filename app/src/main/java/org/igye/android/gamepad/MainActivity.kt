package org.igye.android.gamepad

import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : WebViewActivity<MainActivityViewModel>() {
    override val viewModel: MainActivityViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return (application as AndroidGamepadApp).appContainer.createMainActivityViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        viewModel.closeActivity.set { this.runOnUiThread {
            finish()
        } }
    }

    override fun onDestroy() {
        viewModel.closeActivity.set { null }
        super.onDestroy()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onGenericMotionEvent(event: MotionEvent): Boolean {
        if (event.source and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
            val x = event.getX(0)
            val y = event.getY(0)
            val keyCode = if (x < -0.2) {
                Constants.GAMEPAD_BUTTON_LEFT
            } else if (x > 0.2) {
                Constants.GAMEPAD_BUTTON_RIGHT
            } else if (y < -0.2) {
                Constants.GAMEPAD_BUTTON_UP
            } else if (y > 0.2) {
                Constants.GAMEPAD_BUTTON_DOWN
            } else {
                null
            }
            if (keyCode != null) {
                viewModel.onKeyDown(UserInput(keyCode = keyCode, eventTime = event.eventTime))
            }
            return true
        } else {
            return super.onGenericMotionEvent(event)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.source and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD) {
            viewModel.onKeyDown(UserInput(keyCode = keyCode, eventTime = event.eventTime))
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }
}

