package com.accounting.app.domain.model

import java.util.Date

data class Product(
    val id: String,
    val tenantId: String,
    val sku: String,
    val barcode: String?,
    val arabicName: String,
    val englishName: String?,
    val categoryId: String?,
    val purchasePrice: Double,
    val salePrice: Number,
    val taxRate: Double,
    val currentStock: Double = 0.0,
    val active: Boolean = true
)

data class Category(
    val id: String,
    val tenantId: String,
    val name: String,
    val description: String?
)

data class Customer(
    val id: String,
    val tenantId: String,
    val name: String,
    val phone: String?,
    val balance: Double = 0.0
)

data class Sale(
    val id: String,
    val tenantId: String,
    val invoiceNumber: String,
    val customerId: String?,
    val totalAmount: Double,
    val paidAmount: Double,
    val dueAmount: Double,
    val createdAt: Date
)

data class SaleItem(
    val productId: String,
    val quantity: Double,
    val unitPrice: Double,
    val total: Double
)
