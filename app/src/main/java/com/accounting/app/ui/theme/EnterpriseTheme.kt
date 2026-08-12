package com.accounting.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val EnterpriseDarkColorScheme = darkColorScheme(
    primary = Color(0xFF3B82F6), // Industrial Blue
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = Color(0xFF10B981), // Success Green
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFF0F172A), // Deep Slate Navy
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFF94A3B8),
    error = Color(0xFFEF4444),
    onError = Color(0xFFFFFFFF)
)

val EnterpriseLightColorScheme = lightColorScheme(
    primary = Color(0xFF2563EB), // Professional Navy/Blue
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEFF6FF),
    onPrimaryContainer = Color(0xFF1E40AF),
    secondary = Color(0xFF059669), // Emerald Green
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFF1F5F9), // Light Corporate Gray
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF64748B),
    error = Color(0xFFDC2626),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun EnterpriseTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EnterpriseDarkColorScheme else EnterpriseLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
