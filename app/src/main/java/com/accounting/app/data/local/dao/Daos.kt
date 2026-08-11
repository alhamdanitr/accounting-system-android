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
    @Query("SELECT * FROM sync_operations ORDER BY createdAt ASC")
    suspend fun getPendingOperations(): List<SyncOperationEntity>

    @Delete
    suspend fun deleteOperation(operation: SyncOperationEntity)

    @Insert
    suspend fun insertOperation(operation: SyncOperationEntity)
}
