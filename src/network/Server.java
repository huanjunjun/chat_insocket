package network;

/*
 * ʹ��udp socketʵ���ļ�����
 */
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
 
public class Server extends Thread {
 
	// �ļ��ı���·��
	private String fileDir;
	// socket�������˿ں�
	private int port;
 
	public String getFileDir() {
		return fileDir;
	}
 
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
 
	public int getPort() {
		return port;
	}
 
	public void setPort(int port) {
		this.port = port;
	}
 
	public static void main(String[] args) {
		Server server = new Server();
		server.setFileDir("D:\\");
		server.setPort(9005);
		server.start();
	}
 
	/**
	 * �ļ�����
	 */
	@Override
	public void run() {
 
		int bufferSize = 8192;
		byte[] buf = new byte[bufferSize];
 
		DatagramPacket dpk = null;
		DatagramSocket dsk = null;
 
		DataOutputStream fileOut = null;//�ļ������
		long passedlen = 0;//��ɴ���Ĵ�С
		long len = 0;//�ļ��Ĵ�С
		
		int readSize = 0;//ÿ�ζ�ȡ���ݵĴ�С
		
		String fileName = null;//���ܵ��ļ���
 
		try {
			dpk = new DatagramPacket(buf, buf.length,new InetSocketAddress(InetAddress.getByName("localhost"), port));  
			dsk = new DatagramSocket(port + 1, InetAddress.getByName("localhost"));  
			
			// public Socket accept() throws
			// IOException���������ܵ����׽��ֵ����ӡ��˷����ڽ�������֮ǰһֱ������
			System.out.println("�ȴ��ͻ�������");
			
			dsk.receive(dpk);
			
			
			// ���ر���·�����ļ������Զ��ӷ������˼̳ж�����
			// ��ȡ�ļ���
			
			fileName = new String(buf).trim();
			
			fileDir = fileDir + dpk.getAddress().toString().split("/")[1];
			
			File file = new File(fileDir);
			
			if(!file.exists())
			{
				file.mkdir();
			}
			
			String filePath = fileDir + "\\" + fileName;
						
			dsk.receive(dpk);
			
			System.out.println("buf�ļ���·��" + filePath + "\n");
			
			file = new File(filePath);
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			
			len = Long.parseLong(new String(buf, 0, dpk.getLength()));
 
			System.out.println("��ʼ�����ļ�!" + "\n");
			
			
			dsk.receive(dpk);
			while ((readSize = dpk.getLength()) != 0) {
 
				passedlen += readSize; 
				//��ͼ�λ����棬�ò��ֿ���������Ϊ������
				//System.out.println("�ļ�������" + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, readSize);
				fileOut.flush();
				dsk.receive(dpk);
			}
			
			if(passedlen != len)
			{
				System.out.printf("IP:%s������%s���������ʧȥ����\n",dpk.getAddress(),fileName);
				file.delete();//��ȱ���ļ�ɾ��
			}
			else
				System.out.println("������ɣ��ļ���Ϊ" + filePath + "\n");
 
 
		} catch (Exception e) {
			System.out.println("������Ϣ����" + "\n");
			e.printStackTrace();
			return;
		}finally{
			if(fileOut != null)
				try {
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(dsk != null)
				dsk.close();
			if(passedlen != len)
			{
				System.out.printf("IP:%s������%s���������ʧȥ����\n",dpk.getAddress(),fileName);
			}
		}
	}
 
}