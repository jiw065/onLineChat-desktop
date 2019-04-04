import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	List<clientThread> cList = new ArrayList<clientThread>();

	private class clientThread implements Runnable {
		private Socket ts = null;
		private boolean bConnected = false;
		private DataOutputStream dos = null;
		private DataInputStream dis = null;

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
				writeToAllClients("Client " + ts.getPort() + " is on line");
				while (bConnected) {
					String str = dis.readUTF();
					if (str.equals("EXIT")) {
						break;
					}
					// need to send to all the clients
					str = "Client " + ts.getPort() + ": " + str;
					writeToAllClients(str);

				}
			} catch (IOException e) {
				System.out.println("Client " + ts.getPort() + " reading error");
			} finally {
				bConnected = false;
				System.out.println("disconnecting client " + ts.getPort());
				try {
					dis.close();
					dos.flush();
					dos.close();
					ts.close();

				} catch (IOException e) {
					System.out.println("client " + ts.getPort() + "closing error");
				}
			}

		}

		private void setThreadSocket(Socket s) {
			ts = s;
		}

		private void writeToAllClients(String s) {
			for (clientThread c : cList) {
				try {
					c.dos.writeUTF(s);
				} catch (IOException e) {
					System.out.println("Client " + c.ts.getPort() + " sendind error");
				}
			}
		}

	}

	private void serverStart() {
		boolean connected = false;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(Constant.PORT_NUMBER);
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
				cList.add(ct);
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