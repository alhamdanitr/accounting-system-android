package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
fun VouchersScreen() {
    var selectedVoucherTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("السندات والقيود المالية", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: إنشاء سند جديد */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "سند جديد", tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(
                selectedTabIndex = selectedVoucherTab,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(selected = selectedVoucherTab == 0, onClick = { selectedVoucherTab = 0 }, text = { Text("سندات القبض", fontWeight = FontWeight.SemiBold) })
                Tab(selected = selectedVoucherTab == 1, onClick = { selectedVoucherTab = 1 }, text = { Text("سندات الصرف", fontWeight = FontWeight.SemiBold) })
                Tab(selected = selectedVoucherTab == 2, onClick = { selectedVoucherTab = 2 }, text = { Text("القيود المزدوجة", fontWeight = FontWeight.SemiBold) })
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.screenPadding),
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                Spacer(Modifier.height(Spacing.xs))
                
                SectionHeader(
                    title = when(selectedVoucherTab) {
                        0 -> "سجل المقبوضات النقدية"
                        1 -> "سجل المدفوعات والمصاريف"
                        else -> "دفتر اليومية المساعد"
                    },
                    action = "تصفية"
                )

                val vouchersList = when (selectedVoucherTab) {
                    0 -> listOf(
                        VoucherItem("REC-501", "استلام دفعة من شركة النور", "450.00 $", StatusTone.POSITIVE, "12/08/2026"),
                        VoucherItem("REC-502", "تحويل بنكي - مبيعات جملة", "1,200.00 $", StatusTone.POSITIVE, "11/08/2026")
                    )
                    1 -> listOf(
                        VoucherItem("PAY-301", "شراء كابلات شبكة CAT6", "250.00 $", StatusTone.NEGATIVE, "12/08/2026"),
                        VoucherItem("PAY-302", "تسديد فاتورة إنترنت المعرض", "120.00 $", StatusTone.NEGATIVE, "10/08/2026")
                    )
                    else -> listOf(
                        VoucherItem("JRN-101", "قيد تسوية مخزون MikroTik", "3,100.00 $", StatusTone.INFO, "09/08/2026"),
                        VoucherItem("JRN-102", "قيد إهلاك أجهزة السيرفر", "450.00 $", StatusTone.WARNING, "01/08/2026")
                    )
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.weight(1f)) {
                    items(vouchersList) { voucher ->
                        VoucherRow(voucher)
                    }
                    item { Spacer(Modifier.height(Spacing.xxl)) }
                }
            }
        }
    }
}

data class VoucherItem(val code: String, val description: String, val amount: String, val tone: StatusTone, val date: String)

@Composable
fun VoucherRow(item: VoucherItem) {
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
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.code, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(Spacing.sm))
                    Text(item.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.height(2.dp))
                Text(item.description, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.amount, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                StatusBadge(
                    text = when(item.tone) {
                        StatusTone.POSITIVE -> "مقبوض"
                        StatusTone.NEGATIVE -> "مدفوع"
                        else -> "مرحّل"
                    },
                    tone = item.tone
                )
            }
        }
    }
}
