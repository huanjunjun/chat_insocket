package network;

import java.io.*;
import java.net.*;

public class network {

	public static void main(String [] args) throws IOException
	{
		network nw=new network();
	}
	
	network() throws IOException
	{
		ServerSocket sersocket=new ServerSocket(10000);//设置端口
		System.out.println("服务器监听ing");
		Socket socket=sersocket.accept();//创建端口，准备接受客户端
		
		InputStreamReader isr=new InputStreamReader(socket.getInputStream());
		BufferedReader br=new BufferedReader(isr);
		String message=br.readLine();
		System.out.println(message);//接收流并打印
		
		PrintWriter pw=new PrintWriter(socket.getOutputStream(),true);
		pw.println("---服务器---");
		
		
	}
	
	
}
