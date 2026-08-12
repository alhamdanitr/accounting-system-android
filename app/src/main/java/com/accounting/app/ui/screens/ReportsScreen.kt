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
import androidx.compose.material3.TopAppBarDefaults.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("التقارير المالية والضريبية (Financial & Tax Reports)", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
            Text("التقارير المتاحة للتصدير الفوري (PDF / Excel)", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            val reports = listOf(
                ReportCategory("قائمة الدخل والأرباح والخسائر", "تقرير شامل للمبيعات والمصروفات وصافي الأرباح", Icons.Default.Assessment, "PDF / Excel"),
                ReportCategory("ميزان المراجعة والأستاذ العام", "أرصدة الحسابات والأصول والخصوم والذمم", Icons.Default.AccountBalance, "PDF / Excel"),
                ReportCategory("حركة المخزون والأصناف الراكدة", "كميات بضائع الشبكات ومعدات ميكروتيك المتاحة", Icons.Default.Inventory, "Excel"),
                ReportCategory("تقرير مبيعات نقاط البيع (POS)", "تفاصيل الفواتير اليومية وطرق الدفع والضرائب", Icons.Default.Receipt, "PDF / Excel")
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(reports) { report ->
                    ReportCard(report)
                }
            }
        }
    }
}

data class ReportCategory(val title: String, val description: String, val icon: ImageVector, val format: String)

@Composable
fun ReportCard(report: ReportCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(report.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Column {
                    Text(report.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(report.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Button(
                onClick = { /* Export action */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(report.format, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
