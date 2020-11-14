package network;

/*
 * 使用udp socket实现文件传输
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
 
	// 文件的保存路径
	private String fileDir;
	// socket服务器端口号
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
	 * 文件下载
	 */
	@Override
	public void run() {
 
		int bufferSize = 8192;
		byte[] buf = new byte[bufferSize];
 
		DatagramPacket dpk = null;
		DatagramSocket dsk = null;
 
		DataOutputStream fileOut = null;//文件输出流
		long passedlen = 0;//完成传输的大小
		long len = 0;//文件的大小
		
		int readSize = 0;//每次读取数据的大小
		
		String fileName = null;//接受的文件名
 
		try {
			dpk = new DatagramPacket(buf, buf.length,new InetSocketAddress(InetAddress.getByName("localhost"), port));  
			dsk = new DatagramSocket(port + 1, InetAddress.getByName("localhost"));  
			
			// public Socket accept() throws
			// IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。
			System.out.println("等待客户端链接");
			
			dsk.receive(dpk);
			
			
			// 本地保存路径，文件名会自动从服务器端继承而来。
			// 获取文件名
			
			fileName = new String(buf).trim();
			
			fileDir = fileDir + dpk.getAddress().toString().split("/")[1];
			
			File file = new File(fileDir);
			
			if(!file.exists())
			{
				file.mkdir();
			}
			
			String filePath = fileDir + "\\" + fileName;
						
			dsk.receive(dpk);
			
			System.out.println("buf文件的路径" + filePath + "\n");
			
			file = new File(filePath);
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			
			len = Long.parseLong(new String(buf, 0, dpk.getLength()));
 
			System.out.println("开始接收文件!" + "\n");
			
			
			dsk.receive(dpk);
			while ((readSize = dpk.getLength()) != 0) {
 
				passedlen += readSize; 
				//在图形化界面，该部分可以用来作为进度条
				//System.out.println("文件接收了" + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, readSize);
				fileOut.flush();
				dsk.receive(dpk);
			}
			
			if(passedlen != len)
			{
				System.out.printf("IP:%s发来的%s传输过程中失去连接\n",dpk.getAddress(),fileName);
				file.delete();//将缺损文件删除
			}
			else
				System.out.println("接收完成，文件存为" + filePath + "\n");
 
 
		} catch (Exception e) {
			System.out.println("接收消息错误" + "\n");
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
				System.out.printf("IP:%s发来的%s传输过程中失去连接\n",dpk.getAddress(),fileName);
			}
		}
	}
 
}