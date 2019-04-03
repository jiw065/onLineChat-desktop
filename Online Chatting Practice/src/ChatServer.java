import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(6666);
			while(true) {
				Socket s = ss.accept();
				System.out.println("connected to a client"); //delete
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
