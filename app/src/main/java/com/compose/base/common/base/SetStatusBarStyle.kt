package com.compose.base.common.base

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarStyle(
    isDarkIcons: Boolean = true,
) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = isDarkIcons
    }
}
