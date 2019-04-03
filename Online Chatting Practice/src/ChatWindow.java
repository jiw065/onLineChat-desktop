import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ChatWindow extends JFrame {
	TextField tfTxt = new TextField();
	TextArea taConten = new TextArea();
	public void showWindow() {
		this.setLocation(Constant.WINDOW_X, Constant.WINDOW_Y);
		this.setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
		this.setTitle("Online Chat Practice");
		this.setVisible(true);
		this.add(tfTxt,BorderLayout.SOUTH);
		this.add(taConten,BorderLayout.NORTH);
		pack();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	

	
	

	public static void main(String[] args) {
		ChatWindow c = new ChatWindow();
		c.showWindow();

	}
}
