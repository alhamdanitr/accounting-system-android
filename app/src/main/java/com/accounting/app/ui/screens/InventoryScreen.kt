package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class InventoryItem(
    val id: String,
    val name: String,
    val sku: String,
    val stock: Double,
    val warehouse: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val items = listOf(
        InventoryItem("1", "راوتر ميكروتيك RB951", "SKU-951", 15.0, "المحل الرئيسي"),
        InventoryItem("2", "كاميرا مراقبة داهوا 2MP", "SKU-CAM2", 8.0, "المستودع المركزي"),
        InventoryItem("3", "سويتش شبكة 8 مخرج تي بي لينك", "SKU-SW8", 22.0, "المحل الرئيسي")
    )

    val filteredItems = items.filter { it.name.contains(searchQuery, ignoreCase = true) || it.sku.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إدارة المخزون والمستودعات") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Add, contentDescription = "إضافة منتج")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("بحث بالاسم أو الباركود أو SKU") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(item.name, style = MaterialTheme.typography.titleMedium)
                            Text("SKU: ${item.sku} | المستودع: ${item.warehouse}", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("الرصيد الحالي: ${item.stock}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
