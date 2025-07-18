package com.compose.base.common.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.base.MainActivity
import com.compose.base.common.utils.SimpleAlertDialog
import com.compose.base.ui.component.CustomTopAppBar
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    screenName: String? = null,
    baseViewModel: BaseViewModel? = null,
    isLoading: StateFlow<Boolean>? = null,
    heightLine: Dp = 0.5.dp,
    toolbarColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    isRadiusScreen: Boolean = false,
    isDarkIcons: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    btnLeft: (@Composable () -> Unit)? = null,
    btnRight: (@Composable () -> Unit)? = null,
    contentView: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    var messageError by remember { mutableStateOf<String?>(null) }

    // --- Error Handling ---
    baseViewModel?.let {
        val errorState by remember { it.error }.collectAsState()
        LaunchedEffect(errorState) {
            errorState?.let { errorMessage ->
                messageError = when (errorState) {
                    is String -> errorState as String
                    is Int -> context.getString(errorState as Int)
                    else -> null
                }
                baseViewModel.setError(null)
            }
        }
    }
    // --- Loading State Handling ---
    LaunchedEffect(Unit) {
        isLoading?.let {
            val activity = context as? MainActivity
            it.collectLatest { loading ->
                activity?.setLoading(loading)
            }
        }
    }
    // --- Styling ---
    val finalModifier = modifier
        .then(
            if (isRadiusScreen) {
                Modifier
                    .background(Color.Gray)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            } else {
                Modifier
            },
        )
    SetStatusBarStyle(isDarkIcons = isDarkIcons)
    Scaffold(
        modifier = finalModifier,
        containerColor = backgroundColor,
        topBar = {
            screenName?.let {
                CustomTopAppBar(
                    title = it,
                    backgroundColor = toolbarColor,
                    onBackClick = onBackClick,
                    heightLine = heightLine,
                    actions = {
                        btnLeft?.invoke()
                        btnRight?.invoke()
                    },
                )
            }
        },
        content = { insets ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(insets),
            ) {
                contentView.invoke()
            }
            messageError?.let {
                SimpleAlertDialog(content = it) {
                    messageError = null
                }
            }
        },
    )
}
