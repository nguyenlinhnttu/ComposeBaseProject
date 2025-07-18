package com.compose.base

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner
import com.compose.base.common.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver)
        // Initialize any app-wide components here if necessary,
        // but prefer Hilt for dependency injection where possible.
    }

    object AppLifecycleObserver : DefaultLifecycleObserver {

        private val _isAppResumeFromForeground = MutableLiveData<Boolean>()
        val isAppResumeFromForeground: LiveData<Boolean> get() = _isAppResumeFromForeground

        private var isInBackground = false
        private var isFirstLaunch = true

        override fun onStart(owner: LifecycleOwner) {
            if (isFirstLaunch) {
                isFirstLaunch = false
                AppLogger.i("App launched for the first time", "AppLifecycleObserver")
            } else if (isInBackground) {
                AppLogger.i("App resumed from background", "AppLifecycleObserver")
                _isAppResumeFromForeground.value = true
            }
            isInBackground = false
        }

        override fun onStop(owner: LifecycleOwner) {
            isInBackground = true
            _isAppResumeFromForeground.value = false
            AppLogger.i("App moved to background", "AppLifecycleObserver")
        }
    }
}
