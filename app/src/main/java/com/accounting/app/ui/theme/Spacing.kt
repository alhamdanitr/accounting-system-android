package com.accounting.app.ui.theme

import androidx.compose.ui.unit.dp

/**
 * مقياس المسافات (Spacing Scale)
 * --------------------------------
 * كل الـ padding والـ margin وأبعاد الفراغات في التطبيق تُؤخذ من هنا.
 * هذا يمنع الفوضى الحالية حيث كل شاشة تستخدم أرقامًا عشوائية (14.dp, 16.dp, 20.dp بلا نظام).
 */
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 24.dp
    val xxl = 32.dp
    val xxxl = 48.dp

    // أبعاد ثابتة لعناصر متكررة
    val screenPadding = lg
    val cardPadding = lg
    val cardGap = md
    val iconButtonSize = 40.dp
    val touchTarget = 48.dp   // أقل حجم قابل للمس حسب معايير Material لسهولة الاستخدام باللمس
}

object Radius {
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val pill = 100.dp
}

object Elevation {
    val flat = 0.dp
    val card = 1.dp
    val raised = 4.dp
    val modal = 8.dp
}
