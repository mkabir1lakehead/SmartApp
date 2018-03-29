import socket
import sys

address = ('127.0.0.1', 8000)
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

user = sys.argv[1]
num = sys.argv[2]
app = sys.argv[3]
msg = num + "|" +user + " already use the " +app + " more than 1 hours, please pay attention to it"
s.sendto(msg, address)

s.close()
