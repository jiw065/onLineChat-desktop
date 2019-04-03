import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ChatWindow extends JFrame {
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	//action listener will act to all the events so do not use keyaction listener
	private class TFListiner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = tfTxt.getText();
			taContent.append(text);
			taContent.append("\n");
			tfTxt.setText("");
			
		}
		
		
	}
	public void showWindow() {
		this.setLocation(Constant.WINDOW_X, Constant.WINDOW_Y);
		this.setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
		this.setTitle("Online Chat Practice");
		this.setVisible(true);
		this.add(tfTxt,BorderLayout.SOUTH);
		this.add(taContent,BorderLayout.NORTH);
		pack();
		
		//exit the application when closing the window
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		taContent.setEditable(false);
		tfTxt.addActionListener(new TFListiner());
	}
	

	
	

	public static void main(String[] args) {
		ChatWindow c = new ChatWindow();
		c.showWindow();

	}
}
