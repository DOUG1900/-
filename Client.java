import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;

public class Client {
	class Datasource implements Runnable {
		//�׽ӿڵ����������server��������
		DataOutputStream dos;
		byte[] content;
		Datasource(byte[] bytes,DataOutputStream d) {
			this.content = bytes;
			this.dos= d;
		}
		
		@Override
		public void run() {
			try {
				dos.write(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	class Datareceiver implements Runnable {
		//�׽ӿ�������󣬴�Server��������
		BufferedInputStream bis;
		DataInputStream dis;
		byte[] content;
		Datareceiver(BufferedInputStream b){
			this.bis = b;
			this.dis = new DataInputStream(bis);
		}
		
		@Override
		public void run() {
			int p1 = 4, p2 = 8, p3 = 9;
			int resplen = 73;
			try {
				ByteStream bs = new ByteStream();
				byte[] len = new byte[4];  //totalLength
				byte[] ID = new byte[4];   //commandID
				byte[] status = new byte[1];  //status
				byte[] description = new byte[64];   //description
				byte[] bytes = new byte[resplen];
				if(this.dis.read(bytes) != -1){
					bs.Split(bytes, len, p1, ID, p2, status, p3, description);
				}
				String temp[] = new String(description).split("\0");
				String dsp = temp[0];
				
				System.out.println(dsp);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	String hostName;
	int portNumber;
	Client(){
		this.hostName = "localhost";
		this.portNumber = 12001;
	}
	
	Client(String hostnm, int port){
		this.hostName = hostnm;
		this.portNumber = port;
	}
	
	public static void main(String[] args) throws IOException {
		int ID = 0, op = 0;
		String username, passwd;
		System.out.println("��ѡ�������1.ע��    2.��¼    3.�˳�");
		Scanner input=new Scanner(System.in);
		op = input.nextInt();
		if(op != 3){
			System.out.println("�������û�����");
			username = input.next();
			System.out.println("���������룺");
			passwd = input.next();
			input.close();
			//int length = 8 + username.length() + passwd.length(); //�ֽ���
			int length = 58;
			if(op == 1)
				ID = 1; //ע��
			else
				ID = 3; //��¼
			ReqMsg request = new ReqMsg(ID, length, username, passwd);
			byte[] result = request.getAll();
			
			Client btClient = new Client();
			btClient.start(result);
		}
		else{
			System.exit(0);
		}
	}
	
	void start(byte[] bytestream) throws IOException {
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(this.hostName, this.portNumber);
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			Datasource D1 = new Datasource(bytestream, dos); 
			BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());
			Datareceiver D2 = new Datareceiver(bis);
			
			Thread thread1 = new Thread(D1);
			Thread thread2 = new Thread(D2);
			thread1.start();
			thread2.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


