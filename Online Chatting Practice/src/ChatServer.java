import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(6666);
			while(true) {
				boolean bConnected = false;
				Socket s = ss.accept();
				bConnected = true;
				System.out.println("connected to a client"); //delete
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				while(bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
				}
				dis.close();
				dos.flush();
				dos.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
