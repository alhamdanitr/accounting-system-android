package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class AccountSummary(
    val title: String,
    val balance: String,
    val type: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingScreen() {
    val accounts = listOf(
        AccountSummary("الصندوق الرئيسي (النقد)", "1,250.00 $", "صندوق"),
        AccountSummary("حساب البنك التجاري", "5,400.00 $", "بنك"),
        AccountSummary("مديونيات العملاء", "850.00 $", "أصول"),
        AccountSummary("مصروفات الإيجار والكهرباء", "320.00 $", "مصروفات")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("المحاسبة والصناديق المالية") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("ملخص الأرصدة والحسابات", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(accounts) { acc ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(acc.title, style = MaterialTheme.typography.bodyLarge)
                                Text("النوع: ${acc.type}", style = MaterialTheme.typography.bodySmall)
                            }
                            Text(acc.balance, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* تسجيل سند قبض أو دفع */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("تسجيل حركة نقدية جديدة (قبض / دفع)")
            }
        }
    }
}
