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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersSuppliersScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إدارة العملاء والموردين (Accounts Ledger)", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new customer/supplier action */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "إضافة حساب", tint = MaterialTheme.colorScheme.onPrimary)
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
            // تبويبات التنقل بين العملاء والموردين
            TabRow(selectedTabIndex = selectedTab, containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.primary) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("العملاء (الذمم المدنية)", fontWeight = FontWeight.Bold) })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("الموردين (الذمم الدائنة)", fontWeight = FontWeight.Bold) })
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(if (selectedTab == 0) "بحث في العملاء (الاسم، الهاتف، الرقم الضريبي)..." else "بحث في الموردين...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(8.dp)
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)).padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(if (selectedTab == 0) "اسم العميل" else "اسم المورد", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(2f))
                        Text("رقم الهاتف", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.5f))
                        Text("الرصيد الحالي", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.2f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val accountsList = if (selectedTab == 0) {
                        listOf(
                            AccountItem("شركة التميز لتقنية المعلومات", "07701234567", "1,250.00 $ (مدين)", true),
                            AccountItem("مؤسسة الأفق للشبكات", "07809876543", "450.00 $ (مدين)", true),
                            AccountItem("مركز البغدادي للكمبيوتر", "07901122334", "0.00 $ (متزن)", false)
                        )
                    } else {
                        listOf(
                            AccountItem("شركة ميكروتيك العالمية (الوكيل)", "+371 67 357 000", "-3,400.00 $ (دائن)", false),
                            AccountItem("مؤسسة الفجر لقطع الشبكات", "07711223344", "-1,120.00 $ (دائن)", false),
                            AccountItem("توريدات تي بي لينك", "07822334455", "0.00 $ (متزن)", true)
                        )
                    }

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(accountsList) { account ->
                            AccountRow(account)
                        }
                    }
                }
            }
        }
    }
}

data class AccountItem(val name: String, val phone: String, val balance: String, val isPositive: Boolean)

@Composable
fun AccountRow(account: AccountItem) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(2f)) {
                Text(account.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(account.phone, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(account.phone, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1.5f))
            Text(account.balance, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (account.isPositive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error, modifier = Modifier.weight(1.2f))
        }
    }
}
