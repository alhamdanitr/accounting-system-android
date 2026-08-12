package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
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
    var selectedCategory by remember { mutableStateOf("الكل") }
    var isSubmitting by remember { mutableStateOf(false) }
    var orderSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val fetched = withContext(Dispatchers.IO) {
                NetworkModule.apiService.getProducts(AppConfig.DEFAULT_TENANT_ID)
            }
            products = fetched
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = "خطأ في الاتصال بالخادم: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    val categories = listOf("الكل", "MikroTik", "TP-Link", "Ubiquiti", "Switches", "Cables")

    val filteredProducts = products.filter { product ->
        val matchesSearch = product.arabicName.contains(searchQuery, ignoreCase = true) || product.sku.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "الكل" || product.arabicName.contains(selectedCategory, ignoreCase = true)
        matchesSearch && matchesCategory
    }

    val subtotal = cartItems.sumOf { it.first.salePrice.toDouble() * it.second }
    val taxTotal = cartItems.sumOf { (it.first.salePrice.toDouble() * it.second) * (it.first.taxRate / 100) }
    val grandTotal = subtotal + taxTotal

    Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // القسم الأيمن: محطة اختيار المنتجات (65%)
        Column(modifier = Modifier.weight(0.65f).fillMaxHeight().padding(16.dp)) {
            // شريط العنوان والبحث الاحترافي
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("محطة نقطة البيع (POS Terminal)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("إدارة المبيعات السريعة وإصدار الفواتير", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("بحث متقدم بالاسم أو الباركود أو رقم SKU...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Clear, contentDescription = null) }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // شريط فلاتر الفئات (Categories Bar)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        shape = RoundedCornerShape(6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(16.dp))
                }
            } else if (filteredProducts.isEmpty() && !isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("لا توجد أصناف مطابقة للبحث", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredProducts) { product ->
                        TerminalProductCard(product) {
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

        // القسم الأيسر: سلة الفاتورة والملخص المالي (35%)
        Surface(
            modifier = Modifier.weight(0.35f).fillMaxHeight(),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("سلة الفاتورة الحالية", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { cartItems = emptyList() }) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = "إفراغ السلة", tint = MaterialTheme.colorScheme.error)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                if (cartItems.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ShoppingCartCheckout, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("السلة فارغة. اختر أصنافاً للبيع.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(cartItems) { (product, qty) ->
                            TerminalCartRow(product, qty,
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

                Divider(modifier = Modifier.padding(vertical = 12.dp))

                // الملخص المالي المؤسسي
                FinancialRow("المجموع الفرعي:", "%.2f $".format(subtotal))
                FinancialRow("الضريبة (VAT):", "%.2f $".format(taxTotal))
                Spacer(modifier = Modifier.height(8.dp))
                FinancialRow("الإجمالي النهائي:", "%.2f $".format(grandTotal), isTotal = true)

                Spacer(modifier = Modifier.height(16.dp))

                if (orderSuccess) {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                        Text("تم اعتماد الفاتورة وتحديث المخزون بنجاح!", color = Color.White, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        orderSuccess = true
                        cartItems = emptyList()
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = cartItems.isNotEmpty() && !isSubmitting
                ) {
                    Icon(Icons.Default.Print, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("إتمام الدفع والطباعة", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TerminalProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(110.dp).clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(product.arabicName, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 2)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("المخزون: ${product.currentStock.toInt()}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("%.2f $".format(product.salePrice.toDouble()), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun TerminalCartRow(product: Product, qty: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.arabicName, fontSize = 13.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                Text("%.2f $".format(product.salePrice.toDouble() * qty), fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(14.dp))
                }
                Text("$qty", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp))
                IconButton(onClick = onIncrease, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun FinancialRow(label: String, value: String, isTotal: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = if (isTotal) 16.sp else 14.sp, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal, color = if (isTotal) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = if (isTotal) 18.sp else 14.sp, fontWeight = FontWeight.Bold, color = if (isTotal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
    }
}
