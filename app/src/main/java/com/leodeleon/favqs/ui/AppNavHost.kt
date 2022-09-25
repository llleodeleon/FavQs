package com.leodeleon.favqs.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leodeleon.favqs.presentation.LoginViewModel

@Composable
fun AppNavHost(
    modifier: Modifier,
    loginVM: LoginViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val mainRoute = if (loginVM.isLoggedIn) NavRoutes.Quotes else NavRoutes.Login

    fun onClickTag(tag: String) {
        navController.navigate(NavRoutes.Tag + "/$tag") {
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash,
        modifier = modifier
    ) {
        composable(NavRoutes.Splash) {
            SplashScreen(onNext = {
                navController.navigate(mainRoute)
            })
        }
        composable(NavRoutes.Login) {
            LoginScreen(viewModel = loginVM, onNext = {
                navController.navigate(NavRoutes.Quotes)
            })
        }
        composable(NavRoutes.Search) {
            SearchScreen(navigationIcon = {
                BackArrow(navController = navController)
            })
        }
        composable(
            route = NavRoutes.Tag + "/{${NavArgs.Tag}}",
            arguments = listOf(navArgument(NavArgs.Tag) { type = NavType.StringType })
        ) { entry ->
            val tag = entry.arguments?.getString(NavArgs.Tag) ?: return@composable
            TagScreen(tag = tag, navigationIcon = {
                BackArrow(navController = navController)
            })
        }
        composable(NavRoutes.Quotes) {
            QuotesScreen(
                onClickSearch = {
                    navController.navigate(NavRoutes.Search)
                },
                onClickTag = ::onClickTag,
            )
        }
    }
}

@Composable
fun BackArrow(navController: NavHostController) {
    IconButton(onClick = { navController.navigateUp() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )
    }
}

object NavRoutes {
    const val Login = "login"
    const val Splash = "splash"
    const val Search = "search"
    const val Quotes = "quotes"
    const val Tag = "tag"
}

object NavArgs {
    const val Tag = "tag"
}