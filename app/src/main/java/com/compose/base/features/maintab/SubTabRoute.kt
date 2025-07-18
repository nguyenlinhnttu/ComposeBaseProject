package com.compose.base.features.maintab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.compose.base.R
import com.compose.base.navigation.HOME_TAB
import com.compose.base.navigation.MY_DATA_TAB
import kotlinx.serialization.Serializable

@Serializable
sealed interface SubTabRoute {
    val route: String
    val title: Int
    val iconUnselect: ImageVector
    val iconSelected: ImageVector
    var isShowIconNew: Boolean

    companion object {
        val ALL_TABS: List<SubTabRoute> = listOf(
            TabHome,
            TabMyData,
        )
    }
}

@Serializable
data object TabHome : SubTabRoute {
    override val route = HOME_TAB
    override val iconUnselect = Icons.Default.Home
    override val iconSelected = Icons.Default.Home
    override val title = R.string.tab_home
    override var isShowIconNew: Boolean = false
}

@Serializable
data object TabMyData : SubTabRoute {
    override val route = MY_DATA_TAB
    override val iconUnselect = Icons.Default.AccountCircle
    override val iconSelected = Icons.Default.AccountCircle
    override val title = R.string.tab_my_data
    override var isShowIconNew: Boolean = false
}
