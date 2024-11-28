package com.google.mediapipe.examples.poselandmarker

import android.net.Uri
import java.io.ByteArrayOutputStream
import android.content.ContentResolver
import java.io.IOException


class DataBuffer(private val contentResolver: ContentResolver, private val bufferSize: Int,  private val fileName : String, private val uri : Uri) {
    private val buffer = ByteArrayOutputStream(bufferSize)

    @Synchronized
    fun write(data: String) {
        try {
            buffer.write(data.toByteArray())
            if (buffer.size() >= bufferSize) {
                flushToFile()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun flushToFile() {
        try {
            contentResolver.openOutputStream(uri, "wa")?.use { outputStream ->
                buffer.writeTo(outputStream)
            }
            buffer.reset()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun close(buffer: ByteArrayOutputStream, fileName : String) {
        flushToFile()
        try {
            buffer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}