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
        // ����UDP����ˣ�����port�˿�
        DatagramSocket datagramSocket = new DatagramSocket(port);
        //����һ���ֽ����飬�������ܿͻ��˷���������
        byte[] bytes = new byte[BYTE_LENGTH];
        //�������ݱ���������������
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        System.out.println("����UDP����ˣ��������˿ڣ�" + port);
        //����һ��ѭ�������պʹ���ͻ��˷��͵�����
        while (true){
            //���տͻ��˷��͵����ݣ�������ֱ���пͻ��˷�������
            datagramSocket.receive(datagramPacket);
            //��ȡ���յ�������
            String receiveData = new String(datagramPacket.getData(),0,datagramPacket.getLength(),"UTF-8");
            //��ȡ�ͻ���IP
            InetAddress clientIP = datagramPacket.getAddress();
            //��ȡ�ͻ��˶˿ں�
            int clientPort = datagramPacket.getPort();
            System.out.println("���յ��ͻ��˷��͵����ݣ�" + receiveData + ",�ͻ���IP:" + clientIP + ",�ͻ��˶˿ڣ�" + clientPort);
            //����˷��ظ��ͻ��˵�����
            byte[] sendData = ("�ͻ�����ã�"+System.currentTimeMillis()).getBytes("UTF-8");
            //�����ݡ��ͻ���IP���ͻ��˶˿ڷ�װ�����ݱ�DatagramPacket����
            DatagramPacket sendDataPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
            //��������
            datagramSocket.send(sendDataPacket);
        }
    }
}
