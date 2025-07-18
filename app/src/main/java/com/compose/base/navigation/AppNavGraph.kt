package com.compose.base.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.compose.base.common.extension.slideComposable
import com.compose.base.features.account.login.LoginScreen
import com.compose.base.features.maintab.MainTabHostScreen
import com.compose.base.features.splash.SplashScreen

@Composable
fun AppNavGraph(rootController: NavHostController) {
    NavHost(navController = rootController, startDestination = LOGIN) {
        composable(SPLASH) {
            SplashScreen(onOpenMain = {
                rootController.navigate(MAIN_TAB)
            }, onOpenLogin = {
                rootController.navigate(LOGIN)
            })
        }
        slideComposable(MAIN_TAB) { MainTabHostScreen(rootController) }
        composable(LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    rootController.navigate(MAIN_TAB)
                },
                onRegisterClick = {
                    rootController.navigate(MAIN_TAB)
                },
            )
        }
    }
}
