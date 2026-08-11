package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("نقطة البيع (POS)") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* إضافة صنف */ }) {
                Icon(Icons.Default.Add, contentAlignment = null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "سلة المبيعات", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            // قائمة الأصناف المضافة للسلة (Placeholder)
            Box(modifier = Modifier.weight(1f)) {
                Text("لا توجد أصناف مضافة بعد")
            }

            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("الإجمالي:", style = MaterialTheme.typography.titleLarge)
                Text("0.00", style = MaterialTheme.typography.titleLarge)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* إتمام البيع */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("إتمام عملية البيع")
            }
        }
    }
}

private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentAlignment: Any?) {
    // Helper function for icon
}
