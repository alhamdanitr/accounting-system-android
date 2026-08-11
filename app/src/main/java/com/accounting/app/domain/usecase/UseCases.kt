package com.accounting.app.domain.usecase

import com.accounting.app.domain.model.*
import com.accounting.app.domain.repository.*
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: ProductRepository) {
    operator fun invoke(tenantId: String): Flow<List<Product>> = repository.getProducts(tenantId)
}

class CreateSaleUseCase(private val repository: SaleRepository) {
    suspend operator fun invoke(sale: Sale, items: List<SaleItem>) {
        // يمكن إضافة منطق تحقق هنا قبل الإرسال للمستودع
        repository.createSale(sale, items)
    }
}

class GetCustomersUseCase(private val repository: CustomerRepository) {
    operator fun invoke(tenantId: String): Flow<List<Customer>> = repository.getCustomers(tenantId)
}
