package network;
import java.net.*;
public class UdpServer {
    public final static int SERVER_PORT = 10999;
    public final static int BYTE_LENGTH = 1024;
    public static void main(String[] args) throws Exception {
        UdpServer udpServer = new UdpServer();
        udpServer.startUdpServer(SERVER_PORT);
    }
    public void startUdpServer(int port) throws Exception {
        // 创建UDP服务端，监听port端口
        DatagramSocket datagramSocket = new DatagramSocket(port);
        //创建一个字节数组，用来接受客户端发来的数据
        byte[] bytes = new byte[BYTE_LENGTH];
        //创建数据报，用来接收数据
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        System.out.println("开启UDP服务端，并监听端口：" + port);
        //创建一个循环，接收和处理客户端发送的数据
        while (true){
            //接收客户端发送的数据，阻塞，直到有客户端发送数据
            datagramSocket.receive(datagramPacket);
            //获取接收到的数据
            String receiveData = new String(datagramPacket.getData(),0,datagramPacket.getLength(),"UTF-8");
            //获取客户端IP
            InetAddress clientIP = datagramPacket.getAddress();
            //获取客户端端口号
            int clientPort = datagramPacket.getPort();
            System.out.println("接收到客户端发送的数据：" + receiveData + ",客户端IP:" + clientIP + ",客户端端口：" + clientPort);
            //服务端返回给客户端的数据
            byte[] sendData = ("客户端你好！"+System.currentTimeMillis()).getBytes("UTF-8");
            //将数据、客户端IP、客户端端口封装进数据报DatagramPacket里面
            DatagramPacket sendDataPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
            //发送数据
            datagramSocket.send(sendDataPacket);
        }
    }
}
