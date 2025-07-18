package com.compose.base.features.splash

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.compose.base.R

@Composable
fun SplashScreen(
    onOpenMain: () -> Unit,
    onOpenLogin: () -> Unit,
) {
    Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
}
