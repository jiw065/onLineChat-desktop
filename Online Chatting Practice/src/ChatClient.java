import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class ChatClient extends JFrame {

	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	Socket s;
	DataOutputStream dos;
	DataInputStream dis;
	// action listener will act to all the events so do not use keyaction listener
	private class TFListiner extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			String text = "";
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				text = tfTxt.getText();				
			}
			
			writeToServer(text);
			taContent.setText(readFromServer());
			tfTxt.setText("");
		}
		
	}
	private class RefreshThread implements Runnable{

		@Override
		public void run() {
			while(true) {
				Robot robot;
				try {
					robot = new Robot();
					robot.keyPress(KeyEvent.VK_F5);
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
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
		
		// exit the application when closing the window
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {	
				disConnectToServer();
				System.exit(0);
			}
		});
		taContent.setEditable(false);
		
		tfTxt.addKeyListener(new TFListiner());
		new Thread(new RefreshThread()).start();
		
	}

	private void writeToServer(String str) {
		try {
			dos.writeUTF(str);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private String readFromServer() {
		String str = "";
		System.out.println("reading"); //test
		try {
			str = dis.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
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
			writeToServer("EXIT");
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
