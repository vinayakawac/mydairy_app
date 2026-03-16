package com.example.mydairy_app.core.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FileUtil {
    private const val ROOT_PHOTO_DIR: String = "diary_photos"
    private const val COPY_BUFFER_SIZE: Int = 8_192
    private const val FILE_EXTENSION: String = ".jpg"

    suspend fun copyPhotoToEntryDirectory(context: Context, sourceUri: Uri, entryId: Long): String {
        return withContext(Dispatchers.IO) {
            val destinationFile = createPhotoFile(context = context, entryId = entryId)
            context.contentResolver.openInputStream(sourceUri).use { inputStream ->
                destinationFile.outputStream().use { outputStream ->
                    if (inputStream != null) {
                        inputStream.copyTo(outputStream, COPY_BUFFER_SIZE)
                    }
                }
            }
            destinationFile.absolutePath
        }
    }

    fun createCameraOutputUri(context: Context, entryId: Long): Uri {
        val outputFile = createPhotoFile(context = context, entryId = entryId)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            outputFile,
        )
    }

    suspend fun deleteEntryDirectory(context: Context, entryId: Long): Unit {
        withContext(Dispatchers.IO) {
            val entryDirectory = getEntryDirectory(context = context, entryId = entryId)
            if (entryDirectory.exists()) {
                entryDirectory.deleteRecursively()
            }
        }
    }

    private fun createPhotoFile(context: Context, entryId: Long): File {
        val entryDirectory = getEntryDirectory(context = context, entryId = entryId)
        if (!entryDirectory.exists()) {
            entryDirectory.mkdirs()
        }

        val fileName = buildPhotoFileName()
        return File(entryDirectory, fileName)
    }

    private fun buildPhotoFileName(): String {
        return "photo_${System.currentTimeMillis()}$FILE_EXTENSION"
    }

    private fun getEntryDirectory(context: Context, entryId: Long): File {
        return File(File(context.filesDir, ROOT_PHOTO_DIR), entryId.toString())
    }
}
