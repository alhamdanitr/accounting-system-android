package com.accounting.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val tenantId: String,
    val sku: String,
    val barcode: String?,
    val arabicName: String,
    val englishName: String?,
    val categoryId: String?,
    val purchasePrice: Double,
    val salePrice: Double,
    val taxRate: Double,
    val currentStock: Double,
    val active: Boolean,
    val isSynced: Boolean = true
)

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey val id: String,
    val tenantId: String,
    val name: String,
    val phone: String?,
    val balance: Double,
    val isSynced: Boolean = true
)

@Entity(tableName = "sales")
data class SaleEntity(
    @PrimaryKey val id: String,
    val tenantId: String,
    val invoiceNumber: String,
    val customerId: String?,
    val totalAmount: Double,
    val paidAmount: Double,
    val dueAmount: Double,
    val createdAt: Long,
    val isSynced: Boolean = false
)

@Entity(tableName = "sync_operations")
data class SyncOperationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val idempotencyKey: String,
    val entityType: String,
    val entityId: String,
    val operationType: String,
    val payload: String,
    val createdAt: Long = System.currentTimeMillis()
)
