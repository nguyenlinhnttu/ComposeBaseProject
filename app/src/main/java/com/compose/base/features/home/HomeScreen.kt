package com.compose.base.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.compose.base.common.base.BaseScreen
import com.compose.base.common.extension.getMainActivity
import com.compose.base.common.extension.reactiveClick

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    BaseScreen(
        modifier = Modifier,
        screenName = "Home",
        toolbarColor = Color.Red,
        baseViewModel = null,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "HomeScreen",
                modifier = Modifier.reactiveClick {
                    context.getMainActivity()?.setLoading(true)
                },
                fontSize = 24.sp,
            )
        }
    }
}
