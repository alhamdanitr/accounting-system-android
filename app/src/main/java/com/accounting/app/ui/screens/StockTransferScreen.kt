package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockTransferScreen() {
    var sourceWarehouse by remember { mutableStateOf("المستودع الرئيسي") }
    var targetWarehouse by remember { mutableStateOf("مستودع المعرض") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("تحويل المخزون والتسوية", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: عملية تحويل جديدة */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.SwapHoriz, contentDescription = "تحويل جديد", tint = MaterialTheme.colorScheme.onPrimary)
            }
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
            
            // بطاقة مسار التحويل
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
            ) {
                Column(modifier = Modifier.padding(Spacing.lg), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    Text("مسار التحويل الحالي", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("من مستودع", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(sourceWarehouse, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                            Text("إلى مستودع", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(targetWarehouse, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "سجل العمليات الأخيرة", action = "تصفية")

                val transfers = listOf(
                    TransferItem("TRF-1002", "راوتر ميكروتيك RB951", "5 قطع", StatusTone.POSITIVE, "12/08/2026"),
                    TransferItem("TRF-1001", "سويتش تي بي لينك 24 بورت", "2 قطعة", StatusTone.POSITIVE, "10/08/2026"),
                    TransferItem("ADJ-504", "كاميرا داهوا (مرتجع)", "1 قطعة", StatusTone.WARNING, "09/08/2026")
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.weight(1f)) {
                    items(transfers) { transfer ->
                        TransferRow(transfer)
                    }
                    item { Spacer(Modifier.height(Spacing.xxl)) }
                }
            }
        }
    }
}

data class TransferItem(val code: String, val itemName: String, val quantity: String, val tone: StatusTone, val date: String)

@Composable
fun TransferRow(item: TransferItem) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.lg).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                Text(item.code, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(item.itemName, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
                Text(item.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.quantity, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                StatusBadge(
                    text = if (item.tone == StatusTone.POSITIVE) "مكتمل" else "قيد المعالجة",
                    tone = item.tone
                )
            }
        }
    }
}
