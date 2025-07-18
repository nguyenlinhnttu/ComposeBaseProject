package com.compose.base.navigation
// 📌 METHOD 1 – Passing data via arguments in the route
// Pros: Clear, easy to debug, suitable for simple data types like String, Int
// How to use:
// 1. Define a route pattern, e.g., "MainScreen/{userId}"
// 2. When navigating: navController.navigate("MainScreen/$userId")
// 3. In the composable: read the argument via backStackEntry.arguments?.getString("userId")
// Example:
/*
        composable(
        route = MAIN_WITH_ARG,
        arguments = listOf(navArgument("userId") {
            type = NavType.StringType
        })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            MainTabScreen(userId = userId)
        }
*/

// 📌 METHOD 2 – Passing data via savedStateHandle
// Pros: Good for temporarily passing data between screens without needing route arguments
// How to use:
// 1. Set the data: navController.currentBackStackEntry?.savedStateHandle?.set("key", value)
// 2. Retrieve the data: backStackEntry.savedStateHandle.get<String>("key")
// Example:
/*
    navController.currentBackStackEntry?.savedStateHandle?.set("userId", userId)
    navController.navigate(MAIN)
    val userId = backStackEntry.savedStateHandle.consume<String>("userId")
    Should save userId to viewmodel to use long time
*/
const val HOME_TAB = "HomeTabScreen"
const val MY_DATA_TAB = "MyDataTabScreen"
