package com.google.mediapipe.examples.poselandmarker

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket

object NetworkManager {

    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null
    private var isConnected = false

    fun startClient(ip: String, port: Int, onMessageReceived: (String) -> Unit) {
        Thread {
            try {
                socket = Socket(ip, port)
                writer = PrintWriter(socket!!.getOutputStream(), true)
                reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))

                isConnected = true
                println("Connecté au serveur")

                // Lire les messages en boucle
                var message: String?
                while (isConnected) {
                    message = reader?.readLine()
                    if (message != null) {
                        onMessageReceived(message)  // Appelle un callback pour traiter le message
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendMessage(message: String) {
        if (isConnected && writer != null) {
            writer!!.println(message)
        } else {
            println("Impossible d'envoyer le message : non connecté")
        }
    }

    fun stopClient() {
        try {
            isConnected = false
            writer?.close()
            reader?.close()
            socket?.close()
            println("Déconnecté du serveur")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}