package com.example.apptvseries.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Couleurs modernes et professionnelles
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Couleurs personnalisées pour l'app TV
val NetflixRed = Color(0xFFE50914)
val DeepBlue = Color(0xFF0F172A)
val ElectricBlue = Color(0xFF3B82F6)
val CinemaGold = Color(0xFFFFBF00)
val DarkGray = Color(0xFF1E293B)
val MidGray = Color(0xFF475569)
val LightGray = Color(0xFFF1F5F9)

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    primaryContainer = DeepBlue,
    onPrimaryContainer = ElectricBlue,

    secondary = CinemaGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF2D3748),
    onSecondaryContainer = CinemaGold,

    tertiary = NetflixRed,
    onTertiary = Color.White,

    background = DeepBlue,
    onBackground = Color.White,

    surface = DarkGray,
    onSurface = Color.White,
    surfaceVariant = MidGray,
    onSurfaceVariant = LightGray,

    outline = MidGray,
    outlineVariant = Color(0xFF334155),

    error = Color(0xFFFF6B6B),
    onError = Color.White,

    inverseSurface = LightGray,
    inverseOnSurface = DeepBlue,
    inversePrimary = ElectricBlue
)

private val LightColorScheme = lightColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDEAFE),
    onPrimaryContainer = Color(0xFF1E40AF),

    secondary = NetflixRed,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFEBEE),
    onSecondaryContainer = Color(0xFFB91C1C),

    tertiary = CinemaGold,
    onTertiary = Color.Black,

    background = Color.White,
    onBackground = Color.Black,

    surface = LightGray,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF475569),

    outline = Color(0xFFCBD5E1),
    outlineVariant = Color(0xFFE2E8F0),

    error = NetflixRed,
    onError = Color.White,

    inverseSurface = DeepBlue,
    inverseOnSurface = Color.White,
    inversePrimary = ElectricBlue
)

@Composable
fun TvSeriesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}