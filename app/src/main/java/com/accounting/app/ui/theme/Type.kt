package com.accounting.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.accounting.app.R

/**
 * خط التطبيق
 * -----------
 * الخط الحالي هو خط النظام الافتراضي (Roboto)، وهو خط لاتيني لا يُصمَّم بعناية للعربية:
 * التشكيل غير متزن، وسماكة الحروف العربية تبدو مختلفة عن سماكة الأرقام والحروف اللاتينية بجانبها.
 *
 * الحل: استخدام خط عربي مصمَّم لواجهات الأنظمة الاقتصادية/المحاسبية مثل "Cairo" أو "IBM Plex Sans Arabic"،
 * وكلاهما مجاني من Google Fonts ويحتوي أوزانًا متعددة (Regular/Medium/SemiBold/Bold) وهو أساسي لإظهار
 * الفروقات في التسلسل الهرمي (عناوين/قيم مالية بارزة/نصوص ثانوية).
 *
 * خطوات التفعيل (مطلوبة قبل البناء):
 * 1) نزّل ملفات الخط من https://fonts.google.com/specimen/Cairo (Regular, Medium, SemiBold, Bold)
 * 2) ضعها في app/src/main/res/font/ بالأسماء: cairo_regular.ttf, cairo_medium.ttf,
 *    cairo_semibold.ttf, cairo_bold.ttf
 * 3) هذا الملف سيعمل تلقائيًا بعد ذلك عبر R.font.*
 *
 * إلى أن تُضاف الملفات، استبدل AppFontFamily مؤقتًا بـ FontFamily.Default حتى لا يفشل البناء.
 */
// val AppFontFamily = FontFamily(
//     Font(R.font.cairo_regular, FontWeight.Normal),
//     Font(R.font.cairo_medium, FontWeight.Medium),
//     Font(R.font.cairo_semibold, FontWeight.SemiBold),
//     Font(R.font.cairo_bold, FontWeight.Bold)
// )
val AppFontFamily = FontFamily.Default

// أرقام مالية: نستخدم خطًا أحادي المسافة رقميًا (tabular figures) عبر letterSpacing محسوب
// حتى تصطف الأرقام في الجداول والفواتير عموديًا بدل أن "ترقص" بين الصفوف.
val AppTypography = Typography(
    displaySmall = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 42.sp),
    headlineMedium = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 34.sp),
    headlineSmall = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleLarge = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp),
    titleMedium = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 22.sp),
    titleSmall = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 18.sp),
    labelLarge = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontFamily = AppFontFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 14.sp)
)

// نمط مخصص للأرقام المالية الكبيرة (غير موجود في Typography الافتراضي في Material3)
val FinancialFigureLarge = TextStyle(
    fontFamily = AppFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 30.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.2.sp
)
