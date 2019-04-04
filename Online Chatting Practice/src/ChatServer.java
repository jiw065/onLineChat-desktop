import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		ServerSocket ss = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket s = null;
		try {
			ss = new ServerSocket(6666);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {

			while (true) {
				boolean bConnected = false;
				s = ss.accept();
				bConnected = true;
				System.out.println("connected to a client"); // delete
				 dis = new DataInputStream(s.getInputStream());
				 dos = new DataOutputStream(s.getOutputStream());
				while (bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
				}
				
			}
		} catch (EOFException e) {
			System.out.println("client closed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dis.close();
				dos.flush();
				dos.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

	}

}
