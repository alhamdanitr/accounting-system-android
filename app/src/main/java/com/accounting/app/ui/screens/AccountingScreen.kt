package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("المحاسبة ودفتر الأستاذ (General Ledger)", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ملخص ميزان المراجعة
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("إجمالي الأصول النقدية والبنكية", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("24,850.00 $", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                }
            }

            Text("حسابات الأستاذ العام", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            val ledgerAccounts = listOf(
                LedgerAccount("الصندوق الرئيسي (النقدية)", "صندوق فرعي", "8,450.00 $", true),
                LedgerAccount("حساب البنك التجاري", "بنك", "12,400.00 $", true),
                LedgerAccount("مديونيات العملاء (الذمم)", "أصول متداولة", "4,100.00 $", true),
                LedgerAccount("حساب الموردين والمشتريات", "خصوم متداولة", "-2,500.00 $", false),
                LedgerAccount("مصروفات التشغيل والرواتب", "مصروفات", "1,200.00 $", false)
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ledgerAccounts) { acc ->
                        LedgerRow(acc)
                    }
                }
            }
        }
    }
}

data class LedgerAccount(val name: String, val category: String, val balance: String, val isPositive: Boolean)

@Composable
fun LedgerRow(acc: LedgerAccount) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(acc.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(acc.category, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(acc.balance, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (acc.isPositive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        }
    }
}
