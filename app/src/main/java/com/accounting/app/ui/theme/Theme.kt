package com.accounting.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ملاحظة: هذا الملف يحل محل EnterpriseTheme.kt القديم بنفس اسم الدالة EnterpriseTheme()
// حتى لا يحتاج بقية الكود لأي تعديل عند الاستبدال — فقط احذف EnterpriseTheme.kt القديم
// وضع هذا الملف مكانه.

private val LightColors = lightColorScheme(
    primary = BrandBlue500,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = BrandBlue100,
    onPrimaryContainer = BrandBlue900,
    secondary = SemanticIncome,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = SemanticIncomeBg,
    onSecondaryContainer = SemanticIncome,
    error = SemanticExpense,
    onError = Color(0xFFFFFFFF),
    errorContainer = SemanticExpenseBg,
    onErrorContainer = SemanticExpense,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceAlt,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder
)

private val DarkColors = darkColorScheme(
    primary = BrandBlue400,
    onPrimary = Color(0xFF06162B),
    primaryContainer = BrandBlue700,
    onPrimaryContainer = BrandBlue100,
    secondary = Color(0xFF34D399),
    onSecondary = Color(0xFF06301F),
    secondaryContainer = Color(0xFF0F3D2B),
    onSecondaryContainer = Color(0xFF34D399),
    error = Color(0xFFF87171),
    onError = Color(0xFF350707),
    errorContainer = Color(0xFF4A1414),
    onErrorContainer = Color(0xFFF87171),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceAlt,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder
)

@Composable
fun EnterpriseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
