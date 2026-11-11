package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BrainBlitzColorScheme = darkColorScheme(
  primary = ElectricCyan,
  onPrimary = DeepNavyDark,
  primaryContainer = DeepNavyCard,
  onPrimaryContainer = ElectricCyanGlow,
  secondary = SolarGold,
  onSecondary = DeepNavyDark,
  secondaryContainer = FlameOrange.copy(alpha = 0.2f),
  onSecondaryContainer = SolarGold,
  tertiary = NeonEmerald,
  onTertiary = DeepNavyDark,
  background = DeepNavyDark,
  onBackground = TextPrimary,
  surface = DeepNavySurface,
  onSurface = TextPrimary,
  surfaceVariant = DeepNavySurfaceVariant,
  onSurfaceVariant = TextSecondary,
  outline = DeepNavyCardBorder,
  outlineVariant = DeepNavyCardBorder.copy(alpha = 0.5f),
  error = NeonCoral,
  onError = Color.White
)

@Composable
fun BrainBlitzTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = BrainBlitzColorScheme,
    typography = Typography,
    content = content
  )
}

// Backward-compatible alias for existing templates
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  BrainBlitzTheme(content = content)
}
