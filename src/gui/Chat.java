package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ex2.ClientSender;

public class Chat extends JComponent{
	
	public String[] messages;
	
	public Chat(){
		super();
		
		messages = new String[10];
		
		JList<String> chatting = new JList<>(messages);
		JTextField messageEnter = new JTextField("");
		JButton sendButton = new JButton("SEND");
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!messageEnter.getText().equals("")){
					ClientSender.addMessage("message: "+ClientSender.myUser+ " - "+messageEnter.getText());
					messageEnter.setText("");
				}
			}
		});
		JPanel chatBar = new JPanel();
		chatBar.setBounds(0, 0, 400, 32);
		chatBar.setLayout(new BorderLayout());
		chatBar.add(messageEnter,BorderLayout.CENTER);
		chatBar.add(sendButton,BorderLayout.EAST);
		
		chatting.setBounds(0, 32, 400, 380);
		chatting.setFixedCellHeight(32);
		
		add(chatBar);
		add(chatting);
		
		
		
		
	}
	
	public void addMessage(String message){
		
		
		for(int i = 8; i >= 0; i--){
			messages[i+1] = messages[i];
		}
			messages[0] = message;
		this.repaint();
		
	}

}













