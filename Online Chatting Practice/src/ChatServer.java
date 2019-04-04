import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class ChatServer {
	List<String> strList = new ArrayList<String>(); 
	
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
				while (bConnected) {
					String str = dis.readUTF();
					if(!str.equals("")) {
						str = "Client "+ts.getPort()+": "+str+"\n";
						strList.add(str);
					}
					
					String allStr = "";
					if(str.equals("EXIT")) break;
					if(!strList.isEmpty()) {
						for(String s:strList) {
							allStr+=s;
						}
					}
					if(strList.size()>100) {
						strList.clear();
						allStr = "";
					}
					dos.writeUTF(allStr);
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

	private void serverStart() {
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
