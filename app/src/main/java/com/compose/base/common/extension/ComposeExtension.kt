package com.compose.base.common.extension

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.compose.base.navigation.AnimationNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.reactiveClick(
    delayInMillis: Long = 500L,
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    var canClick = true
    clickable(
        enabled = enabled,
        indication = null,
        interactionSource = interactionSource,
    ) {
        if (canClick) {
            canClick = false
            onClick()
            CoroutineScope(Dispatchers.Main).launch {
                delay(delayInMillis)
                canClick = true
            }
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    clickable(
        interactionSource = interactionSource,
        indication = null,
    ) {
        onClick()
    }
}

fun NavController.backSafely() {
    if (this.previousBackStackEntry != null) {
        this.popBackStack()
    }
}

fun NavController.backSafely(route: String, inclusive: Boolean): Boolean {
    return if (this.previousBackStackEntry != null) {
        this.popBackStack(route, inclusive)
    } else {
        false
    }
}

fun NavGraphBuilder.slideComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route,
        arguments,
        enterTransition = AnimationNavigation.rightToLeftEnterTransition,
        popExitTransition = AnimationNavigation.leftToRightExitTransition,
        popEnterTransition = null,
        content = content,
    )
}

fun NavGraphBuilder.sheetComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route,
        arguments,
        enterTransition = AnimationNavigation.bottomToTopEnterTransition,
        popExitTransition = AnimationNavigation.topToBottomExitTransition,
        popEnterTransition = null,
        content = content,
    )
}
