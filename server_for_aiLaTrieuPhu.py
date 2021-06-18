import socket
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

def sendmail(mail_content, receiver_address):
    #The mail addresses and password
    sender_address = 'testdoan123456@gmail.com'
    sender_pass = 'testdoan123@123'
    #Setup the MIME
    message = MIMEMultipart()
    message['From'] = sender_address
    message['To'] = receiver_address
    message[
        'Subject'] = 'A test mail sent by Python. It has an attachment.'  #The subject line
    #The body and the attachments for the mail
    message.attach(MIMEText(mail_content, 'plain'))
    #Create SMTP session for sending the mail
    session = smtplib.SMTP('smtp.gmail.com', 587)  #use gmail with port
    session.starttls()  #enable security
    session.login(sender_address,
                  sender_pass)  #login with mail_id and password
    text = message.as_string()
    ret = session.sendmail(sender_address, receiver_address, text)
    session.quit()
    print('Mail Sent')
    return ret


'''
import thư viện socket, đây là thư viện bult-in, không cần phải cài thêm bất kỳ gì 
'''
socketObject = socket.socket()
'''
gọi socket() method để lấy về 1 socket object
socket object có vai trò lắng nghe và bắt các connection đến port
'''
host = "192.168.1.5"  #địa chỉ localhost
port = 32323  #port để lắng nghe
socketObject.bind((host, port))  # bắt đầu lắng nghe ở localhost vơi port là 1234

socketObject.listen(5)  # mở kết nối trên server, parameter là số lượng kết nối (client) được phép kết nối đến

c, addr = socketObject.accept()  # chấp nhận 1 kết nối, trả về (conn,address)
# conn: object đại diện cho kết nối giữa 2 bên (server-client)
# address: là địa chỉ của client kết nối đến

print('Got connection from', addr)  # thông báo nhận kết nối từ ...

#c.send(f'Thank you for conecting'.encode("utf-8"))  # gửi  1 message ngược lại cho client biết là đã kết nối

messrecv = c.recv(
    1024)  # nhận message từ client, parameter: số bytes nhận trong 1 lần
if(messrecv):
    print(messrecv)  # in ra message đã nhận
    info = messrecv.decode().split('\n')
    print(info)
    mail = info[0]
    content = f'''
    Xin chào người chơi {info[1]}, đây là email xác nhận của trò chơi ai là triệu phú, đây là mã xác nhận của bạn {info[3]}, vui lòng nhập vào màn hình trò chơi.
    '''
    ret = sendmail(content,mail)
    if(ret == {}):
        c.send("Success".encode())
    # messsent = str(input()).encode("utf-8") # lấy input từ phía server
    # c.send(messsent) # gửi message về client
    # messrecv = c.recv(1024) # tiếp tục nhận message từ client
    c.close()


