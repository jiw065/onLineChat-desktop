import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	private class clientThread implements Runnable {
		Socket ts = null;
		boolean bConnected = false;
		DataOutputStream dos = null;
		DataInputStream dis = null;

		@Override
		public void run() {
			System.out.println("Connected to Client " + ts.getPort());	
			bConnected = true;

			try {
				dis = new DataInputStream(ts.getInputStream());
				dos = new DataOutputStream(ts.getOutputStream());
			} catch (IOException e) {
				System.out.println("initialize data stream error");
				e.printStackTrace();
			}

			try {
				while (bConnected) {
					String str = dis.readUTF();
					if(str.equals("EXIT")) break;
					System.out.println(str);
				}
			} catch (IOException e) {
				System.out.println("Client "+ts.getPort()+" reading error");
			} finally {
				bConnected = false;
				System.out.println("disconnecting client "+ts.getPort());
				try {
					dis.close();
					dos.flush();
					dos.close();
					ts.close();

				} catch (IOException e) {
					System.out.println("client "+ts.getPort()+ "closing error" );
				}
			}

		}

		private void setThreadSocket(Socket s) {
			ts = s;
		}

	}

	public void serverStart() {
		boolean connected = false;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(6666);
			connected = true;
		} catch (IOException e) {
			System.out.println("Server connection error");
			e.printStackTrace();
		}
		while (connected) {
			try {
				Socket s = ss.accept();
				clientThread ct = new clientThread();
				ct.setThreadSocket(s);
				new Thread(ct).start();

			} catch (IOException e) {
				System.out.println("Server connect to client error");
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new ChatServer().serverStart();
	}

}
