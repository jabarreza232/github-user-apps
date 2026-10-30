package id.co.evolution.mygithubuserapps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.co.evolution.mygithubuserapps.feature.users.presentation.UserDetailScreen
import id.co.evolution.mygithubuserapps.feature.users.presentation.UserListScreen
import id.co.evolution.mygithubuserapps.feature.users.presentation.viewmodel.UserListViewModel
import id.co.evolution.mygithubuserapps.ui.theme.MyGithubUserAppsTheme

object NavRoute {
    const val USER_LIST = "user_list"
    const val USER_DETAIL = "user_detail/{username}"

    fun createDetailRoute(username: String): String = "user_detail/$username"
}

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModelUserList: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModelUserList.uiState.collectAsStateWithLifecycle()

    MyGithubUserAppsTheme(darkTheme = uiState.isDarkTheme) {
        NavHost(
            navController = navController,
            startDestination = NavRoute.USER_LIST,
            modifier = modifier
        ) {
            val onEvent = viewModelUserList::onEvent
            composable(NavRoute.USER_LIST) {
                UserListScreen(
                    uiState = uiState,
                    onEvent = onEvent,
                    onUserClick = { username ->
                        navController.navigate(NavRoute.createDetailRoute(username))
                    }
                )
            }

            composable(
                route = NavRoute.USER_DETAIL,
                arguments = listOf(
                    navArgument("username") { type = NavType.StringType }
                )
            ) {
                UserDetailScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
