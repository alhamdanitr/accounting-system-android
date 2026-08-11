package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("لوحة التحكم والمؤشرات الرئيسية") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardCard(
                    title = "مبيعات اليوم",
                    value = "1,450.00 $",
                    modifier = Modifier.weight(1f)
                )
                DashboardCard(
                    title = "عدد الفواتير",
                    value = "24 فاتورة",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardCard(
                    title = "إجمالي المصروفات",
                    value = "120.00 $",
                    modifier = Modifier.weight(1f)
                )
                DashboardCard(
                    title = "تنبيهات المخزون",
                    value = "3 أصناف منخفضة",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        }
    }
}
