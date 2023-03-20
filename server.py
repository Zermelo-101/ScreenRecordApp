import socket

HOST = ''
PORT = 8080

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind((HOST,PORT))
sock.listen(1)

conn, add = sock.accept()



data = conn.recv(1024)
print(data.decode())