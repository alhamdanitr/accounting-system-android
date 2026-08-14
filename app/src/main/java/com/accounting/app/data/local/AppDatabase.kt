package com.accounting.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.accounting.app.data.local.dao.CustomerDao
import com.accounting.app.data.local.dao.ProductDao
import com.accounting.app.data.local.dao.SaleDao
import com.accounting.app.data.local.dao.SyncDao
import com.accounting.app.data.local.entity.CustomerEntity
import com.accounting.app.data.local.entity.ProductEntity
import com.accounting.app.data.local.entity.SaleEntity
import com.accounting.app.data.local.entity.SyncOperationEntity

@Database(
    entities = [ProductEntity::class, CustomerEntity::class, SaleEntity::class, SyncOperationEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao
    abstract fun saleDao(): SaleDao
    abstract fun syncDao(): SyncDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "accounting-local.db",
            ).build().also { INSTANCE = it }
        }
    }
}
