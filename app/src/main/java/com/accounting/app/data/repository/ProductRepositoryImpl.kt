package com.accounting.app.data.repository

import com.accounting.app.data.local.dao.ProductDao
import com.accounting.app.data.local.entity.ProductEntity
import com.accounting.app.data.remote.ApiService
import com.accounting.app.domain.model.Product
import com.accounting.app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val apiService: ApiService
) : ProductRepository {

    override fun getProducts(tenantId: String): Flow<List<Product>> {
        return productDao.getProducts(tenantId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return productDao.getProductById(id)?.toDomain()
    }

    override suspend fun syncProducts(tenantId: String) {
        try {
            val remoteProducts = apiService.getProducts(tenantId)
            val entities = remoteProducts.map { it.toEntity() }
            productDao.insertProducts(entities)
        } catch (e: Exception) {
            // معالجة الخطأ أو تسجيله
        }
    }

    private fun ProductEntity.toDomain() = Product(
        id = id,
        tenantId = tenantId,
        sku = sku,
        barcode = barcode,
        arabicName = arabicName,
        englishName = englishName,
        categoryId = categoryId,
        purchasePrice = purchasePrice,
        salePrice = salePrice,
        taxRate = taxRate,
        currentStock = currentStock,
        active = active
    )

    private fun Product.toEntity() = ProductEntity(
        id = id,
        tenantId = tenantId,
        sku = sku,
        barcode = barcode,
        arabicName = arabicName,
        englishName = englishName,
        categoryId = categoryId,
        purchasePrice = purchasePrice,
        salePrice = salePrice.toDouble(),
        taxRate = taxRate,
        currentStock = currentStock,
        active = active
    )
}
