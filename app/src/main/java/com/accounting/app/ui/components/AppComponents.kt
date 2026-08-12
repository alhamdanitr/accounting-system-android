package com.accounting.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.ui.theme.*

/**
 * مكتبة المكوّنات المشتركة
 * -------------------------
 * كانت كل شاشة سابقًا تُعرِّف بطاقاتها وأزرارها من الصفر بأرقام وألوان مختلفة قليلًا في كل مرة
 * (مثال: KpiCard كانت معرّفة فقط داخل DashboardScreen ولا يمكن إعادة استخدامها في شاشة أخرى).
 * هذه المكوّنات تُستخدم في كل الشاشات لضمان اتساق الشكل، وتسهّل أي تعديل مستقبلي على التصميم
 * (تغيير نصف قطر الزوايا مثلاً في مكان واحد بدل 11 شاشة).
 */

// -------- عنوان قسم موحّد --------
@Composable
fun SectionHeader(
    title: String,
    action: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
        if (action != null && onActionClick != null) {
            TextButton(onClick = onActionClick) {
                Text(action, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

// -------- شارة حالة دلالية (مكتمل / معلّق / متأخر ...) --------
enum class StatusTone { POSITIVE, NEGATIVE, WARNING, NEUTRAL, INFO }

@Composable
fun StatusBadge(text: String, tone: StatusTone) {
    val (bg, fg) = when (tone) {
        StatusTone.POSITIVE -> SemanticIncomeBg to SemanticIncome
        StatusTone.NEGATIVE -> SemanticExpenseBg to SemanticExpense
        StatusTone.WARNING -> SemanticWarningBg to SemanticWarning
        StatusTone.NEUTRAL -> SemanticNeutralBg to SemanticNeutral
        StatusTone.INFO -> SemanticInfoBg to SemanticInfo
    }
    Surface(shape = RoundedCornerShape(Radius.pill), color = bg) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = fg,
            modifier = Modifier.padding(horizontal = Spacing.sm, vertical = 4.dp)
        )
    }
}

// -------- بطاقة مؤشر أداء (KPI) موحّدة، قابلة لإعادة الاستخدام في أي شاشة --------
data class KpiData(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val tone: StatusTone = StatusTone.INFO,
    val trend: String? = null // مثال: "+12.4%" — اختياري
)

@Composable
fun KpiCard(kpi: KpiData, modifier: Modifier = Modifier) {
    val (bg, fg) = when (kpi.tone) {
        StatusTone.POSITIVE -> SemanticIncomeBg to SemanticIncome
        StatusTone.NEGATIVE -> SemanticExpenseBg to SemanticExpense
        StatusTone.WARNING -> SemanticWarningBg to SemanticWarning
        StatusTone.NEUTRAL -> SemanticNeutralBg to SemanticNeutral
        StatusTone.INFO -> SemanticInfoBg to SemanticInfo
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.card)
    ) {
        Column(modifier = Modifier.padding(Spacing.cardPadding)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                Surface(shape = RoundedCornerShape(Radius.sm), color = bg, modifier = Modifier.size(32.dp)) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(kpi.icon, contentDescription = null, tint = fg, modifier = Modifier.size(18.dp))
                    }
                }
                Text(
                    kpi.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
            Spacer(Modifier.height(Spacing.sm))
            Text(kpi.value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
            if (kpi.trend != null) {
                Spacer(Modifier.height(2.dp))
                Text(kpi.trend, style = MaterialTheme.typography.labelSmall, color = fg)
            }
        }
    }
}

// -------- حالة فارغة موحّدة (بدل الشاشة البيضاء الصامتة عند عدم وجود بيانات) --------
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    subtitle: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(Spacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(48.dp))
        Spacer(Modifier.height(Spacing.md))
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(Spacing.xs))
        Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = Spacing.lg)
        )
        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(Spacing.lg))
            Button(onClick = onAction) { Text(actionLabel) }
        }
    }
}

// -------- زر أساسي بارتفاع لمس ثابت (48dp) لتفادي أزرار صغيرة يصعب الضغط عليها --------
@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(Spacing.touchTarget),
        shape = MaterialTheme.shapes.medium
    ) {
        if (leadingIcon != null) {
            Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(Spacing.xs))
        }
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}
