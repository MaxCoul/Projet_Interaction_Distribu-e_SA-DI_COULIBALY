#!/usr/bin/env -P /usr/bin:/usr/local/bin python3 -B
# coding: utf-8

#
#  main.py
#  image_sender
#  Created by Ingenuity i/o on 2024/11/24
#

import sys
import ingescape as igs
import cv2
import base64
import requests
import socket
import re


last_item_id = [None, None]   # (camera, map)
current_item_id = [None, None]
pose_list = [[10,20,30,40,50,60,70,80,90,100],[10,20,30,40,50,60,70,80,90,100],[10,20,30,40,50,60,70,80,90,100]] #liste des pose predefinie
url_image = "http://192.168.186.208:8080/stream.mjpeg?clientId=B5VYIFnf29rOucTs"
url_list = "list"
n = 0
current_pose = 0
HOST = '0.0.0.0'  # Écoute sur toutes les interfaces réseau
PORT = 5000       # Doit correspondre au port utilisé par le client




def test_pose(nb_pose, current_list, tolerance= 5):
    list1 = pose_list[nb_pose]
    if len(list1) != len(current_list):
        raise ValueError("Lists must have the same length for comparison.")

    for a, b in zip(list1, current_list):
        if not (b - tolerance <= a <= b + tolerance):
            return False  

    return True      



def data_to_float_list(data):
    nombres = re.findall(r'[-+]?\d*\.?\d+', data)
    nombres_floats = [float(nombre) for nombre in nombres]
    return nombres_floats


def get_image_from_video(url):
     
    #ouverture du stream
    video_stream = cv2.VideoCapture(url, cv2.CAP_FFMPEG)
    if not video_stream.isOpened():
        raise ValueError("Cannot open video stream from the URL.")
    
    try: 
        while True:
            success, frame = video_stream.read()
            if not success:
                print("End of video stream or error.")
                break
            #conversion de l'image en base64
            _, buffer = cv2.imencode('.png', frame)
            
            base64_image = base64.b64encode(buffer).decode('utf-8')

            #appel de la fonction d'affichage d'image pour afficher l'image 
            displayimage(frame, (50,50), ratio=1, image_type=0)
            landmarks = get_landmarks()
            pose = data_to_float_list(landmarks)
            test = test_pose(pose, current_pose)
            if test:
                current_pose +=1
                if current_pose == 5 : break
                #displayimage(image_pose[current_pose], (50,50), ratio=1, image_type=1)  ligne pour changer de pose a l'ecran
            



    except KeyboardInterrupt:
        print("\nExiting on user request.")
    except Exception as e:
        print(f"une erreur {e}")




def displayimage(cv_image, position = (0,0), ratio=4, image_type = 0):  #image_type 0: camera, 1: poses

        try:
            # Encodage de l'image en format JPEG
            _, buffer = cv2.imencode('.jpg', cv_image)
            
            image_base64 = base64.b64encode(buffer).decode('utf-8')
            
            height, width  = cv_image.shape[:2]
            x, y = position
            arguments_list = (image_base64, x, y, width/ratio, height/ratio)
            
            igs.service_call("Whiteboard", "addImage", arguments_list, str(image_type))
            
            if last_item_id[image_type]:
                igs.service_call("Whiteboard", "remove", last_item_id[image_type], "")

            last_item_id[image_type] = current_item_id[image_type]
            

        except Exception as e:
            print(f"Error: {e}")  

def get_landmarks():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((HOST, PORT))
    server_socket.listen()
    print("Serveur en attente de connexion...")
    conn, addr = server_socket.accept()
    print(f"Connecté à {addr}")
    while True:
        try:
            chunk = conn.recv(1024)  # Réception de messages
            if not chunk:
                break
            data = chunk
        except BlockingIOError:
            break
    print(f"Message reçu: {data.decode('utf-8')}")
    conn.close()
    return data.decode('utf-8') if data else None



def update_element_id_callback( sender_agent_name, sender_agent_uuid, service_name, arguments, token, my_data):
        if token=="0":
            current_item_id[0] = arguments[0]
        else:
            current_item_id[1] = arguments[0]


if __name__ == "__main__":
    if len(sys.argv) < 4:
        print("usage: python3 main.py agent_name network_device port")
        devices = igs.net_devices_list()
        print("Please restart with one of these devices as network_device argument:")
        for device in devices:
            print(f" {device}")
        exit(0)

    igs.agent_set_name(sys.argv[1])
    igs.definition_set_class("image_sender")
    igs.log_set_console(True)
    igs.log_set_file(True, None)
    igs.set_command_line(sys.executable + " " + " ".join(sys.argv))
    igs.service_init("elementCreated", update_element_id_callback, None)
    igs.service_arg_add("elementCreated", "elementID", igs.INTEGER_T)

    igs.debug(f"Ingescape version: {igs.version()} (protocol v{igs.protocol()})")

    igs.start_with_device(sys.argv[2], int(sys.argv[3]))

    #displayimage(image_pose[0], (50,50), ratio=1, image_type=1)
    get_image_from_video(url_image)
    


    input()

    igs.stop()

