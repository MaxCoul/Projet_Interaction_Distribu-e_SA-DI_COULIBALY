import android.util.Log
import kotlinx.coroutines.*
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket
import java.net.InetSocketAddress

class SocketClientTask(
    private val host: String = "192.168.249.103",  // Remplacez par votre IP serveur
    private val port: Int = 5000                 // Remplacez par votre port serveur
) {

    // Fonction pour envoyer un message via Socket
    suspend fun sendMessage(messages: List<String>) {
        withContext(Dispatchers.IO) {
            try {
                Socket().use { socket ->
                    // Connexion au serveur avec un timeout de 5 secondes
                    socket.connect(InetSocketAddress(host, port), 5000)
                    val output: OutputStream = socket.getOutputStream()
                    val writer = PrintWriter(output, true)

                    // Envoi de chaque message
                    for (message in messages) {
                        writer.println(message)
                        delay(100)  // Pause de 100 ms entre chaque message
                    }

                    writer.flush()
                }
            } catch (e: Exception) {
                // Gérer les erreurs de connexion
                Log.e("SocketClientTask", "Erreur lors de l'envoi du message : ${e.message}")
            }
        }
    }
}

