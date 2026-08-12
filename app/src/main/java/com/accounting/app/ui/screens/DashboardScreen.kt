package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("لوحة التحكم الذكية", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // ملخص الأداء المالي العلوي
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("إجمالي مبيعات الشهر", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                    Text("12,450.00 $", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color.Green, modifier = Modifier.size(16.dp))
                        Text(" +15% زيادة عن الشهر الماضي", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("المؤشرات الرئيسية", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

            val stats = listOf(
                StatItem("فواتير اليوم", "24", Icons.Default.Receipt, MaterialTheme.colorScheme.secondary),
                StatItem("صافي الربح", "3,120 $", Icons.Default.Payments, Color(0xFF2E7D32)),
                StatItem("أصناف منخفضة", "5", Icons.Default.Inventory, Color(0xFFD32F2F)),
                StatItem("العملاء الجدد", "12", Icons.Default.GroupAdd, Color(0xFF0288D1))
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(stats) { stat ->
                    StatCard(stat)
                }
            }
        }
    }
}

data class StatItem(val title: String, val value: String, val icon: ImageVector, val color: Color)

@Composable
fun StatCard(stat: StatItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(stat.color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(stat.icon, contentDescription = null, tint = stat.color)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(stat.title, fontSize = 14.sp, color = Color.Gray)
            Text(stat.value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
