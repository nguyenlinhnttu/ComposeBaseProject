package com.compose.base.features.maintab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.base.navigation.HOME_TAB
import com.compose.base.navigation.MY_DATA_TAB
import kotlinx.coroutines.launch

@Composable
fun MainTabHostScreen(rootController: NavController) {
    val tabs = SubTabRoute.ALL_TABS
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    val tabHomeController = rememberNavController()
    val tabMyDataController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                windowInsets = WindowInsets(top = 0, bottom = 0),
            ) {
                tabs.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (index == pagerState.currentPage) item.iconSelected else item.iconUnselect,
                                contentDescription = null,
                            )
                        },
                        label = { Text(stringResource(item.title)) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                        ),
                    )
                }
            }
        },
    ) { _ ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize(),
        ) { page ->
            when (page) {
                0 -> SubTabNavTab(
                    rootController = rootController,
                    tabNavController = tabHomeController,
                    modifier = Modifier,
                    startDestination = HOME_TAB,
                )

                1 -> SubTabNavTab(
                    rootController = rootController,
                    tabNavController = tabMyDataController,
                    modifier = Modifier,
                    startDestination = MY_DATA_TAB,
                )

                else -> Box(
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        }
    }
}
