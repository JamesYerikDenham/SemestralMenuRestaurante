package com.example.semestral

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.semestral.data.SessionManager
import com.example.semestral.ui.LoginScreen
import com.example.semestral.ui.MenuScreen
import com.example.semestral.ui.RegisterScreen
import com.example.semestral.ui.navigation.Screens
import com.example.semestral.ui.theme.SemestralTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SemestralTheme {
                val navController = rememberNavController()
                NavigationController(navController)
            }
        }
    }
}

@Composable
fun NavigationController(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "loader"
    ) {
        // Pantalla temporal para validar sesión y redirigir
        composable("loader") {
            LaunchedEffect(Unit) {
                val token = SessionManager.obtenerToken(context)
                if (!token.isNullOrBlank()) {
                    navController.navigate(Screens.Menu.route) {
                        popUpTo("loader") { inclusive = true }
                    }
                } else {
                    navController.navigate(Screens.Login.route) {
                        popUpTo("loader") { inclusive = true }
                    }
                }
            }
        }

        composable(Screens.Login.route) {
            LoginScreen(navController)
        }

        composable(Screens.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screens.Menu.route) {
            MenuScreen(navController)
        }
    }
}
