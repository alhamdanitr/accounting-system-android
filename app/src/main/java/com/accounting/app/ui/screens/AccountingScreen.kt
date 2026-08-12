package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ChevronLeft
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingScreen() {
    // TODO: جلب البيانات الحقيقية من ApiService (accounting/accounts)
    // حالياً نستخدم بيانات تجريبية بنفس هيكلية الـ ERP الجديدة
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("المحاسبة ودفتر الأستاذ", style = MaterialTheme.typography.titleLarge) },
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
            
            // بطاقة الأصول الإجمالية
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(Spacing.lg).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("إجمالي الأصول النقدية", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("24,850.00 $", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Bold)
                    }
                    Surface(shape = RoundedCornerShape(Spacing.md), color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f), modifier = Modifier.size(48.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "شجرة الحسابات الرئيسية", action = "عرض الكل")
                
                val ledgerAccounts = listOf(
                    LedgerAccount("الصندوق الرئيسي (النقدية)", "نقدية", "8,450.00 $", StatusTone.POSITIVE),
                    LedgerAccount("حساب البنك التجاري", "بنك", "12,400.00 $", StatusTone.POSITIVE),
                    LedgerAccount("ذمم العملاء المدينة", "أصول متداولة", "4,100.00 $", StatusTone.INFO),
                    LedgerAccount("حساب الموردين", "خصوم متداولة", "-2,500.00 $", StatusTone.NEGATIVE),
                    LedgerAccount("مصروفات التشغيل", "مصروفات", "1,200.00 $", StatusTone.WARNING)
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.weight(1f)) {
                    items(ledgerAccounts) { acc ->
                        LedgerRow(acc)
                    }
                }
            }
        }
    }
}

data class LedgerAccount(val name: String, val category: String, val balance: String, val tone: StatusTone)

@Composable
fun LedgerRow(acc: LedgerAccount) {
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
                Text(acc.name, style = MaterialTheme.typography.titleSmall)
                Text(acc.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(acc.balance, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(Spacing.sm))
                Icon(Icons.AutoMirrored.Filled.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
            }
        }
    }
}
