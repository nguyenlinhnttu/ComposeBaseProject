package com.compose.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.compose.base.features.home.HomeScreen
import com.compose.base.features.mydata.MyDataScreen

@Composable
fun TabNavGraph(
    rootController: NavController,
    tabNavController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = tabNavController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(HOME_TAB) {
            HomeScreen()
        }
        composable(MY_DATA_TAB) {
            MyDataScreen()
        }
    }
}
