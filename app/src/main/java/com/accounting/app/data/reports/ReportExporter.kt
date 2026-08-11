package com.accounting.app.data.reports

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class ReportExporter(private val context: Context) {
    private val TAG = "ReportExporter"

    fun exportReportToLocalFile(reportName: String, content: String, isExcel: Boolean): File {
        val extension = if (isExcel) "xlsx" else "pdf"
        val fileName = "${reportName}_${System.currentTimeMillis()}.$extension"
        val file = File(context.filesDir, fileName)

        FileOutputStream(file).use { output ->
            output.write(content.toByteArray())
        }

        Log.d(TAG, "Report exported locally to: ${file.absolutePath}")
        return file
    }
}
