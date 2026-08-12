package com.accounting.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * نظام الألوان (Design Tokens)
 * -----------------------------
 * كل الألوان المستخدمة في التطبيق تُعرَّف هنا فقط.
 * ممنوع كتابة Color(0xFF...) داخل أي Composable للشاشات — إذا احتجت لونًا جديدًا، أضِفه هنا أولاً.
 * هذا هو الفرق الأهم بين تطبيق "يبدو احترافيًا بالصدفة" وتطبيق له هوية بصرية متسقة.
 */

// -------- العلامة التجارية (Brand) --------
val BrandBlue900 = Color(0xFF0B2545)
val BrandBlue700 = Color(0xFF13315C)
val BrandBlue500 = Color(0xFF1E5AA8)
val BrandBlue400 = Color(0xFF2D74D6)
val BrandBlue100 = Color(0xFFE4EEFB)

// -------- ألوان دلالية (Semantic) --------
// يُستخدم نفس اللون دائمًا لنفس المعنى في كل الشاشات: الأخضر = دخل/إيجابي، الأحمر = مصروف/سلبي، البرتقالي = تنبيه
val SemanticIncome = Color(0xFF15803D)      // إيرادات / أرباح / حالة "مكتمل"
val SemanticIncomeBg = Color(0xFFE7F6EC)
val SemanticExpense = Color(0xFFDC2626)      // مصروفات / خسائر / حالة "متأخر"
val SemanticExpenseBg = Color(0xFFFDECEC)
val SemanticWarning = Color(0xFFB45309)      // مخزون منخفض / حالة "معلّق"
val SemanticWarningBg = Color(0xFFFEF3E2)
val SemanticInfo = Color(0xFF1E5AA8)         // معلومات عامة / روابط
val SemanticInfoBg = Color(0xFFE4EEFB)
val SemanticNeutral = Color(0xFF64748B)      // حالات محايدة (مسودة، غير مصنّف)
val SemanticNeutralBg = Color(0xFFF1F5F9)

// -------- سطح فاتح (Light Surfaces) --------
val LightBackground = Color(0xFFF6F8FB)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceAlt = Color(0xFFF0F3F8)      // لتمييز صفوف الجداول والبطاقات الثانوية
val LightBorder = Color(0xFFE2E8F0)
val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF64748B)
val LightTextTertiary = Color(0xFF94A3B8)

// -------- سطح داكن (Dark Surfaces) --------
val DarkBackground = Color(0xFF0B1220)
val DarkSurface = Color(0xFF141B2D)
val DarkSurfaceAlt = Color(0xFF1B2438)
val DarkBorder = Color(0xFF283349)
val DarkTextPrimary = Color(0xFFF3F6FB)
val DarkTextSecondary = Color(0xFFA6B1C4)
val DarkTextTertiary = Color(0xFF6B7690)
