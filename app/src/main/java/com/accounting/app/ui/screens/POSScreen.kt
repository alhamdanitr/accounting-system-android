package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.data.remote.NetworkModule
import com.accounting.app.domain.model.Product
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * التغييرات الجوهرية عن النسخة القديمة (وليست شكلية فقط):
 *
 * 1) BUG حقيقي تم إصلاحه: كانت فلترة الفئة تعمل بمطابقة نص اسم المنتج العربي مع قائمة فئات
 *    ثابتة يدويًا ("MikroTik", "TP-Link"...) رغم أن Product يملك حقل `categoryId` فعليًا في الموديل.
 *    هذا يعني أن الفلترة كانت تعمل بالصدفة فقط إذا احتوى الاسم العربي على الكلمة الإنجليزية للفئة.
 *    الآن: الفئات تُشتق من `categoryId` الحقيقي الموجود في البيانات المحمّلة فعلًا.
 *    (ملاحظة: لا يوجد endpoint لجلب أسماء الفئات بعد — لذا نعرض المعرّف مؤقتًا. راجع القسم الأخير
 *    في README للتفاصيل، هذا قيد حقيقي في الـ API وليس شيئًا يمكن حلّه من الواجهة فقط.)
 *
 * 2) تخطيط متجاوب: التخطيط القديم كان يقسّم الشاشة 65%/35% بشكل ثابت — هذا مناسب فقط لجهاز لوحي
 *    بالعرض. على هاتف بالوضع الرأسي (الحالة الأكثر شيوعًا) كانت شبكة 3 أعمدة + شريط سلة جانبي
 *    ستصبح ضيقة جدًا بلا استخدام. الآن نستخدم BoxWithConstraints: عرض واسع (تابلت/أفقي) = عمودان
 *    جنبًا إلى جنب كالسابق، عرض ضيق (هاتف عمودي) = شبكة منتجات بملء الشاشة + شريط سلة عائم أسفل
 *    الشاشة يفتح كـ Bottom Sheet كامل عند الضغط عليه.
 *
 * 3) لا حالة فارغة للسلة سابقًا (كانت تظهر فراغًا صامتًا) — أضفنا EmptyState.
 * 4) أزرار +/- في السلة كانت 28dp فقط (أصغر من حد اللمس الموصى به 44-48dp) — كبّرناها.
 * 5) لا تحذير عند نفاد المخزون — بطاقة المنتج الآن تُعتم وتُعطَّل عند currentStock <= 0.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var cartItems by remember { mutableStateOf(listOf<Pair<Product, Int>>()) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<String?>(null) } // null = الكل
    var showCartSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val tenantId = NetworkModule.sessionStore.tenantId
                ?: error("لا توجد جلسة مصادق عليها")
            val fetched = withContext(Dispatchers.IO) {
                NetworkModule.apiService.getProducts(tenantId)
            }
            products = fetched
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = "خطأ في الاتصال بالخادم: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    // الفئات الحقيقية المشتقة من بيانات المنتجات نفسها بدل قائمة وهمية ثابتة
    val availableCategoryIds = remember(products) {
        products.mapNotNull { it.categoryId }.distinct()
    }

    val filteredProducts = products.filter { product ->
        val matchesSearch = searchQuery.isBlank() ||
            product.arabicName.contains(searchQuery, ignoreCase = true) ||
            product.sku.contains(searchQuery, ignoreCase = true) ||
            (product.barcode?.contains(searchQuery, ignoreCase = true) == true)
        val matchesCategory = selectedCategoryId == null || product.categoryId == selectedCategoryId
        matchesSearch && matchesCategory
    }

    val subtotal = cartItems.sumOf { it.first.salePrice.toDouble() * it.second }
    val taxTotal = cartItems.sumOf { (it.first.salePrice.toDouble() * it.second) * (it.first.taxRate / 100) }
    val grandTotal = subtotal + taxTotal
    val cartCount = cartItems.sumOf { it.second }

    fun addToCart(product: Product) {
        if (product.currentStock <= 0) return
        cartItems = if (cartItems.any { it.first.id == product.id }) {
            cartItems.map { if (it.first.id == product.id) it.first to (it.second + 1) else it }
        } else {
            cartItems + (product to 1)
        }
    }

    fun increase(product: Product) {
        cartItems = cartItems.map { if (it.first.id == product.id) it.first to (it.second + 1) else it }
    }

    fun decrease(product: Product, qty: Int) {
        cartItems = if (qty > 1) {
            cartItems.map { if (it.first.id == product.id) it.first to (it.second - 1) else it }
        } else {
            cartItems.filter { it.first.id != product.id }
        }
    }

    fun checkout() {
        cartItems = emptyList()
        showCartSheet = false
        // TODO: استدعاء API إنشاء عملية بيع + طباعة الفاتورة
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideLayout = maxWidth >= 700.dp

        if (isWideLayout) {
            Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                Column(modifier = Modifier.weight(0.62f).fillMaxHeight()) {
                    ProductBrowser(
                        products = filteredProducts,
                        isLoading = isLoading,
                        errorMessage = errorMessage,
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        categoryIds = availableCategoryIds,
                        selectedCategoryId = selectedCategoryId,
                        onCategorySelect = { selectedCategoryId = it },
                        onProductClick = ::addToCart,
                        columns = 3
                    )
                }
                Surface(
                    modifier = Modifier.weight(0.38f).fillMaxHeight(),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = Spacing.xs
                ) {
                    CartPanel(
                        cartItems = cartItems,
                        subtotal = subtotal,
                        taxTotal = taxTotal,
                        grandTotal = grandTotal,
                        onIncrease = ::increase,
                        onDecrease = ::decrease,
                        onClear = { cartItems = emptyList() },
                        onCheckout = ::checkout
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize().padding(bottom = if (cartItems.isNotEmpty()) 76.dp else 0.dp)) {
                    ProductBrowser(
                        products = filteredProducts,
                        isLoading = isLoading,
                        errorMessage = errorMessage,
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        categoryIds = availableCategoryIds,
                        selectedCategoryId = selectedCategoryId,
                        onCategorySelect = { selectedCategoryId = it },
                        onProductClick = ::addToCart,
                        columns = 2
                    )
                }
                if (cartItems.isNotEmpty()) {
                    FloatingCartBar(
                        count = cartCount,
                        total = grandTotal,
                        onClick = { showCartSheet = true },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }

            if (showCartSheet) {
                ModalBottomSheet(onDismissRequest = { showCartSheet = false }) {
                    Box(modifier = Modifier.heightIn(min = 300.dp, max = 560.dp)) {
                        CartPanel(
                            cartItems = cartItems,
                            subtotal = subtotal,
                            taxTotal = taxTotal,
                            grandTotal = grandTotal,
                            onIncrease = ::increase,
                            onDecrease = ::decrease,
                            onClear = { cartItems = emptyList() },
                            onCheckout = ::checkout
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductBrowser(
    products: List<Product>,
    isLoading: Boolean,
    errorMessage: String?,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    categoryIds: List<String>,
    selectedCategoryId: String?,
    onCategorySelect: (String?) -> Unit,
    onProductClick: (Product) -> Unit,
    columns: Int
) {
    Column(modifier = Modifier.fillMaxSize().padding(Spacing.screenPadding)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("محطة نقطة البيع", style = MaterialTheme.typography.titleLarge)
                Text("إدارة المبيعات السريعة وإصدار الفواتير", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        }

        Spacer(Modifier.height(Spacing.lg))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("بحث بالاسم أو الباركود أو رمز الصنف...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "مسح البحث")
                    }
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        if (categoryIds.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.md))
            androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                item {
                    FilterChip(
                        selected = selectedCategoryId == null,
                        onClick = { onCategorySelect(null) },
                        label = { Text("الكل") }
                    )
                }
                items(categoryIds) { catId ->
                    FilterChip(
                        selected = selectedCategoryId == catId,
                        onClick = { onCategorySelect(catId) },
                        label = { Text(catId) } // TODO: استبدال بالاسم الفعلي عند توفر endpoint للفئات
                    )
                }
            }
        }

        Spacer(Modifier.height(Spacing.lg))

        when {
            errorMessage != null -> Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(errorMessage, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(Spacing.lg))
            }
            !isLoading && products.isEmpty() -> EmptyState(
                icon = Icons.Default.SearchOff,
                title = "لا توجد نتائج",
                subtitle = "لم يتم العثور على منتجات مطابقة لبحثك أو الفئة المحددة"
            )
            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.md),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    TerminalProductCard(product) { onProductClick(product) }
                }
            }
        }
    }
}

@Composable
fun TerminalProductCard(product: Product, onClick: () -> Unit) {
    val outOfStock = product.currentStock <= 0
    val lowStock = product.currentStock in 1.0..5.0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(122.dp)
            .clickable(enabled = !outOfStock) { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (outOfStock) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
    ) {
        Column(modifier = Modifier.padding(Spacing.md).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                product.arabicName,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                color = if (outOfStock) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                if (outOfStock) {
                    StatusBadge("نفد المخزون", StatusTone.NEGATIVE)
                } else if (lowStock) {
                    StatusBadge("متبقي ${product.currentStock.toInt()}", StatusTone.WARNING)
                } else {
                    Text("متوفر: ${product.currentStock.toInt()}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(
                    "%.2f $".format(product.salePrice.toDouble()),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun CartPanel(
    cartItems: List<Pair<Product, Int>>,
    subtotal: Double,
    taxTotal: Double,
    grandTotal: Double,
    onIncrease: (Product) -> Unit,
    onDecrease: (Product, Int) -> Unit,
    onClear: () -> Unit,
    onCheckout: () -> Unit
) {
    Column(modifier = Modifier.padding(Spacing.lg)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("سلة الفاتورة", style = MaterialTheme.typography.titleMedium)
            if (cartItems.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(Icons.Default.DeleteOutline, contentDescription = "إفراغ السلة", tint = MaterialTheme.colorScheme.error)
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = Spacing.sm))

        if (cartItems.isEmpty()) {
            EmptyState(
                icon = Icons.Default.ShoppingCart,
                title = "السلة فارغة",
                subtitle = "اضغط على أي منتج من القائمة لإضافته إلى الفاتورة"
            )
        } else {
            LazyColumn(modifier = Modifier.weight(1f, fill = false).heightIn(max = 320.dp), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(cartItems) { (product, qty) ->
                    TerminalCartRow(product, qty,
                        onIncrease = { onIncrease(product) },
                        onDecrease = { onDecrease(product, qty) }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = Spacing.md))

            FinancialRow("المجموع الفرعي", "%.2f $".format(subtotal))
            FinancialRow("الضريبة", "%.2f $".format(taxTotal))
            Spacer(Modifier.height(Spacing.xs))
            FinancialRow("الإجمالي", "%.2f $".format(grandTotal), isTotal = true)

            Spacer(Modifier.height(Spacing.lg))

            PrimaryActionButton(
                text = "إتمام الدفع والطباعة",
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.Print
            )
        }
    }
}

@Composable
private fun FloatingCartBar(count: Int, total: Double, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.lg)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.lg, vertical = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                Spacer(Modifier.width(Spacing.sm))
                Text("$count صنف في السلة", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleSmall)
            }
            Text("%.2f $".format(total), color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun TerminalCartRow(product: Product, qty: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(Spacing.sm), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.arabicName, style = MaterialTheme.typography.bodyMedium, maxLines = 1, fontWeight = FontWeight.Medium)
                Text("%.2f $".format(product.salePrice.toDouble() * qty), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = "إنقاص الكمية", modifier = Modifier.size(18.dp))
                }
                Text("$qty", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(horizontal = Spacing.xs))
                IconButton(onClick = onIncrease, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "زيادة الكمية", modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun FinancialRow(label: String, value: String, isTotal: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = if (isTotal) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodyMedium)
        Text(
            value,
            style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (isTotal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}
