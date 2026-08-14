package com.accounting.app.ui.screens

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
import com.accounting.app.domain.model.Customer
import com.accounting.app.domain.model.Supplier
import com.accounting.app.ui.components.*
import com.accounting.app.ui.theme.Spacing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private enum class AccountKind { CUSTOMER, SUPPLIER }

private data class AccountEntry(
    val id: String,
    val name: String,
    val phone: String?,
    val balance: Double,
    val kind: AccountKind
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersSuppliersScreen() {
    var selectedTab by remember { mutableStateOf(AccountKind.CUSTOMER) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedAccount by remember { mutableStateOf<AccountEntry?>(null) }
    
    var allAccounts by remember { mutableStateOf<List<AccountEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val tenantId = NetworkModule.sessionStore.tenantId
                ?: error("لا توجد جلسة مصادق عليها")
            val customers = withContext(Dispatchers.IO) {
                NetworkModule.apiService.getCustomers(tenantId)
            }
            val suppliers = withContext(Dispatchers.IO) {
                NetworkModule.apiService.getSuppliers(tenantId)
            }
            
            allAccounts = customers.map { AccountEntry(it.id, it.name, it.phone, it.balance, AccountKind.CUSTOMER) } +
                          suppliers.map { AccountEntry(it.id, it.name, it.phone, it.balance, AccountKind.SUPPLIER) }
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = "تعذّر تحميل البيانات: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    val current = selectedAccount
    if (current != null) {
        AccountDetailView(entry = current, onBack = { selectedAccount = null })
        return
    }

    val tabAccounts = allAccounts.filter { it.kind == selectedTab }
    val filtered = tabAccounts.filter {
        searchQuery.isBlank() ||
            it.name.contains(searchQuery, ignoreCase = true) ||
            (it.phone?.contains(searchQuery, ignoreCase = true) == true)
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

                when {
                    isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    errorMessage != null -> Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        val message = errorMessage.orEmpty()
                        Text(message, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(Spacing.lg))
                    }
                    filtered.isEmpty() -> EmptyState(
                        icon = if (selectedTab == AccountKind.CUSTOMER) Icons.Default.People else Icons.Default.LocalShipping,
                        title = if (selectedTab == AccountKind.CUSTOMER) "لا يوجد عملاء مطابقون" else "لا يوجد موردون مطابقون",
                        subtitle = "جرّب تعديل كلمة البحث، أو أضف حسابًا جديدًا"
                    )
                    else -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                            items(filtered, key = { it.id }) { entry ->
                                AccountRow(entry) { selectedAccount = entry }
                            }
                            item { Spacer(Modifier.height(Spacing.xxl)) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceSummaryCard(accounts: List<AccountEntry>, kind: AccountKind) {
    val total = accounts.sumOf { it.balance }
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
    val balance = entry.balance
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
                Text(entry.name, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Spacer(Modifier.height(2.dp))
                Text(entry.phone ?: "بلا رقم هاتف", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("%.2f $".format(kotlin.math.abs(balance)), style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(4.dp))
                StatusBadge(statusText, tone)
            }
            Spacer(Modifier.width(Spacing.xs))
            Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
            Text(entry.name, style = MaterialTheme.typography.headlineSmall)

            Card(shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(Spacing.lg), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    DetailRowAccount("رقم الهاتف", entry.phone ?: "—")
                    HorizontalDivider()
                    DetailRowAccount("الرصيد الحالي", "%.2f $".format(kotlin.math.abs(entry.balance)))
                    DetailRowAccount(
                        "الحالة",
                        if (entry.balance == 0.0) "متزن"
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
