package com.aoo.gestion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aoo.gestion.ui.dashboard.DashboardScreen
import com.aoo.gestion.ui.login.LoginScreen
import com.aoo.gestion.ui.splash.SplashScreen

@Composable
fun AooNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AooDestinations.SPLASH) {
        composable(AooDestinations.SPLASH) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(AooDestinations.LOGIN) {
                        popUpTo(AooDestinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(AooDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AooDestinations.DASHBOARD) {
                        popUpTo(AooDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(AooDestinations.DASHBOARD) {
            DashboardScreen()
        }
    }
}
