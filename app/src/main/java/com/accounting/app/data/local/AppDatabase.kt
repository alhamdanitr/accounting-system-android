package com.accounting.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.accounting.app.data.local.dao.CustomerDao
import com.accounting.app.data.local.dao.ProductDao
import com.accounting.app.data.local.dao.RemoteChangeDao
import com.accounting.app.data.local.dao.SaleDao
import com.accounting.app.data.local.dao.SyncDao
import com.accounting.app.data.local.entity.CustomerEntity
import com.accounting.app.data.local.entity.ProductEntity
import com.accounting.app.data.local.entity.RemoteSyncChangeEntity
import com.accounting.app.data.local.entity.SaleEntity
import com.accounting.app.data.local.entity.SyncOperationEntity

@Database(
    entities = [ProductEntity::class, CustomerEntity::class, SaleEntity::class, SyncOperationEntity::class, RemoteSyncChangeEntity::class],
    version = 2,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao
    abstract fun saleDao(): SaleDao
    abstract fun syncDao(): SyncDao
    abstract fun remoteChangeDao(): RemoteChangeDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS sync_remote_changes (id TEXT NOT NULL PRIMARY KEY, sequence TEXT NOT NULL, tenantId TEXT NOT NULL, deviceId TEXT NOT NULL, entityType TEXT NOT NULL, entityId TEXT NOT NULL, operationType TEXT NOT NULL, payload TEXT NOT NULL, status TEXT NOT NULL, errorMessage TEXT, receivedAt INTEGER NOT NULL)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_sync_remote_changes_tenantId_status ON sync_remote_changes (tenantId, status)")
            }
        }

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "accounting-local.db",
            ).addMigrations(MIGRATION_1_2).build().also { INSTANCE = it }
        }
    }
}
