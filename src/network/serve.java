package network;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class serve extends JFrame implements ActionListener{
	
	//类中先定义需要的类型
	JTextArea  jta=null;
	JButton  button1=null;
	JTextField jtf=null;
	JPanel panel=null;
	JScrollPane jsp=null;//gui
	
	PrintWriter pw=null;//net writer
	
	public static void main(String [] args) throws IOException
	{
		serve nw=new serve();
	}
	
	serve() throws IOException
	{
		jta=new JTextArea();
		button1=new JButton("send");
		button1.addActionListener(this);
		
		jtf=new JTextField(30);
		panel=new JPanel();
		jsp=new JScrollPane(jta);
		
		panel.add(jtf);  panel.add(button1);
       
		this.add(jsp);
		this.add(panel,BorderLayout.SOUTH);
		
		this.setSize(500, 500);
		this.setLocation(200,200);
		this.setTitle("服务器端");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ServerSocket sersocket=new ServerSocket(9999);//设置端口
//		System.out.println("服务器监听ing");
		Socket socket=sersocket.accept();//创建端口，准备接受客户端
		
		jta.setText("成功连接客户端\n");
		
		
		InputStreamReader isr=new InputStreamReader(socket.getInputStream());
		BufferedReader br=new BufferedReader(isr);//接收端口信息

		InputStreamReader isr1=new InputStreamReader(System.in);
		BufferedReader br1=new BufferedReader(isr1);//接收键盘信息
		
		pw=new PrintWriter(socket.getOutputStream(),true);//初始化



		while(true)
		{
//			String mymessage=br1.readLine();
//			pw.println(mymessage);
			
			String message=br.readLine();
//			System.out.println("客户端对我说：");
//			System.out.println("---"+message+"\n");
			jta.append("客户端对我说：\n");
			jta.append("---"+message+"\n");
			
		}
		
		
	}



	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==button1)
		{
			String message1=jtf.getText();
			jta.append("我说："+message1+"\n");
			pw.println(message1);
			jtf.setText("");
		}
	}
}
