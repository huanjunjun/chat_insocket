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
		//创建一个UDP的客户端
		DatagramSocket datagramSocket = new DatagramSocket();
		//发送给服务端的数据
		byte[] bytes = msg.getBytes("UTF-8");
		//封装数据包，包括数据、服务端IP、服务端端口
		DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(ip), port);
		//发送数据
		datagramSocket.send(datagramPacket);
		/*********** 接收服务端发来的消息， 也可以像服务端开启新的线程 ************/
		//用来接受服务端发来的数据
		byte[] receiveData = new byte[BYTE_LENGTH];
		//封装数据报，用来接收数据
		DatagramPacket receiveDataPacket = new DatagramPacket(receiveData, receiveData.length);
		//接受数据，阻塞，直到有数据发来
		datagramSocket.receive(receiveDataPacket);
		String serverSendData = new String(receiveDataPacket.getData(),0,receiveDataPacket.getLength(),"UTF-8");
		System.out.println("服务端返回的数据：" + serverSendData + ",服务端IP:" + receiveDataPacket.getAddress() + ",服务端端口：" + receiveDataPacket.getPort());
	}
}
