package network;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
 /*
  * ʹ��udp socketʵ���ļ�����
  */
 
public class Client {
    // �ϴ����ļ�·��������main�������޸�
    private String filePath;
    // socket��������ַ�Ͷ˿ں�
    private String host;
    private int port;
 
    public String getFilePath() {
        return filePath;
    }
 
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
 
    public int getPort() {
        return port;
    }
 
    public void setPort(int port) {
        this.port = port;
    }
 
    public String getHost() {
        return host;
    }
 
    public void setHost(String host) {
        this.host = host;
    }
    
    public static void main(String[] args) {
        Client client = new Client();
        client.setHost("127.0.0.1");
        client.setPort(9005);
        client.setFilePath("E:\\");
        client.uploadFile("test.txt");
    }
 
    /**
     * �ͻ����ļ��ϴ�
     * @param fileName �ļ���
     */
    public void uploadFile(String fileName) {
 
        DatagramSocket dsk = null;
        DatagramPacket dpk = null;
        DataInputStream fis = null;
        try {
            dsk = new DatagramSocket(port, InetAddress.getByName(host));
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            dpk = new DatagramPacket(buf, buf.length,new InetSocketAddress(InetAddress.getByName(host), port + 1)); 
            // ѡ����д�����ļ�
            File fi = new File(filePath + fileName);
            System.out.println("�ļ�����:" + (int) fi.length());
            
            fis = new DataInputStream(new FileInputStream(filePath + fileName));
            
            buf = fi.getName().getBytes();
            
            dpk.setData(buf, 0, fileName.length());
            dsk.send(dpk);
            
            String fileLen = Long.toString((long) fi.length());
            
            buf = fileLen.getBytes();            
            System.out.println("buf�ļ�����"+new String(buf));
            
            dpk.setData(buf, 0, fileLen.length());
            dsk.send(dpk);
            
            while (true) {
                int read = 0;
                if (fis != null) {
                    read = fis.read(buf);
                }
 
                if (read == -1) {
                    break;
                }
                dpk.setData(buf, 0, read);
                dsk.send(dpk);
            }
            //������������һ����ֹ�ź�
            dpk.setData(buf, 0, 0);
            dsk.send(dpk);
            System.out.println("�ļ��������");
        } catch (Exception e) {
    		System.out.println("������"+host+":"+port+"ʧȥ����");
            e.printStackTrace();
        }finally{
        	try{
        		if(fis!=null)
        			fis.close();
        		if(dsk != null)
        			dsk.close();
        	}catch(IOException e){
        		e.printStackTrace();
        	}
        }
    }
}