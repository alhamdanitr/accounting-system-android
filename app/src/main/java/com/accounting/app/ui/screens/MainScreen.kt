package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf(
        NavigationItem("الرئيسية", Icons.Default.Dashboard),
        NavigationItem("نقطة البيع", Icons.Default.PointOfSale),
        NavigationItem("المخازن", Icons.Default.Inventory),
        NavigationItem("العملاء", Icons.Default.People),
        NavigationItem("السندات", Icons.Default.Receipt),
        NavigationItem("التحويلات", Icons.Default.SwapHoriz),
        NavigationItem("التقارير", Icons.Default.Assessment),
        NavigationItem("الإعدادات", Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title, style = MaterialTheme.typography.labelSmall) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedItem) {
                0 -> DashboardScreen()
                1 -> POSScreen()
                2 -> InventoryScreen()
                3 -> CustomersSuppliersScreen()
                4 -> VouchersScreen()
                5 -> StockTransferScreen()
                6 -> ReportsScreen()
                7 -> SettingsScreen()
            }
        }
    }
}

data class NavigationItem(val title: String, val icon: ImageVector)
