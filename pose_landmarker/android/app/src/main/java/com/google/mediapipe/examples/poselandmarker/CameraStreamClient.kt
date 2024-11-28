import android.graphics.Bitmap
import android.os.AsyncTask
import java.io.OutputStream
import java.net.Socket
import java.io.ByteArrayOutputStream

class CameraStreamClient(private val serverAddress: String, private val serverPort: Int) {

    private var socket: Socket? = null
    private var outputStream: OutputStream? = null

    init {
        try {
            socket = Socket(serverAddress, serverPort)
            outputStream = socket?.getOutputStream()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Nouvelle fonction asynchrone pour envoyer l'image
    fun sendImageAsync(image: Bitmap) {
        object : AsyncTask<Bitmap, Void, Void>() {
            override fun doInBackground(vararg params: Bitmap): Void? {
                val image = params[0]
                try {
                    if (socket?.isConnected == true) {
                        val imageData = bitmapToByteArray(image)

                        val sizeBuffer = ByteArray(4)
                        sizeBuffer[0] = (imageData.size shr 24 and 0xFF).toByte()
                        sizeBuffer[1] = (imageData.size shr 16 and 0xFF).toByte()
                        sizeBuffer[2] = (imageData.size shr 8 and 0xFF).toByte()
                        sizeBuffer[3] = (imageData.size and 0xFF).toByte()

                        outputStream?.write(sizeBuffer)
                        outputStream?.write(imageData)
                        outputStream?.flush()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute(image)  // Exécution de la tâche avec l'image
    }

    // Fonction pour convertir l'image en tableau de bytes
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    // Send the image through the socket
  /*  fun sendImage(image: Bitmap) {
        try {
            val imageData = bitmapToByteArray(image)
            // Send the length of the image data first
            outputStream?.write(imageData.size)
            outputStream?.write(imageData)
            outputStream?.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } */

    fun sendImage(image: Bitmap) {
        try {
            // Vérifie si le socket est connecté avant d'envoyer les données
            if (socket?.isConnected == true) {
                // Convertit le bitmap en tableau d'octets
                val imageData = bitmapToByteArray(image)

                // Prépare un tableau de 4 octets pour la taille
                val sizeBuffer = ByteArray(4)
                sizeBuffer[0] = (imageData.size shr 24 and 0xFF).toByte()
                sizeBuffer[1] = (imageData.size shr 16 and 0xFF).toByte()
                sizeBuffer[2] = (imageData.size shr 8 and 0xFF).toByte()
                sizeBuffer[3] = (imageData.size and 0xFF).toByte()

                // Envoie la taille des données d'abord
                outputStream?.write(sizeBuffer)

                // Envoie les données de l'image
                outputStream?.write(imageData)

                // Vide le tampon pour garantir l'envoi immédiat
                outputStream?.flush()
            } else {
                println("[WARNING] La connexion au serveur est fermée.")
            }
        } catch (e: Exception) {
            // Capture et affiche toute exception
            e.printStackTrace()
        }
    }

    // Close the connection when done
    fun close() {
        try {
            socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
