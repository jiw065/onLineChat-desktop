import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
/**
 * this is a simple desktop chat app client
 * @author Amber
 *
 */
public class ChatClient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	Socket s;
	DataOutputStream dos;
	DataInputStream dis;
	boolean receving = true;
	Thread rt = new Thread(new RecivingThread());

	// action listener will act to all the events so do not use keyaction listener
	private class TFListiner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = tfTxt.getText();
			// taContent.setText(text);
			writeToServer(text);
			tfTxt.setText("");

		}

	}

	private class RecivingThread implements Runnable {

		@Override
		public void run() {
			while (receving) {
				taContent.append(readFromServer());
				taContent.append("\n");

			}
		}

	}

	public void showWindow() {
		this.setLocation(Constant.WINDOW_X, Constant.WINDOW_Y);
		this.setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
		this.setTitle("Online Chat Practice");
		this.setVisible(true);
		this.add(tfTxt, BorderLayout.SOUTH);
		this.add(taContent, BorderLayout.NORTH);
		pack();
		connectToServer();
		rt.start();
		// exit the application when closing the window
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disConnectToServer();
				System.exit(0);
			}
		});
		taContent.setEditable(false);
		tfTxt.addActionListener(new TFListiner());
	}

	private void writeToServer(String str) {
		try {
			dos.writeUTF(str);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String readFromServer() {
		String str = "";
		try {
			str = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	private void connectToServer() {

		try {
			s = new Socket("127.0.0.1", Constant.PORT_NUMBER);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void disConnectToServer() {
		try {
			dos.writeUTF("is off line");
			receving = false;
			rt.join();  // the thread should join to the main thread, this actually really close the thread
			
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				
				dos.close();
				dis.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
		}																																																															
	}

	public static void main(String[] args) {
		new ChatClient().showWindow();

	}
}
