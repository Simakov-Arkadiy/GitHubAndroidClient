package com.example.githubandroidclient.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceContainerHighest = SurfaceContainerHighest,
    onSurface = OnSurface,
    error = Error,
)

@Composable
fun GitHubAndroidClientTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}