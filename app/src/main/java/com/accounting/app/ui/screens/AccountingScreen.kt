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
                title = { Text("الإدارة المالية والمحاسبة", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // كارت ميزان الأرصدة الإجمالي
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("إجمالي الأرصدة النقدية", fontSize = 14.sp, color = Color.Gray)
                        Text("24,800.00 $", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("كشف الحسابات والصناديق", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

            val accounts = listOf(
                AccountItem("الصندوق الرئيسي", "8,450 $", "نقدية", Color(0xFF2E7D32)),
                AccountItem("البنك العربي", "12,200 $", "بنك", Color(0xFF1565C0)),
                AccountItem("مديونيات العملاء", "3,150 $", "ذمم مدينة", Color(0xFFEF6C00)),
                AccountItem("مصروفات تشغيلية", "1,000 $", "مصروفات", Color(0xFFC62828))
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(accounts) { account ->
                    AccountCard(account)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("سند قبض")
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) {
                    Icon(Icons.Default.Remove, contentDescription = null)
                    Text("سند صرف")
                }
            }
        }
    }
}

data class AccountItem(val name: String, val balance: String, val type: String, val color: Color)

@Composable
fun AccountCard(account: AccountItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(4.dp, 40.dp).background(account.color, RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(account.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(account.type, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Text(account.balance, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = account.color)
        }
    }
}
