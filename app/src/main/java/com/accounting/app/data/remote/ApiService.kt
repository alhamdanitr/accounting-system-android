package com.accounting.app.data.remote

import com.accounting.app.domain.model.*
import retrofit2.http.*

interface ApiService {
    @GET("products/{tenantId}")
    suspend fun getProducts(@Path("tenantId") tenantId: String): List<Product>

    @POST("sales")
    suspend fun createSale(@Body saleRequest: SaleRequest): Sale

    @POST("sync/push")
    suspend fun pushSyncOperations(@Body syncRequest: SyncPushRequest): SyncResponse
}

data class SaleRequest(
    val tenantId: String,
    val warehouseId: String,
    val customerId: String?,
    val items: List<SaleItemRequest>
)

data class SaleItemRequest(
    val productId: String,
    val quantity: Double,
    val unitPrice: Double
)

data class SyncPushRequest(
    val tenantId: String,
    val deviceId: String,
    val operations: List<SyncOperationDto>
)

data class SyncOperationDto(
    val idempotencyKey: String,
    val entityType: String,
    val entityId: String,
    val operationType: String,
    val payload: String
)

data class SyncResponse(
    val success: Boolean,
    val processedCount: Int
)
