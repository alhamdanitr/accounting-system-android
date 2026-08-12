package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.accounting.app.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen() {
    var cartItems by remember { mutableStateOf(listOf<Pair<Product, Int>>()) }
    var searchQuery by remember { mutableStateOf("") }
    
    // حسابات مالية دقيقة
    val subtotal = cartItems.sumOf { it.first.salePrice.toDouble() * it.second }
    val taxTotal = cartItems.sumOf { (it.first.salePrice.toDouble() * it.second) * (it.first.taxRate / 100) }
    val grandTotal = subtotal + taxTotal

    Row(modifier = Modifier.fillMaxSize()) {
        // الجانب الأيمن: قائمة المنتجات والبحث (60% من الشاشة)
        Column(modifier = Modifier.weight(0.6f).padding(16.dp)) {
            Text("قائمة المنتجات", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("بحث بالاسم أو الباركود...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // عينات منتجات للتصميم الاحترافي
            val demoProducts = listOf(
                Product("1", "t1", "SKU001", "123", "راوتر ميكروتيك RB951", null, null, 40.0, 55.0, 5.0, 10.0),
                Product("2", "t1", "SKU002", "456", "كاميرا داهوا 4MP", null, null, 25.0, 35.0, 5.0, 20.0),
                Product("3", "t1", "SKU003", "789", "سويتش تي بي لينك 16 منفذ", null, null, 60.0, 85.0, 5.0, 5.0)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(demoProducts) { product ->
                    ProductCard(product) {
                        cartItems = if (cartItems.any { it.first.id == product.id }) {
                            cartItems.map { if (it.first.id == product.id) it.first to (it.second + 1) else it }
                        } else {
                            cartItems + (product to 1)
                        }
                    }
                }
            }
        }

        // الجانب الأيسر: ملخص الفاتورة والسلة (40% من الشاشة)
        Card(
            modifier = Modifier.weight(0.4f).fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("فاتورة المبيعات", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { (product, qty) ->
                        CartItemRow(product, qty, 
                            onIncrease = {
                                cartItems = cartItems.map { if (it.first.id == product.id) it.first to (it.second + 1) else it }
                            },
                            onDecrease = {
                                cartItems = if (qty > 1) {
                                    cartItems.map { if (it.first.id == product.id) it.first to (it.second - 1) else it }
                                } else {
                                    cartItems.filter { it.first.id != product.id }
                                }
                            }
                        )
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // ملخص الحسابات المالية
                FinancialSummaryRow("المجموع الفرعي:", "%.2f $".format(subtotal))
                FinancialSummaryRow("ضريبة القيمة المضافة:", "%.2f $".format(taxTotal))
                Spacer(modifier = Modifier.height(8.dp))
                FinancialSummaryRow("الإجمالي النهائي:", "%.2f $".format(grandTotal), isTotal = true)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { /* إتمام البيع */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("إتمام البيع وطباعة الفاتورة", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onAdd() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.arabicName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("SKU: ${product.sku} | المخزون: ${product.currentStock}", style = MaterialTheme.typography.bodySmall)
            }
            Text("%.2f $".format(product.salePrice.toDouble()), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onAdd) {
                Icon(Icons.Default.AddCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun CartItemRow(product: Product, qty: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(product.arabicName, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text("%.2f $".format(product.salePrice.toDouble() * qty), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(16.dp))
            }
            Text("$qty", modifier = Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Bold)
            IconButton(onClick = onIncrease, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun FinancialSummaryRow(label: String, value: String, isTotal: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal, fontSize = if (isTotal) 20.sp else 16.sp)
        Text(value, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal, fontSize = if (isTotal) 20.sp else 16.sp, color = if (isTotal) MaterialTheme.colorScheme.primary else Color.Unspecified)
    }
}
