package com.accounting.app.domain.repository

import com.accounting.app.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(tenantId: String): Flow<List<Product>>
    suspend fun getProductById(id: String): Product?
    suspend fun syncProducts(tenantId: String)
}

interface CustomerRepository {
    fun getCustomers(tenantId: String): Flow<List<Customer>>
    suspend fun addCustomer(customer: Customer)
}

interface SaleRepository {
    fun getSales(tenantId: String): Flow<List<Sale>>
    suspend fun createSale(sale: Sale, items: List<SaleItem>)
}
