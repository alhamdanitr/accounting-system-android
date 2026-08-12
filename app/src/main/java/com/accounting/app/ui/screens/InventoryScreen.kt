package com.accounting.app.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إدارة المخازن والأصناف (Inventory Ledger)", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("بحث برمز الصنف SKU أو الباركود أو الوصف...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(8.dp)
            )

            // جدول بيانات الأصناف (Enterprise Table View)
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp)).padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("اسم الصنف", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(2f))
                        Text("الرمز (SKU)", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.5f))
                        Text("المخزون", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                        Text("السعر", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val stockItems = listOf(
                        StockRowItem("راوتر ميكروتيك RB951UiAS", "SKU-RB951", "14 قطعة", "55.00 $", false),
                        StockRowItem("كاميرا مراقبة داهوا 4MP", "SKU-CAM4", "3 قطع", "35.00 $", true),
                        StockRowItem("سويتش تي بي لينك 16 بورت", "SKU-SW16", "22 قطعة", "85.00 $", false),
                        StockRowItem("كابل شبكة CAT6 (لفة 300م)", "SKU-CAT6", "8 لفات", "120.00 $", false)
                    )

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(stockItems) { item ->
                            StockTableRow(item)
                        }
                    }
                }
            }
        }
    }
}

data class StockRowItem(val name: String, val sku: String, val stock: String, val price: String, val isLow: Boolean)

@Composable
fun StockTableRow(item: StockRowItem) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = if (item.isLow) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f) else Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(item.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(2f))
            Text(item.sku, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1.5f))
            Text(item.stock, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (item.isLow) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            Text(item.price, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
        }
    }
}
