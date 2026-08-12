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
import com.accounting.app.data.remote.NetworkModule
import com.accounting.app.data.settings.AppConfig
import com.accounting.app.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var cartItems by remember { mutableStateOf(listOf<Pair<Product, Int>>()) }
    var searchQuery by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var orderSuccess by remember { mutableStateOf(false) }

    // جلب المنتجات الحقيقية من الباك إند عند فتح الشاشة
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val fetched = withContext(Dispatchers.IO) {
                NetworkModule.apiService.getProducts(AppConfig.DEFAULT_TENANT_ID)
            }
            products = fetched
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = "تعرّف على خطأ في الاتصال بالسيرفر: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    val filteredProducts = products.filter { 
        it.arabicName.contains(searchQuery, ignoreCase = true) || it.sku.contains(searchQuery, ignoreCase = true) 
    }

    // الحسابات المالية الدقيقة
    val subtotal = cartItems.sumOf { it.first.salePrice.toDouble() * it.second }
    val taxTotal = cartItems.sumOf { (it.first.salePrice.toDouble() * it.second) * (it.first.taxRate / 100) }
    val grandTotal = subtotal + taxTotal

    Row(modifier = Modifier.fillMaxSize()) {
        // الجانب الأيمن: قائمة المنتجات الحقيقية (60%)
        Column(modifier = Modifier.weight(0.6f).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("نقطة البيع المتكاملة (POS)", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("بحث بالاسم أو الباركود أو SKU...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(16.dp))
                }
            } else if (products.isEmpty() && !isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("لا توجد أصناف في المخزن حالياً. قم بإضافة أصناف من لوحة تحكم الويندوز أو السيرفر.", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredProducts) { product ->
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
        }

        // الجانب الأيسر: سلة المبيعات والفاتورة الحقيقية (40%)
        Card(
            modifier = Modifier.weight(0.4f).fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("سلة الفاتورة الحالية", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                if (cartItems.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("السلة فارغة", color = Color.Gray)
                    }
                } else {
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
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                FinancialSummaryRow("المجموع الفرعي:", "%.2f $".format(subtotal))
                FinancialSummaryRow("ضريبة القيمة المضافة:", "%.2f $".format(taxTotal))
                Spacer(modifier = Modifier.height(8.dp))
                FinancialSummaryRow("الإجمالي النهائي:", "%.2f $".format(grandTotal), isTotal = true)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                if (orderSuccess) {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32))) {
                        Text("تم تسجيل الفاتورة بنجاح وتحديث المخزون!", color = Color.White, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        // إرسال الفاتورة للسيرفر الحقيقي
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = cartItems.isNotEmpty() && !isSubmitting,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إتمام الدفع وتسجيل الفاتورة", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
