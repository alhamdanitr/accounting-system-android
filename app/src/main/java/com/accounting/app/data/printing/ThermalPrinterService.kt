package com.accounting.app.data.printing

import android.util.Log

/**
 * خدمة الطباعة الحرارية عبر بروتوكول ESC/POS لتطبيقات الأندرويد (Bluetooth/USB)
 */
class ThermalPrinterService {
    companion object {
        private const val TAG = "ThermalPrinterService"
        // أوامر ESC/POS الأساسية
        private val ESC_INIT = byteArrayOf(0x1B, 0x40)
        private val ALIGN_CENTER = byteArrayOf(0x1B, 0x61, 0x01)
        private val ALIGN_LEFT = byteArrayOf(0x1B, 0x61, 0x00)
        private val TEXT_BOLD_ON = byteArrayOf(0x1B, 0x45, 0x01)
        private val TEXT_BOLD_OFF = byteArrayOf(0x1B, 0x45, 0x00)
        private val CUT_PAPER = byteArrayOf(0x1D, 0x56, 0x41, 0x10)
    }

    fun generateReceiptBytes(
        companyName: String,
        invoiceNumber: String,
        items: List<ReceiptItem>,
        grandTotal: Double
    ): ByteArray {
        val buffer = mutableListOf<Byte>()

        // تهيئة الطابعة
        ESC_INIT.forEach { buffer.add(it) }

        // محاذاة المنتصف لاسم الشركة
        ALIGN_CENTER.forEach { buffer.add(it) }
        TEXT_BOLD_ON.forEach { buffer.add(it) }
        companyName.toByteArray(Charsets.UTF_8).forEach { buffer.add(it) }
        "\n".toByteArray().forEach { buffer.add(it) }
        TEXT_BOLD_OFF.forEach { buffer.add(it) }

        // رقم الفاتورة
        "فاتورة مبيعات: $invoiceNumber\n".toByteArray(Charsets.UTF_8).forEach { buffer.add(it) }
        "--------------------------------\n".toByteArray().forEach { buffer.add(it) }

        // محاذاة اليسار للأصناف
        ALIGN_LEFT.forEach { buffer.add(it) }
        for (item in items) {
            val line = "${item.name} x${item.quantity}  ${item.total}$\n"
            line.toByteArray(Charsets.UTF_8).forEach { buffer.add(it) }
        }

        "--------------------------------\n".toByteArray().forEach { buffer.add(it) }
        
        // الإجمالي
        TEXT_BOLD_ON.forEach { buffer.add(it) }
        val totalLine = "الإجمالي النهائي: $grandTotal$\n\n\n"
        totalLine.toByteArray(Charsets.UTF_8).forEach { buffer.add(it) }
        TEXT_BOLD_OFF.forEach { buffer.add(it) }

        // قص الورق
        CUT_PAPER.forEach { buffer.add(it) }

        Log.i(TAG, "Generated receipt ESC/POS byte array successfully.")
        return buffer.toByteArray()
    }
}

data class ReceiptItem(
    val name: String,
    val quantity: Int,
    val total: Double
)
