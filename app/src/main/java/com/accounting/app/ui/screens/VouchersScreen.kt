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
import androidx.compose.material3.TopAppBarDefaults.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VouchersScreen() {
    var selectedVoucherTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("السندات والقيود المالية (Vouchers & Journal Entries)", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Create voucher action */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "سند جديد", tint = MaterialTheme.colorScheme.onPrimary)
            }
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
            TabRow(selectedTabIndex = selectedVoucherTab, containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.primary) {
                Tab(selected = selectedVoucherTab == 0, onClick = { selectedVoucherTab = 0 }, text = { Text("سندات القبض", fontWeight = FontWeight.Bold) })
                Tab(selected = selectedVoucherTab == 1, onClick = { selectedVoucherTab = 1 }, text = { Text("سندات الصرف", fontWeight = FontWeight.Bold) })
                Tab(selected = selectedVoucherTab == 2, onClick = { selectedVoucherTab = 2 }, text = { Text("القيود المزدوجة", fontWeight = FontWeight.Bold) })
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)).padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("رقم السند / القيد", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.2f))
                        Text("البيان والوصف", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(2f))
                        Text("المبلغ", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.2f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val vouchersList = when (selectedVoucherTab) {
                        0 -> listOf(
                            VoucherItem("REC-501", "استلام دفعة نقدية من شركة التميز", "450.00 $", true),
                            VoucherItem("REC-502", "تحويل بنكي من مؤسسة الأفق", "1,200.00 $", true)
                        )
                        1 -> listOf(
                            VoucherItem("PAY-301", "شراء أدوات صيانة شبكات ومعدات", "250.00 $", false),
                            VoucherItem("PAY-302", "تسديد فاتورة كهرباء المعرض", "120.00 $", false)
                        )
                        else -> listOf(
                            VoucherItem("JRN-101", "قيد تسوية المخزون الشهري", "3,100.00 $", true),
                            VoucherItem("JRN-102", "قيد إهلاك الأجهزة والمعدات", "450.00 $", false)
                        )
                    }

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(vouchersList) { voucher ->
                            VoucherRow(voucher)
                        }
                    }
                }
            }
        }
    }
}

data class VoucherItem(val code: String, val description: String, val amount: String, val isPositive: Boolean)

@Composable
fun VoucherRow(item: VoucherItem) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(item.code, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1.2f))
            Text(item.description, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(2f))
            Text(item.amount, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (item.isPositive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error, modifier = Modifier.weight(1.2f))
        }
    }
}
