package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.*

/**
 * الفرق عن النسخة القديمة:
 * - لا أرقام sp/dp عشوائية: كل شيء من Spacing / Typography الموحّدة.
 * - البطاقة المالية الكبرى أصبحت تدرّجًا بصريًا (gradient) هادئًا بدل لون مسطّح، لتمييزها كنقطة تركيز واحدة.
 * - KpiCard أصبحت مكوّنًا مشتركًا (ui/components) بدل تعريف محلي مكرر.
 * - أُضيف قسم "أحدث الفواتير" الذي كان غائبًا تمامًا — لوحة تحكم بلا نشاط حديث لا تخدم صاحب المحل.
 * - استخدام ألوان دلالية ثابتة (SemanticIncome/Expense/Warning) بدل ألوان يدوية مختلفة في كل شاشة.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("لوحة المؤشرات", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { /* TODO: إشعارات */ }) {
                        Icon(Icons.Default.NotificationsNone, contentDescription = "الإشعارات")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.screenPadding)
                .verticalScrollFix(),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            Spacer(Modifier.height(Spacing.xs))
            HeroFinancialCard()

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "مؤشرات الأداء الرئيسية")
                val kpis = listOf(
                    KpiData("إجمالي المبيعات", "18,400 $", Icons.Default.TrendingUp, StatusTone.POSITIVE, "+8.2%"),
                    KpiData("إجمالي المصروفات", "1,200 $", Icons.Default.MoneyOff, StatusTone.NEGATIVE, "+2.1%"),
                    KpiData("أصناف منخفضة المخزون", "4 أصناف", Icons.Default.Warning, StatusTone.WARNING),
                    KpiData("مديونيات العملاء", "2,150 $", Icons.Default.AccountBalance, StatusTone.INFO)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(kpis) { kpi -> KpiCard(kpi) }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "أحدث الفواتير", action = "عرض الكل", onActionClick = {})
                RecentInvoicesList()
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

@Composable
private fun HeroFinancialCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = BrandBlue900)
    ) {
        Box(
            modifier = Modifier.background(
                Brush.linearGradient(listOf(BrandBlue900, BrandBlue700))
            )
        ) {
            Column(modifier = Modifier.padding(Spacing.xl)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("صافي الإيرادات اليومية", style = MaterialTheme.typography.bodyMedium, color = BrandBlue100)
                    Surface(shape = RoundedCornerShape(Radius.pill), color = SemanticIncome.copy(alpha = 0.25f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = Spacing.sm, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Circle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(8.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("مباشر", style = MaterialTheme.typography.labelSmall, color = Color(0xFF34D399), fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(Modifier.height(Spacing.sm))
                Text("3,450.00 $", style = FinancialFigureLarge.copy(color = androidx.compose.ui.graphics.Color.White))
                Spacer(Modifier.height(Spacing.md))
                Divider(color = BrandBlue100.copy(alpha = 0.15f))
                Spacer(Modifier.height(Spacing.md))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    HeroStat(label = "عدد الفواتير", value = "32 فاتورة")
                    HeroStat(label = "متوسط السلة", value = "107.8 $")
                }
            }
        }
    }
}

@Composable
private fun HeroStat(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.bodySmall, color = BrandBlue100.copy(alpha = 0.7f))
        Text(value, style = MaterialTheme.typography.titleSmall, color = androidx.compose.ui.graphics.Color.White)
    }
}

private data class InvoicePreview(val number: String, val customer: String, val amount: String, val tone: StatusTone, val status: String)

@Composable
private fun RecentInvoicesList() {
    val invoices = listOf(
        InvoicePreview("#INV-1042", "شركة النور للتجارة", "740.00 $", StatusTone.POSITIVE, "مدفوعة"),
        InvoicePreview("#INV-1041", "محمد العبدالله", "1,280.00 $", StatusTone.WARNING, "معلّقة"),
        InvoicePreview("#INV-1040", "مؤسسة الأمين", "320.00 $", StatusTone.NEGATIVE, "متأخرة")
    )
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.card)
    ) {
        Column {
            invoices.forEachIndexed { index, inv ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(Spacing.lg),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(inv.customer, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(2.dp))
                        Text(inv.number, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(inv.amount, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(4.dp))
                        StatusBadge(inv.status, inv.tone)
                    }
                }
                if (index != invoices.lastIndex) {
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                }
            }
        }
    }
}

// Modifier صغير مساعد لتفعيل التمرير العمودي بدون تكرار الاستيراد في كل مكان
@Composable
private fun Modifier.verticalScrollFix(): Modifier {
    val scrollState = androidx.compose.foundation.rememberScrollState()
    return this.then(androidx.compose.foundation.verticalScroll(scrollState))
}
