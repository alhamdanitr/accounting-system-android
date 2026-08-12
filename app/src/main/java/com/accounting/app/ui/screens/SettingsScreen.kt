package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var serverUrl by remember { mutableStateOf("https://accounting-system-backend-production-97e3.up.railway.app") }
    var printerName by remember { mutableStateOf("POS-80 Thermal Printer") }
    var isSyncEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("الإعدادات والتهيئة", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            Spacer(Modifier.height(Spacing.xs))

            // إعدادات الخادم
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "الربط السحابي")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
                ) {
                    Column(modifier = Modifier.padding(Spacing.lg), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                        OutlinedTextField(
                            value = serverUrl,
                            onValueChange = { serverUrl = it },
                            label = { Text("رابط الخادم (Backend URL)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("المزامنة التلقائية", style = MaterialTheme.typography.titleSmall)
                                Text("تحديث البيانات في الخلفية", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = isSyncEnabled, onCheckedChange = { isSyncEnabled = it })
                        }
                    }
                }
            }

            // إعدادات الطابعة
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "الأجهزة والملحقات")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
                ) {
                    Column(modifier = Modifier.padding(Spacing.lg), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                        OutlinedTextField(
                            value = printerName,
                            onValueChange = { printerName = it },
                            label = { Text("طابعة الفواتير الافتراضية") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        )
                        PrimaryActionButton(
                            text = "طباعة فاتورة اختبار",
                            onClick = { /* TODO: Test print */ },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = Icons.Default.Print
                        )
                    }
                }
            }

            // حول النظام
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SectionHeader(title = "حول النظام")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(Spacing.lg), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("نظام المحاسبة والمخزون المؤسسي", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("الإصدار 2.0.0 (Enterprise)", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(Spacing.md))
                        Text("تم تطويره بواسطة Manus AI لصالح شركة الحمداني", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            
            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}
