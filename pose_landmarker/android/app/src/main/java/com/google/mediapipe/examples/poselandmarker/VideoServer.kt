package com.google.mediapipe.examples.poselandmarker

import fi.iki.elonen.NanoHTTPD
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class VideoServer(private val port: Int) : NanoHTTPD(port) {

    private var currentFrame: Bitmap? = null

    // Mettre à jour le frame actuel
    fun updateFrame(frame: Bitmap) {
        synchronized(this) {
            currentFrame = frame
        }
    }

    override fun serve(session: IHTTPSession?): Response {
        synchronized(this) {
            currentFrame?.let { bitmap ->
                // Convertir le Bitmap en JPEG
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                val jpegData = outputStream.toByteArray()

                // Retourner une réponse HTTP avec l'image
                return newFixedLengthResponse(Response.Status.OK, "image/jpeg", jpegData.inputStream(), jpegData.size.toLong())
            }
        }
        // Si aucun frame n'est disponible
        return newFixedLengthResponse(Response.Status.NO_CONTENT, "text/plain", "No frame available")
    }
}
