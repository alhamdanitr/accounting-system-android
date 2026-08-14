package com.accounting.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.data.remote.NetworkModule
import com.accounting.app.domain.model.Product
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * لماذا دُمجت هذه الشاشة مع ProductListScreen القديمة؟
 * -------------------------------------------------------
 * كانت هناك شاشتان منفصلتان لنفس المفهوم (قائمة المنتجات):
 *
 * 1) `InventoryScreen` القديمة: حقل بحث لا يفعل شيئًا فعليًا (searchQuery غير مستخدم في أي فلترة)،
 *    وجدول من 4 عناصر بيانات **مكتوبة يدويًا** (`stockItems = listOf(...)`) بلا أي اتصال بالـ API
 *    الحقيقي — أي أن الشاشة التي يراها المستخدم كانت بيانات وهمية دائمًا مهما تغيّر المخزون الفعلي.
 * 2) `ProductListScreen` القديمة: تعتمد على `ProductViewModel` الذي يحتاج `GetProductsUseCase` في
 *    الـ constructor، لكن لا يوجد في المشروع أي مكان يُنشئ هذا الـ ViewModel فعليًا (لا Hilt ولا
 *    Factory يدوي) — الشاشة كانت **غير قابلة للتشغيل أصلًا لو حاولت استدعاءها**، ولذلك لم تكن
 *    موجودة في أي تنقّل بالتطبيق. كود ميت فعليًا.
 *
 * الحل: شاشة واحدة حقيقية، تجلب البيانات بنفس الأسلوب المستخدم فعلًا في POSScreen (اتصال مباشر عبر
 * NetworkModule، وهو النمط الوحيد الذي يعمل حاليًا في المشروع)، مع بحث وفلترة تعملان فعليًا،
 * وتدفّق قائمة ← تفاصيل عند الضغط على أي صنف (كان غائبًا تمامًا في كلا الشاشتين القديمتين).
 *
 * يمكن حذف ملف ProductListScreen.kt القديم بالكامل بعد دمج هذه الشاشة.
 */

private enum class StockFilter(val label: String) {
    ALL("الكل"), LOW("مخزون منخفض"), OUT("نافد")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var stockFilter by remember { mutableStateOf(StockFilter.ALL) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val tenantId = NetworkModule.sessionStore.tenantId
                ?: error("لا توجد جلسة مصادق عليها")
            products = withContext(Dispatchers.IO) {
                NetworkModule.apiService.getProducts(tenantId)
            }
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = "تعذّر تحميل الأصناف: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    val current = selectedProduct
    if (current != null) {
        ProductDetailView(product = current, onBack = { selectedProduct = null })
        return
    }

    val filtered = products.filter { p ->
        val matchesSearch = searchQuery.isBlank() ||
            p.arabicName.contains(searchQuery, ignoreCase = true) ||
            p.sku.contains(searchQuery, ignoreCase = true) ||
            (p.barcode?.contains(searchQuery, ignoreCase = true) == true)
        val matchesFilter = when (stockFilter) {
            StockFilter.ALL -> true
            StockFilter.LOW -> p.currentStock in 1.0..5.0
            StockFilter.OUT -> p.currentStock <= 0
        }
        matchesSearch && matchesFilter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("المخزون والأصناف", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("بحث برمز الصنف أو الباركود أو الاسم...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "مسح البحث")
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                StockFilter.entries.forEach { f ->
                    FilterChip(
                        selected = stockFilter == f,
                        onClick = { stockFilter = f },
                        label = { Text(f.label) }
                    )
                }
            }

            // ملخص سريع أعلى القائمة — كان غائبًا تمامًا، ومهم لصاحب المحل ليرى الصورة العامة
            // قبل التمرير في عشرات الأصناف
            InventorySummaryRow(products)

            when {
                errorMessage != null -> Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(errorMessage.orEmpty(), color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(Spacing.lg))
                }
                isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                filtered.isEmpty() -> EmptyState(
                    icon = Icons.Default.Inventory2,
                    title = "لا توجد أصناف مطابقة",
                    subtitle = "جرّب تعديل البحث أو الفلتر المحدد"
                )
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    items(filtered, key = { it.id }) { product ->
                        ProductRow(product) { selectedProduct = product }
                    }
                    item { Spacer(Modifier.height(Spacing.xl)) }
                }
            }
        }
    }
}

@Composable
private fun InventorySummaryRow(products: List<Product>) {
    if (products.isEmpty()) return
    val totalValue = products.sumOf { it.salePrice.toDouble() * it.currentStock }
    val lowCount = products.count { it.currentStock in 1.0..5.0 }
    val outCount = products.count { it.currentStock <= 0 }

    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.fillMaxWidth()) {
        SummaryChip(label = "إجمالي الأصناف", value = "${products.size}", modifier = Modifier.weight(1f))
        SummaryChip(label = "قيمة المخزون", value = "%.0f $".format(totalValue), modifier = Modifier.weight(1f))
        SummaryChip(label = "منخفض/نافد", value = "$lowCount / $outCount", tone = if (outCount > 0) StatusTone.NEGATIVE else if (lowCount > 0) StatusTone.WARNING else StatusTone.POSITIVE, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SummaryChip(label: String, value: String, tone: StatusTone = StatusTone.INFO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
    ) {
        Column(modifier = Modifier.padding(Spacing.sm)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
            Spacer(Modifier.height(2.dp))
            Text(value, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun ProductRow(product: Product, onClick: () -> Unit) {
    val outOfStock = product.currentStock <= 0
    val lowStock = product.currentStock in 1.0..5.0

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xs / 4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.arabicName, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Spacer(Modifier.height(2.dp))
                Text("SKU: ${product.sku}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.width(Spacing.sm))
            Column(horizontalAlignment = Alignment.End) {
                Text("%.2f $".format(product.salePrice.toDouble()), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(4.dp))
                when {
                    outOfStock -> StatusBadge("نفد", StatusTone.NEGATIVE)
                    lowStock -> StatusBadge("${product.currentStock.toInt()} متبقي", StatusTone.WARNING)
                    else -> Text("${product.currentStock.toInt()} بالمخزون", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// شاشة التفاصيل — كانت غائبة تمامًا في كلا الشاشتين القديمتين
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailView(product: Product, onBack: () -> Unit) {
    val outOfStock = product.currentStock <= 0
    val lowStock = product.currentStock in 1.0..5.0
    val margin = if (product.purchasePrice > 0) {
        ((product.salePrice.toDouble() - product.purchasePrice) / product.purchasePrice) * 100
    } else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تفاصيل الصنف", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            Column {
                Text(product.arabicName, style = MaterialTheme.typography.headlineSmall)
                product.englishName?.let {
                    Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.height(Spacing.sm))
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    when {
                        outOfStock -> StatusBadge("نفد المخزون", StatusTone.NEGATIVE)
                        lowStock -> StatusBadge("مخزون منخفض", StatusTone.WARNING)
                        else -> StatusBadge("متوفر", StatusTone.POSITIVE)
                    }
                    if (!product.active) StatusBadge("غير مُفعّل", StatusTone.NEUTRAL)
                }
            }

            Card(shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(Spacing.lg), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    DetailRow("رمز الصنف (SKU)", product.sku)
                    product.barcode?.let { DetailRow("الباركود", it) }
                    product.categoryId?.let { DetailRow("الفئة", it) }
                    HorizontalDivider()
                    DetailRow("سعر الشراء", "%.2f $".format(product.purchasePrice))
                    DetailRow("سعر البيع", "%.2f $".format(product.salePrice.toDouble()))
                    if (margin != null) DetailRow("هامش الربح", "%.1f%%".format(margin))
                    DetailRow("نسبة الضريبة", "%.1f%%".format(product.taxRate))
                    HorizontalDivider()
                    DetailRow("الكمية المتوفرة", "${product.currentStock.toInt()}")
                }
            }

            Spacer(Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { /* TODO: فتح شاشة تحويل مخزون لهذا الصنف تحديدًا */ },
                    modifier = Modifier.weight(1f).height(Spacing.touchTarget)
                ) {
                    Icon(Icons.Default.SwapHoriz, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(Spacing.xs))
                    Text("تحويل مخزون")
                }
                PrimaryActionButton(
                    text = "تعديل الصنف",
                    onClick = { /* TODO: فتح نموذج تعديل الصنف */ },
                    modifier = Modifier.weight(1f),
                    leadingIcon = Icons.Default.Edit
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
    }
}
