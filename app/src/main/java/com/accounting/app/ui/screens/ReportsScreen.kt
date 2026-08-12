package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("التقارير والتحليلات", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            Spacer(Modifier.height(Spacing.xs))
            
            SectionHeader(title = "التقارير الجاهزة للتصدير", action = "PDF / Excel")

            val reports = listOf(
                ReportCategory("قائمة الدخل والأرباح", "تحليل شامل للمبيعات والمصروفات وصافي الربح", Icons.Default.Assessment, "PDF"),
                ReportCategory("ميزان المراجعة", "أرصدة كافة الحسابات المدينة والدائنة", Icons.Default.AccountBalance, "Excel"),
                ReportCategory("حركة المخزون", "تقرير دوران الأصناف والكميات المتاحة", Icons.Default.Inventory, "Excel"),
                ReportCategory("إقرار ضريبة القيمة المضافة", "تفاصيل الضريبة المحصلة والمدفوعة للفترة", Icons.Default.ReceiptLong, "PDF")
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.md), modifier = Modifier.weight(1f)) {
                items(reports) { report ->
                    ReportCard(report)
                }
                item { Spacer(Modifier.height(Spacing.xxl)) }
            }
        }
    }
}

data class ReportCategory(val title: String, val description: String, val icon: ImageVector, val format: String)

@Composable
fun ReportCard(report: ReportCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.lg).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(Spacing.md),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(report.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    }
                }
                Column {
                    Text(report.title, style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(report.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            IconButton(
                onClick = { /* TODO: تصدير التقرير */ },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Icon(Icons.Default.FileDownload, contentDescription = "تنزيل", modifier = Modifier.size(20.dp))
            }
        }
    }
}
