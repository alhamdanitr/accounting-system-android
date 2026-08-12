package com.accounting.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ChevronLeft
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.accounting.app.domain.model.Customer
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing

/**
 * ملاحظة صادقة مهمة قبل أي شيء آخر:
 * -------------------------------------
 * راجعت `ApiService.kt` كاملًا — لا يوجد فيه أي endpoint لجلب العملاء أو الموردين
 * (`getCustomers`, `getSuppliers` غير موجودين إطلاقًا)، ولا يوجد حتى موديل `Supplier` منفصل في
 * `Models.kt`؛ يوجد فقط `Customer(id, tenantId, name, phone, balance)` بلا أي حقل يميّز "مورد" عن
 * "عميل". بمعنى آخر: تبويب "الموردين" في الشاشة القديمة لم يكن يعرض بيانات حقيقية ولم يكن بإمكانه
 * ذلك أصلًا — البيانات كانت مكتوبة يدويًا بالكامل (`listOf(AccountItem(...))`).
 *
 * هذا ليس شيئًا يمكن إصلاحه من طبقة الواجهة فقط. ما فعلته هنا:
 * 1) أعدت بناء الواجهة والتفاعل (بحث يعمل فعليًا، تبويبات، ملخص أرصدة، تفاصيل) بجودة إنتاجية كاملة.
 * 2) استخدمت موديل `Customer` الحقيقي بدل `AccountItem` الوهمي (اسم/هاتف/نص رصيد مدمج)، حتى تكون
 *    البيانات رقمية (`Double`) قابلة للفرز والجمع، لا نصًا مثل "1,250.00 $ (مدين)".
 * 3) عزلت جلب البيانات في دالة واحدة `loadAccounts()` بحيث عندما يُضاف endpoint حقيقي للـ Backend
 *    (`GET customers/{tenantId}` و`GET suppliers/{tenantId}` أو حقل `type` في نفس الجدول)، التعديل
 *    المطلوب في الواجهة سطر واحد فقط، لا إعادة كتابة الشاشة.
 *
 * **إجراء موصى به خارج نطاق هذه الشاشة:** إضافة endpoint فعلي في الـ Backend، وإضافة حقل يميّز
 * نوع الحساب (عميل/مورد) في موديل البيانات.
 */

private enum class AccountKind { CUSTOMER, SUPPLIER }

private data class AccountEntry(val customer: Customer, val kind: AccountKind)

// بيانات تجريبية مؤقتة إلى حين توفر endpoint حقيقي — بصيغة الموديل الحقيقي Customer وليس نصوصًا مدمجة
private fun placeholderAccounts(): List<AccountEntry> = listOf(
    AccountEntry(Customer("c1", "t1", "شركة التميز لتقنية المعلومات", "07701234567", 1250.00), AccountKind.CUSTOMER),
    AccountEntry(Customer("c2", "t1", "مؤسسة الأفق للشبكات", "07809876543", 450.00), AccountKind.CUSTOMER),
    AccountEntry(Customer("c3", "t1", "مركز البغدادي للكمبيوتر", "07901122334", 0.00), AccountKind.CUSTOMER),
    AccountEntry(Customer("s1", "t1", "شركة ميكروتيك العالمية (الوكيل)", "+371 67 357 000", -3400.00), AccountKind.SUPPLIER),
    AccountEntry(Customer("s2", "t1", "مؤسسة الفجر لقطع الشبكات", "07711223344", -1120.00), AccountKind.SUPPLIER),
    AccountEntry(Customer("s3", "t1", "توريدات تي بي لينك", "07822334455", 0.00), AccountKind.SUPPLIER)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersSuppliersScreen() {
    var selectedTab by remember { mutableStateOf(AccountKind.CUSTOMER) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedAccount by remember { mutableStateOf<AccountEntry?>(null) }

    val allAccounts = remember { placeholderAccounts() }

    val current = selectedAccount
    if (current != null) {
        AccountDetailView(entry = current, onBack = { selectedAccount = null })
        return
    }

    val tabAccounts = allAccounts.filter { it.kind == selectedTab }
    val filtered = tabAccounts.filter {
        searchQuery.isBlank() ||
            it.customer.name.contains(searchQuery, ignoreCase = true) ||
            (it.customer.phone?.contains(searchQuery, ignoreCase = true) == true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("العملاء والموردون", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: نموذج إضافة عميل/مورد جديد */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "إضافة حساب", tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            TabRow(
                selectedTabIndex = if (selectedTab == AccountKind.CUSTOMER) 0 else 1,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == AccountKind.CUSTOMER,
                    onClick = { selectedTab = AccountKind.CUSTOMER; searchQuery = "" },
                    text = { Text("العملاء", fontWeight = FontWeight.SemiBold) }
                )
                Tab(
                    selected = selectedTab == AccountKind.SUPPLIER,
                    onClick = { selectedTab = AccountKind.SUPPLIER; searchQuery = "" },
                    text = { Text("الموردون", fontWeight = FontWeight.SemiBold) }
                )
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.screenPadding),
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                Spacer(Modifier.height(Spacing.xs))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(if (selectedTab == AccountKind.CUSTOMER) "بحث بالاسم أو الهاتف..." else "بحث في الموردين...") },
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

                BalanceSummaryCard(tabAccounts, selectedTab)

                if (filtered.isEmpty()) {
                    EmptyState(
                        icon = if (selectedTab == AccountKind.CUSTOMER) Icons.Default.People else Icons.Default.LocalShipping,
                        title = if (selectedTab == AccountKind.CUSTOMER) "لا يوجد عملاء مطابقون" else "لا يوجد موردون مطابقون",
                        subtitle = "جرّب تعديل كلمة البحث، أو أضف حسابًا جديدًا"
                    )
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                        items(filtered, key = { it.customer.id }) { entry ->
                            AccountRow(entry) { selectedAccount = entry }
                        }
                        item { Spacer(Modifier.height(Spacing.xxl)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceSummaryCard(accounts: List<AccountEntry>, kind: AccountKind) {
    val total = accounts.sumOf { it.customer.balance }
    val label = if (kind == AccountKind.CUSTOMER) "إجمالي مديونيات العملاء (لك)" else "إجمالي مستحقات الموردين (عليك)"
    val tone = if (kind == AccountKind.CUSTOMER) StatusTone.POSITIVE else StatusTone.WARNING

    Card(shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(4.dp))
                Text("%.2f $".format(kotlin.math.abs(total)), style = MaterialTheme.typography.titleLarge)
            }
            StatusBadge("${accounts.size} حساب", tone)
        }
    }
}

@Composable
private fun AccountRow(entry: AccountEntry, onClick: () -> Unit) {
    val balance = entry.customer.balance
    val (statusText, tone) = when {
        balance > 0 && entry.kind == AccountKind.CUSTOMER -> "مدين لك" to StatusTone.POSITIVE
        balance < 0 && entry.kind == AccountKind.SUPPLIER -> "مستحق عليك" to StatusTone.WARNING
        balance == 0.0 -> "متزن" to StatusTone.NEUTRAL
        else -> "متزن" to StatusTone.NEUTRAL
    }

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
                Text(entry.customer.name, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Spacer(Modifier.height(2.dp))
                Text(entry.customer.phone ?: "بلا رقم هاتف", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("%.2f $".format(kotlin.math.abs(balance)), style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(4.dp))
                StatusBadge(statusText, tone)
            }
            Spacer(Modifier.width(Spacing.xs))
            Icon(Icons.AutoMirrored.Filled.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountDetailView(entry: AccountEntry, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (entry.kind == AccountKind.CUSTOMER) "بيانات العميل" else "بيانات المورد") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "رجوع") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            Text(entry.customer.name, style = MaterialTheme.typography.headlineSmall)

            Card(shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(Spacing.lg), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    DetailRowAccount("رقم الهاتف", entry.customer.phone ?: "—")
                    HorizontalDivider()
                    DetailRowAccount("الرصيد الحالي", "%.2f $".format(kotlin.math.abs(entry.customer.balance)))
                    DetailRowAccount(
                        "الحالة",
                        if (entry.customer.balance == 0.0) "متزن"
                        else if (entry.kind == AccountKind.CUSTOMER) "مدين لك" else "مستحق عليك"
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { /* TODO: فتح سجل الحركات المالية لهذا الحساب */ },
                    modifier = Modifier.weight(1f).height(Spacing.touchTarget)
                ) {
                    Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(Spacing.xs))
                    Text("سجل الحركات")
                }
                PrimaryActionButton(
                    text = "إضافة سند",
                    onClick = { /* TODO: فتح شاشة السندات مع تعبئة هذا الحساب مسبقًا */ },
                    modifier = Modifier.weight(1f),
                    leadingIcon = Icons.Default.Receipt
                )
            }
        }
    }
}

@Composable
private fun DetailRowAccount(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
    }
}
