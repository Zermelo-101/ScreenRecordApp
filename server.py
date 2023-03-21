import socket
import time 

HOST = ''
PORT = 8080

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
sock.bind((HOST,PORT))
sock.listen(1)

conn, add = sock.accept()

data = conn.recv(1024)
print(data.decode())


sock.listen(1)

conn, add = sock.accept()

data = conn.recv(1024)
print(data.decode())


sock.close()
conn.close()