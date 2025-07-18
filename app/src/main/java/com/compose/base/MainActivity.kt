package com.compose.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.rememberNavController
import com.compose.base.common.base.LoadingViewModel
import com.compose.base.common.utils.AppLogger
import com.compose.base.features.shared.SharedViewModel
import com.compose.base.navigation.AppNavGraph
import com.compose.base.ui.component.LoadingView
import com.compose.base.ui.theme.ComposeBaseProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val sharedViewModel: SharedViewModel by viewModels()
    private val loadingViewModel: LoadingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBaseProjectTheme {
                val isLoading = loadingViewModel.isLoading.collectAsState()
                val rootNavController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(rootNavController)
                    if (isLoading.value) {
                        Dialog(
                            onDismissRequest = {},
                            properties = DialogProperties(
                                dismissOnBackPress = false,
                                dismissOnClickOutside = false,
                                usePlatformDefaultWidth = false,
                            ),
                        ) {
                            LoadingView()
                        }
                    }
                }
            }
        }

        MyApplication.AppLifecycleObserver.isAppResumeFromForeground.observe(this) { isAppResumeFromForeground ->
            if (isAppResumeFromForeground) {
                AppLogger.i("MyActivity", "App resumed from background")
            } else {
                AppLogger.i("MyActivity", "App in background")
            }
        }
    }

    fun getSharedVM(): SharedViewModel? {
        return sharedViewModel
    }

    fun setLoading(isLoading: Boolean) {
        loadingViewModel.setLoading(isLoading)
    }
}
