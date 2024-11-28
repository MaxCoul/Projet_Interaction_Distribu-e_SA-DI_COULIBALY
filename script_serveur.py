# import socket

# HOST = '0.0.0.0'  # Écoute sur toutes les interfaces réseau
# PORT = 5000      # Doit correspondre au port utilisé par le client

# server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# server_socket.bind((HOST, PORT))
# server_socket.listen()

# print("Serveur en attente de connexion...")
# conn, addr = server_socket.accept()
# print(f"Connecté à {addr}")

# while True:
#     data = conn.recv(1024)  # Réception de messages
#     if not data:
#         break
#     print(f"Message reçu: {data.decode('utf-8')}")

# conn.close()




# import socket
# import cv2
# import numpy as np
# from datetime import datetime

# # Fonction pour ajouter des horodatages aux messages de log
# def log_message(message, level="[INFO]"):
#     print(f"[{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}] {level} {message}")

# # Paramètres du serveur
# HOST = '0.0.0.0'  # Accepter les connexions de n'importe quel hôte
# PORT = 5000       # Port d'écoute

# # Créer le socket
# log_message("Initialisation du socket...")
# server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# server_socket.bind((HOST, PORT))
# server_socket.listen(1)
# log_message(f"Serveur en écoute sur le port {PORT}")

# # Attente d'une connexion client
# log_message("En attente de la connexion d'un client...")
# client_socket, client_address = server_socket.accept()
# log_message(f"Connexion acceptée de {client_address}")

# # Flux d'images reçues
# while True:
#     try:
#         # Étape 1 : Lire la taille de l'image
#         log_message("Lecture de la taille de l'image...")
#         image_size = client_socket.recv(4)
#         if not image_size:
#             log_message("Connexion fermée par le client.", "[INFO]")
#             break

#         image_size = int.from_bytes(image_size, 'big')
#         log_message(f"Taille de l'image reçue : {image_size} octets")

#         # Étape 2 : Lire l'image complète en fonction de la taille
#         log_message("Réception des données de l'image...")
#         image_data = b""
#         while len(image_data) < image_size:
#             packet = client_socket.recv(image_size - len(image_data))
#             if not packet:
#                 log_message("Aucune donnée reçue, arrêt.", "[WARNING]")
#                 break
#             image_data += packet
#         log_message(f"Données de l'image reçues ({len(image_data)} octets)")

#         # Étape 3 : Convertir les données reçues en une image
#         log_message("Conversion des données en image...")
#         np_array = np.frombuffer(image_data, dtype=np.uint8)
#         frame = cv2.imdecode(np_array, cv2.IMREAD_COLOR)

#         if frame is not None:
#             # Afficher l'image
#             log_message("Affichage de l'image...")
#             cv2.imshow("Camera Stream", frame)
#         else:
#             log_message("Image non valide ou corrompue.", "[WARNING]")

#         # Étape 4 : Gestion de la touche 'q' pour quitter
#         if cv2.waitKey(1) & 0xFF == ord('q'):
#             log_message("Touche 'q' pressée, fermeture...")
#             break

#     except Exception as e:
#         log_message(f"Erreur de réception d'image : {e}", "[ERROR]")
#         break

# # Étape finale : Fermeture
# log_message("Fermeture de la connexion et nettoyage...")
# client_socket.close()
# server_socket.close()
# cv2.destroyAllWindows()
# log_message("Serveur arrêté.")i

#-----------------------marche partiellement-------------------------------------

import socket

# HOST = '0.0.0.0'
# PORT = 5000

# server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# server_socket.bind((HOST, PORT))
# server_socket.listen()

# print("Serveur en attente de connexion...")
# conn, addr = server_socket.accept()
# print(f"Connecté à {addr}")

# # Liste pour stocker les messages reçus
# liste_messages = []

# # while True:
# #     data = conn.recv(1024)
# #     # if not data:
# #     #     break

# #     # Ajout du message reçu à la liste
# #     message = data.decode('utf-8').strip()
# #     liste_messages.append(message)
# #     print(f"Message reçu : {message}")
# i = 0    
# while i<20:
#     data = conn.recv(1024)
#     # if not data:
#     #     break

#     # Ajout du message reçu à la liste
#     message = data.decode('utf-8').strip()
#     liste_messages.append(message)
#     print(f"Message reçu : {message}")    
#     i+=1

# print(f"Liste complète des messages : {liste_messages}")

# # conn.close()
# # print("Connexion fermée.")

#-----------------------marche partiellement-------------------------------------

# import socket

# server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# server_socket.bind(('0.0.0.0', 5000))  # Adresse locale et port
# server_socket.listen(1)
# print("Serveur en attente de connexion...")

# conn, addr = server_socket.accept()
# print(f"Connecté à {addr}")

# buffer = ""  # Buffer pour accumuler les données

# while True:
#     data = conn.recv(1024)  # Recevoir les données en paquets de 1024 octets

#     # Vérification si des données ont été reçues
#     # if not data:
#     #     continue  # Revenir au début de la boucle si aucune donnée reçue

#     # Ajouter les données reçues au buffer
#     buffer += data.decode('utf-8')

#     # Traitement du buffer : vérifier s'il contient des messages complets
#     while '/' in buffer:
#         # Extraire le message complet et conserver le reste dans le buffer
#         message, buffer = buffer.split('/', 1)

#         message = message.strip()  # Supprimer les espaces inutiles
#         if message:  # Si le message n'est pas vide
#             print(f"Message reçu : {message}")
#         else:
#             print("Message vide ignoré.")



# # Fermer la connexion
# # conn.close()
# # server_socket.close()


import socket

def start_server(host='0.0.0.0', port=5000):
    # Création du socket serveur
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))
    server_socket.listen(1)  # Limite à 1 client à la fois si nécessaire
    print(f"Serveur en écoute sur {host}:{port}")

    while True:
        print("En attente de connexion...")
        conn, addr = server_socket.accept()
        print(f"Client connecté depuis {addr}")

        try:
            # Boucle pour traiter les messages tant que le client est connecté
            while True:
                data = conn.recv(1024)
                if not data:
                    # Si aucune donnée reçue, le client s'est déconnecté
                    print(f"Client {addr} déconnecté.")
                    break
                
                message = data.decode('utf-8').strip()
                print(f"Message reçu de {addr}: {message}")

                # Envoyer une réponse au client
                conn.sendall(f"Reçu : {message}".encode('utf-8'))

        except Exception as e:
            print(f"Erreur avec le client {addr}: {e}")

        finally:
            # Fermeture de la connexion actuelle mais on reste à l'écoute pour une nouvelle connexion
            conn.close()
            print(f"Connexion fermée avec {addr}. Le serveur est toujours en écoute.\n")

# Démarrage du serveur
if __name__ == "__main__":
    start_server()