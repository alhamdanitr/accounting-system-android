package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.data.remote.DailySaleResponse
import com.accounting.app.data.remote.DailySalesReportResponse
import com.accounting.app.data.remote.NetworkModule
import com.accounting.app.ui.components.SectionHeader
import com.accounting.app.ui.theme.Spacing
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {
    val tenantId = NetworkModule.sessionStore.tenantId
    var report by remember { mutableStateOf<DailySalesReportResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val reportDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()) }

    LaunchedEffect(tenantId, reportDate) {
        if (tenantId.isNullOrBlank()) {
            isLoading = false
            errorMessage = "لا توجد شركة مرتبطة بالجلسة الحالية"
            return@LaunchedEffect
        }
        isLoading = true
        errorMessage = null
        runCatching {
            val warehouse = NetworkModule.apiService.getWarehouses(tenantId).firstOrNull()
                ?: error("لا توجد مستودعات نشطة للشركة الحالية")
            NetworkModule.apiService.getDailySalesReport(tenantId, warehouse.id, reportDate)
        }
            .onSuccess { report = it }
            .onFailure { errorMessage = it.message ?: "تعذر تحميل تقرير المبيعات" }
        isLoading = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("التقارير والتحليلات", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            Spacer(Modifier.height(Spacing.xs))
            SectionHeader(title = "تقرير المبيعات اليومية", action = report?.warehouse?.name ?: "جاري التحميل")
            when {
                isLoading -> Box(Modifier.fillMaxWidth().padding(vertical = Spacing.xl), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                errorMessage != null -> Box(Modifier.fillMaxWidth().padding(vertical = Spacing.xl), contentAlignment = Alignment.Center) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                }
                report != null -> DailySalesContent(report!!)
            }
        }
    }
}

@Composable
private fun DailySalesContent(report: DailySalesReportResponse) {
    val summary = report.summary
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
        Text("التاريخ: ${report.date} — المستودع: ${report.warehouse.name}", style = MaterialTheme.typography.bodyMedium)
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(Spacing.lg),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SummaryMetric("الفواتير", summary.count.toString())
                SummaryMetric("المبيعات", "${summary.totalRevenue} ${report.warehouse.code}")
                SummaryMetric("المتبقي", summary.totalDue.toString())
            }
        }
        if (report.sales.isEmpty()) {
            Text("لا توجد فواتير مبيعات لهذا اليوم.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.fillMaxWidth()) {
                items(report.sales, key = { it.id }) { sale -> DailySaleRow(sale) }
            }
        }
    }
}

@Composable
private fun SummaryMetric(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DailySaleRow(sale: DailySaleResponse) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(sale.invoiceNumber, style = MaterialTheme.typography.titleSmall)
                Text(sale.customer?.name ?: "عميل نقدي", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(sale.grandTotal.toString(), fontWeight = FontWeight.Bold)
                Text(sale.paymentType, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
