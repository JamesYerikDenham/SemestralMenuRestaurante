package com.example.semestral.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.semestral.data.SessionManager
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menú del Restaurante",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Simulación de opciones del menú
        Text("🍕 Pizza Margherita")
        Text("🍔 Hamburguesa Clásica")
        Text("🥗 Ensalada César")
        Text("🍝 Pasta Carbonara")

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                scope.launch {
                    SessionManager.borrarToken(context)
                    Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("menu") { inclusive = true }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
