package com.accounting.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.accounting.app.ui.theme.Spacing

/**
 * لماذا تغيّر شريط التنقل السفلي؟
 * ---------------------------------
 * كان يحتوي 8 عناصر في شريط سفلي واحد. معيار Material يوصي بحد أقصى 5 عناصر لأن:
 * 1) الأيقونات تضيق جدًا على الشاشات الصغيرة فيصعب الضغط عليها بدقة (Fitts's Law).
 * 2) النص أسفل كل أيقونة يتقلّص لدرجة يتعذّر قراءته.
 * 3) "المحاسبة" و"قائمة المنتجات" لم تكونا موجودتين في الشريط أصلًا رغم وجود شاشاتهما — تناقض في
 *    معمارية المعلومات (Information Architecture) كان يعني أن المستخدم لا يجد طريقًا لبعض الشاشات.
 *
 * الحل: خمسة عناصر ثابتة للمهام شديدة التكرار (الرئيسية، نقطة البيع، المخزون، العملاء)،
 * وعنصر خامس "المزيد" يفتح قائمة منظّمة بكل الشاشات الثانوية.
 */
private enum class PrimaryDestination(val label: String, val icon: ImageVector) {
    DASHBOARD("الرئيسية", Icons.Default.Dashboard),
    POS("نقطة البيع", Icons.Default.PointOfSale),
    INVENTORY("المخزون", Icons.Default.Inventory2),
    CUSTOMERS("العملاء", Icons.Default.People),
    MORE("المزيد", Icons.Default.MoreHoriz)
}

private data class MoreItem(val label: String, val icon: ImageVector, val destination: String)

private val moreItems = listOf(
    MoreItem("قائمة المنتجات", Icons.Default.Inventory, "products"),
    MoreItem("السندات", Icons.Default.Receipt, "vouchers"),
    MoreItem("التحويلات المخزنية", Icons.Default.SwapHoriz, "transfer"),
    MoreItem("المحاسبة", Icons.Default.AccountBalanceWallet, "accounting"),
    MoreItem("التقارير", Icons.Default.Assessment, "reports"),
    MoreItem("الإعدادات", Icons.Default.Settings, "settings")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selected by remember { mutableStateOf(PrimaryDestination.DASHBOARD) }
    // شاشة ثانوية مفتوحة من قائمة "المزيد" (null يعني لا شيء مفتوح، نعرض الوجهة الأساسية)
    var openSecondary by remember { mutableStateOf<String?>(null) }
    var showMoreSheet by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = Spacing.xs
            ) {
                PrimaryDestination.values().forEach { dest ->
                    NavigationBarItem(
                        icon = { Icon(dest.icon, contentDescription = dest.label) },
                        label = { Text(dest.label, style = MaterialTheme.typography.labelSmall) },
                        selected = selected == dest && openSecondary == null,
                        onClick = {
                            if (dest == PrimaryDestination.MORE) {
                                showMoreSheet = true
                            } else {
                                selected = dest
                                openSecondary = null
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (openSecondary) {
                "products" -> ProductListScreen()
                "vouchers" -> VouchersScreen()
                "transfer" -> StockTransferScreen()
                "accounting" -> AccountingScreen()
                "reports" -> ReportsScreen()
                "settings" -> SettingsScreen()
                else -> when (selected) {
                    PrimaryDestination.DASHBOARD -> DashboardScreen()
                    PrimaryDestination.POS -> POSScreen()
                    PrimaryDestination.INVENTORY -> InventoryScreen()
                    PrimaryDestination.CUSTOMERS -> CustomersSuppliersScreen()
                    PrimaryDestination.MORE -> Unit // لا تُعرض أبدًا كشاشة، فقط تفتح القائمة
                }
            }
        }
    }

    if (showMoreSheet) {
        ModalBottomSheet(onDismissRequest = { showMoreSheet = false }) {
            Text(
                "المزيد",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = Spacing.lg, vertical = Spacing.sm)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                modifier = Modifier.padding(Spacing.lg).heightIn(max = 320.dp)
            ) {
                items(moreItems) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickableSafely {
                                openSecondary = item.destination
                                showMoreSheet = false
                            }
                            .padding(vertical = Spacing.sm)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(Spacing.md),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Icon(item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                        Spacer(Modifier.height(Spacing.xs))
                        Text(item.label, style = MaterialTheme.typography.labelSmall, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                }
            }
            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

// دالة صغيرة مساعدة لتفادي تكرار استيراد clickable + interactionSource في كل مكان
@Composable
private fun Modifier.clickableSafely(onClick: () -> Unit): Modifier {
    return this.then(
        Modifier.let {
            androidx.compose.foundation.clickable(
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
        }
    )
}
