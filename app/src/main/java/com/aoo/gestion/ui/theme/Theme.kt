package com.aoo.gestion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AooPrimaryLight,
    onPrimary = AooBackground,
    secondary = AooSuccess,
    background = AooBackgroundDark,
    surface = AooSurfaceDark,
    error = AooError
)

private val LightColorScheme = lightColorScheme(
    primary = AooPrimary,
    onPrimary = AooSurface,
    primaryContainer = AooPrimaryLight,
    secondary = AooSuccess,
    background = AooBackground,
    surface = AooSurface,
    error = AooError
)

@Composable
fun AsociacionOdontologicaOccidenteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
