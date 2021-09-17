package org.igye.android.gamepad

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : WebViewActivity<MainActivityViewModel>() {
    override val viewModel: MainActivityViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(applicationContext) as T
            }
        }
    }
}

