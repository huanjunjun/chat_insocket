package network;


import java.net.*;
import java.util.Scanner;
public class UdpClient {
	public final static String SERVER_IP = "127.0.0.1";
	public final static int SERVER_PORT = 10999;
    public final static int BYTE_LENGTH = 1024;
	public static void main(String[] args) throws Exception {
		UdpClient udpClient = new UdpClient();
		Scanner in = new Scanner(System.in);
		while(true) {
			String msg = in.next();
			if("exit".equals(msg)) {
				break;
			}
			udpClient.startUdpClient(SERVER_IP, SERVER_PORT, msg);
		}
	}
	
	public void startUdpClient(String ip, int port, String msg) throws Exception {
		//����һ��UDP�Ŀͻ���
		DatagramSocket datagramSocket = new DatagramSocket();
		//���͸�����˵�����
		byte[] bytes = msg.getBytes("UTF-8");
		//��װ���ݰ����������ݡ������IP������˶˿�
		DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(ip), port);
		//��������
		datagramSocket.send(datagramPacket);
		/*********** ���շ���˷�������Ϣ�� Ҳ���������˿����µ��߳� ************/
		//�������ܷ���˷���������
		byte[] receiveData = new byte[BYTE_LENGTH];
		//��װ���ݱ���������������
		DatagramPacket receiveDataPacket = new DatagramPacket(receiveData, receiveData.length);
		//�������ݣ�������ֱ�������ݷ���
		datagramSocket.receive(receiveDataPacket);
		String serverSendData = new String(receiveDataPacket.getData(),0,receiveDataPacket.getLength(),"UTF-8");
		System.out.println("����˷��ص����ݣ�" + serverSendData + ",�����IP:" + receiveDataPacket.getAddress() + ",����˶˿ڣ�" + receiveDataPacket.getPort());
	}
}
