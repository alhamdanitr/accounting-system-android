package com.accounting.app.ui.screens

import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.accounting.app.data.auth.AuthSession
import com.accounting.app.data.remote.LoginRequest
import com.accounting.app.data.remote.NetworkModule
import java.security.MessageDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(onAuthenticated: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var tenantId by remember { mutableStateOf("") }
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text("تسجيل الدخول", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("أدخل بيانات الشركة والحساب للمتابعة", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(tenantId, { tenantId = it }, Modifier.fillMaxWidth(), label = { Text("معرف الشركة") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(identifier, { identifier = it }, Modifier.fillMaxWidth(), label = { Text("البريد أو الهاتف") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            password,
            { password = it },
            Modifier.fillMaxWidth(),
            label = { Text("كلمة المرور") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
        )
        errorMessage?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    errorMessage = null
                    try {
                        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
                        val response = withContext(Dispatchers.IO) {
                            NetworkModule.apiService.login(
                                LoginRequest(
                                    tenantId = tenantId.trim(),
                                    identifier = identifier.trim(),
                                    password = password,
                                    deviceName = android.os.Build.MODEL,
                                    devicePlatform = "Android",
                                    deviceKeyHash = sha256(androidId),
                                ),
                            )
                        }
                        NetworkModule.sessionStore.saveSession(
                            AuthSession(
                                accessToken = response.accessToken,
                                refreshToken = response.refreshToken,
                                tenantId = response.user.tenantId,
                                userId = response.user.id,
                                deviceId = response.device.id,
                                branchId = response.user.branchId,
                            ),
                        )
                        onAuthenticated()
                    } catch (error: Exception) {
                        errorMessage = error.localizedMessage ?: "تعذر تسجيل الدخول"
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading && tenantId.isNotBlank() && identifier.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (isLoading) CircularProgressIndicator()
            else Text("دخول")
        }
    }
}

private fun sha256(value: String): String = MessageDigest.getInstance("SHA-256")
    .digest(value.toByteArray())
    .joinToString("") { "%02x".format(it) }
