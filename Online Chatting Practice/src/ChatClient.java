import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class ChatClient extends JFrame {

	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	Socket s;
	DataOutputStream dos;
	DataInputStream dis;
	// action listener will act to all the events so do not use keyaction listener
	private class TFListiner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = tfTxt.getText();
			taContent.setText(text);
			writeToServer(text);
			tfTxt.setText("");
			
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
		System.out.println("writing"); //test
		try {
			dos.writeUTF(str);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//s.close();
	}
	private void connectToServer() {

		try {
			s = new Socket("127.0.0.1", 6666);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			
			System.out.println("Connect to Server"); // delete
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void disConnectToServer() {
		try {
			dos.writeUTF("exiting");
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ChatClient().showWindow();

	}
}
