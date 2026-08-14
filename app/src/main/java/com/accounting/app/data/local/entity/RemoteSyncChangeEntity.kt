package com.accounting.app.data.local.entity

import androidx.room.Entity

@Entity(tableName = "sync_remote_changes")
data class RemoteSyncChangeEntity(
    @androidx.room.PrimaryKey val id: String,
    val sequence: String,
    val tenantId: String,
    val deviceId: String,
    val entityType: String,
    val entityId: String,
    val operationType: String,
    val payload: String,
    val status: String = "PENDING",
    val errorMessage: String? = null,
    val receivedAt: Long = System.currentTimeMillis(),
)
