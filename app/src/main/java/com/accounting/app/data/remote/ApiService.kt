package com.accounting.app.data.remote

import com.accounting.app.domain.model.*
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/refresh")
    suspend fun refresh(@Body request: RefreshRequest): AuthResponse

    @POST("auth/logout")
    suspend fun logout(@Body request: RefreshRequest): SimpleResponse

    @GET("products/{tenantId}")
    suspend fun getProducts(@Path("tenantId") tenantId: String): List<Product>

    @GET("accounting/accounts/{tenantId}")
    suspend fun getAccounts(@Path("tenantId") tenantId: String): List<AccountResponse>

    @GET("reports/sales/daily/{tenantId}")
    suspend fun getDailySalesReport(
        @Path("tenantId") tenantId: String,
        @Query("warehouseId") warehouseId: String,
        @Query("date") date: String,
    ): DailySalesReportResponse

    @POST("sales")
    suspend fun createSale(@Body saleRequest: SaleRequest): Sale

    @GET("inventory/warehouses/{tenantId}")
    suspend fun getWarehouses(@Path("tenantId") tenantId: String): List<WarehouseResponse>

    @POST("sync/push")
    suspend fun pushSyncOperations(@Body syncRequest: SyncPushRequest): SyncResponse

    @GET("sync/pull")
    suspend fun pullSyncOperations(
        @Query("tenantId") tenantId: String,
        @Query("deviceId") deviceId: String,
        @Query("cursor") cursor: String = "0",
        @Query("limit") limit: Int = 100,
    ): SyncPullResponse

    @GET("sales/customers/{tenantId}")
    suspend fun getCustomers(@Path("tenantId") tenantId: String): List<Customer>

    @GET("purchases/suppliers/{tenantId}")
    suspend fun getSuppliers(@Path("tenantId") tenantId: String): List<Supplier>
}

data class LoginRequest(
    val tenantId: String,
    val identifier: String,
    val password: String,
    val deviceName: String,
    val devicePlatform: String,
    val deviceKeyHash: String,
)

data class RefreshRequest(
    val tenantId: String,
    val refreshToken: String,
    val deviceId: String? = null,
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val device: DeviceResponse,
    val user: AuthUserResponse,
)

data class DeviceResponse(val id: String, val name: String, val platform: String)
data class AuthUserResponse(val id: String, val fullName: String, val email: String?, val tenantId: String, val branchId: String?)
data class SimpleResponse(val success: Boolean)
data class WarehouseResponse(val id: String, val name: String, val code: String)
data class DailySalesReportResponse(
    val date: String,
    val warehouse: WarehouseResponse,
    val summary: DailySalesSummaryResponse,
    val sales: List<DailySaleResponse>,
)
data class DailySalesSummaryResponse(
    val count: Int,
    val totalRevenue: Double,
    val totalPaid: Double,
    val totalDue: Double,
)
data class DailySaleResponse(
    val id: String,
    val invoiceNumber: String,
    val createdAt: String,
    val grandTotal: Double,
    val paidAmount: Double,
    val dueAmount: Double,
    val paymentType: String,
    val customer: DailySalesCustomerResponse?,
)
data class DailySalesCustomerResponse(val name: String)

data class AccountResponse(
    val id: String,
    val tenantId: String,
    val code: String,
    val name: String,
    val type: String,
    val parentId: String?,
)

data class SaleRequest(
    val tenantId: String,
    val branchId: String? = null,
    val warehouseId: String,
    val customerId: String? = null,
    val userId: String? = null,
    val paymentType: String,
    val paidAmount: Double,
    val discount: Double? = null,
    val notes: String? = null,
    val items: List<SaleItemRequest>,
)

data class SaleItemRequest(
    val productId: String,
    val quantity: Double,
    val unitPrice: Double,
    val discount: Double? = null,
)

data class SyncPushRequest(
    val tenantId: String,
    val deviceId: String,
    val operations: List<SyncOperationDto>,
)

data class SyncOperationDto(
    val idempotencyKey: String,
    val entityType: String,
    val entityId: String,
    val operationType: String,
    val payload: String,
)

data class SyncResponse(
    val success: Boolean,
    val processedCount: Int,
    val results: List<SyncOperationResult> = emptyList(),
)

data class SyncOperationResult(
    val idempotencyKey: String,
    val operationId: String,
    val status: String,
    val sequence: String? = null,
    val duplicate: Boolean? = null,
    val retryable: Boolean? = null,
    val errorMessage: String? = null,
)

data class SyncPullResponse(
    val success: Boolean,
    val operations: List<RemoteSyncOperation>,
    val nextCursor: String,
    val hasMore: Boolean,
)

data class RemoteSyncOperation(
    val id: String,
    val sequence: String,
    val tenantId: String,
    val deviceId: String,
    val entityType: String,
    val entityId: String,
    val operationType: String,
    val payload: String,
    val status: String,
)
