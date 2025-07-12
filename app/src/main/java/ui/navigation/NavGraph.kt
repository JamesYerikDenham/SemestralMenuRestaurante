package com.example.semestral.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestral.ui.LoginScreen
import com.example.semestral.ui.RegisterScreen
import com.example.semestral.ui.MenuScreen

// Definición de rutas centralizadas
sealed class Screens(val route: String) {
    object Login : Screens("login")
    object Register : Screens("register")
    object Menu : Screens("menu")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {
        composable(route = Screens.Login.route) {
            LoginScreen(navController)
        }

        composable(route = Screens.Register.route) {
            RegisterScreen(navController)
        }

        composable(route = Screens.Menu.route) {
            MenuScreen(navController)
        }
    }
}
