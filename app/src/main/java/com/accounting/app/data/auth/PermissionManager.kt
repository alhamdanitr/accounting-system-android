package com.accounting.app.data.auth

import android.content.Context

class PermissionManager(private val context: Context) {
    private var userPermissions: Set<String> = emptySet()

    fun setPermissions(permissions: List<String>) {
        userPermissions = permissions.toSet()
    }

    fun hasPermission(permissionCode: String): Boolean {
        // Admin always has all permissions
        if (userPermissions.contains("admin")) return true
        return userPermissions.contains(permissionCode)
    }

    fun canCreateSale(): Boolean = hasPermission("sales.create")
    fun canDeleteSale(): Boolean = hasPermission("sales.delete")
    fun canViewReports(): Boolean = hasPermission("reports.view")
    fun canManageInventory(): Boolean = hasPermission("inventory.manage")
}
