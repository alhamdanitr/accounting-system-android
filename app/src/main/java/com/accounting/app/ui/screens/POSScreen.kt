package com.accounting.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class CartItem(
    val productId: String,
    val productName: String,
    val unitPrice: Double,
    val quantity: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen() {
    var cartItemsState by remember { mutableStateOf(listOf<CartItem>()) }
    var discountState by remember { mutableStateOf("0") }

    val subtotal = cartItemsState.sumOf { it.unitPrice * it.quantity }
    val discount = discountState.toDoubleOrNull() ?: 0.0
    val total = maxOf(0.0, subtotal - discount)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("نقطة البيع المتكاملة (POS)") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "السلة")
                    }
                }
            )
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // قسم المنتجات السريعة (يسار الشاشة أو النصف العلوي)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Text("اختر المنتجات", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                // عينات منتجات سريعة للاختبار
                Button(
                    onClick = {
                        val item = CartItem("p1", "راوتر ميكروتيك RB951", 45.0, 1)
                        cartItemsState = if (cartItemsState.any { it.productId == item.productId }) {
                            cartItemsState.map { if (it.productId == item.productId) it.copy(quantity = it.quantity + 1) else it }
                        } else {
                            cartItemsState + item
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text("إضافة: راوتر ميكروتيك (45$)")
                }

                Button(
                    onClick = {
                        val item = CartItem("p2", "كاميرا مراقبة داهوا 2MP", 30.0, 1)
                        cartItemsState = if (cartItemsState.any { it.productId == item.productId }) {
                            cartItemsState.map { if (it.productId == item.productId) it.copy(quantity = it.quantity + 1) else it }
                        } else {
                            cartItemsState + item
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text("إضافة: كاميرا مراقبة داهوا (30$)")
                }
            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

            // قسم السلة والفاتورة (يمين الشاشة أو النصف السفلي)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Text("سلة المبيعات الحالية", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItemsState) { cartItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(cartItem.productName, style = MaterialTheme.typography.bodyMedium)
                                    Text("الكمية: ${cartItem.quantity} × ${cartItem.unitPrice}$", style = MaterialTheme.typography.bodySmall)
                                }
                                IconButton(onClick = {
                                    cartItemsState = cartItemsState.filter { it.productId != cartItem.productId }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "حذف الصنف")
                                }
                            }
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("المجموع الفرعي:")
                    Text("$subtotal$")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("الخصم:")
                    Text("$discount$")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("الإجمالي النهائي:", style = MaterialTheme.typography.titleLarge)
                    Text("$total$", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // إتمام الطلب وإرساله للخادم أو الحفظ محلياً
                        cartItemsState = emptyList()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cartItemsState.isNotEmpty()
                ) {
                    Text("إتمام الدفع وتسجيل الفاتورة")
                }
            }
        }
    }
}
