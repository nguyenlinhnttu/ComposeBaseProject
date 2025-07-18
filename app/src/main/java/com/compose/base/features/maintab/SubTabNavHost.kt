package com.compose.base.features.maintab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.compose.base.common.extension.logi
import com.compose.base.navigation.TabNavGraph

@Composable
fun SubTabNavTab(
    modifier: Modifier = Modifier,
    rootController: NavController,
    tabNavController: NavHostController,
    startDestination: String,
) {
    DisposableEffect(tabNavController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            "Navigated to: ${destination.route}".logi()
        }
        tabNavController.addOnDestinationChangedListener(listener)

        onDispose {
            tabNavController.removeOnDestinationChangedListener(listener)
        }
    }

    TabNavGraph(rootController, tabNavController, startDestination = startDestination, modifier = modifier)
}
