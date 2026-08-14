package com.accounting.app.data.local.dao

import androidx.room.*
import com.accounting.app.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE tenantId = :tenantId")
    fun getProducts(tenantId: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: String): ProductEntity?
}

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers WHERE tenantId = :tenantId")
    fun getCustomers(tenantId: String): Flow<List<CustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)
}

@Dao
interface SaleDao {
    @Query("SELECT * FROM sales WHERE tenantId = :tenantId")
    fun getSales(tenantId: String): Flow<List<SaleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: SaleEntity)
}

@Dao
interface SyncDao {
    @Query("SELECT * FROM sync_operations WHERE tenantId = :tenantId AND deviceId = :deviceId AND status IN ('PENDING', 'FAILED') AND nextAttemptAt <= :now ORDER BY createdAt ASC LIMIT :limit")
    suspend fun getPendingOperations(tenantId: String, deviceId: String, now: Long, limit: Int = 100): List<SyncOperationEntity>

    @Query("UPDATE sync_operations SET status = :status, attempts = :attempts, lastError = :lastError, nextAttemptAt = :nextAttemptAt WHERE idempotencyKey = :idempotencyKey")
    suspend fun updateState(idempotencyKey: String, status: String, attempts: Int, lastError: String?, nextAttemptAt: Long)

    @Query("DELETE FROM sync_operations WHERE idempotencyKey IN (:idempotencyKeys)")
    suspend fun deleteAcknowledged(idempotencyKeys: List<String>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOperation(operation: SyncOperationEntity)
}
