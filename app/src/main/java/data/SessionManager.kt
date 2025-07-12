package com.example.semestral.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Extension para acceder al DataStore desde cualquier contexto
val Context.dataStore by preferencesDataStore(name = "sesion_usuario")

object SessionManager {

    private val TOKEN_KEY = stringPreferencesKey("token")

    // Guardar el token en DataStore
    suspend fun guardarToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Obtener el token (null si no existe)
    suspend fun obtenerToken(context: Context): String? {
        return context.dataStore.data
            .map { preferences -> preferences[TOKEN_KEY] ?: "" }
            .first()
    }

    // Eliminar el token (logout)
    suspend fun borrarToken(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}
