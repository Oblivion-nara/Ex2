package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ex2.ClientSender;

public class Gui extends JComponent {
	
	public static String[] connectedUsers;
	public static String[] userScores;
	private boolean userView = true; 

	public Gui() {

		super();

		connectedUsers = new String[50];
		userScores = new String[50];
		JScrollPane scrollPane = new JScrollPane();
		JList<String> users = new JList<>(connectedUsers);
		users.setFixedCellHeight(20);
		JList<String> scores = new JList<>(userScores);
		scores.setFixedCellHeight(20);
		
		scrollPane.setViewportView(users);
		scrollPane.setBounds(5, 5, 490, 440);

		JButton exit = new JButton("EXIT");
		JButton refresh = new JButton("REFRESH");
		JButton game = new JButton("REQUEST");
		JButton leaderboard = new JButton("SCORES");
		exit.setBounds(5, 455, 115, 90);
		refresh.setBounds(125, 455, 115, 90);
		game.setBounds(245, 455, 115, 90);
		leaderboard.setBounds(365, 455, 115, 90);
		

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClientSender.addMessage("exit");
			}
		});
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClientSender.addMessage("refresh");
			}
		});
		game.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(users.getSelectedValue() != null){
					ClientSender.addMessage("connectToUser: " + users.getSelectedValue());
				}

			}
		});
		leaderboard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(userView){
					scrollPane.setViewportView(scores);
					leaderboard.setText("PLAYERS");
					userView = false;
				}else{
					scrollPane.setViewportView(users);
					leaderboard.setText("SCORES");
					userView = true;
				}

			}
		});

		add(scrollPane);
		add(game);
		add(refresh);
		add(leaderboard);
		add(exit);

	}
	public void refresh(String data){
		
		String word = "";
		ArrayList<String> users = new ArrayList<String>();				// contains all the users currently connected
		ArrayList<String> scores = new ArrayList<String>();				// contains the scores of the user in string format
		ArrayList<Integer> actualScores = new ArrayList<Integer>();		//contians the integer value of each users score
		for(int i = 0; i < data.length(); i++){
			
			char c = data.charAt(i);
			
			switch(c){		// formats the input string of data
			case '-':
				users.add(word);
				word = "";
				break;
			case ';':
				actualScores.add(Integer.parseInt(word));
				word += "       User:  ";
				break;
			case '_':
				scores.add("Score: "+word);
				word = "";
				break;
			default:
				word += c;
				break;	
			}
			
		}
		
		connectedUsers = users.toArray(connectedUsers);
		userScores = scores.toArray(userScores);
		Integer[] nums = new Integer[userScores.length];
		nums = actualScores.toArray(nums);
		
		int numberOfUsers = getNumOfUsers(nums);
		
		for (int i = 0; i < numberOfUsers-1; i++) {			// sorts the scores using a bubble sort
			for (int j = 0; j < numberOfUsers-1; j++) {
				
				if(nums[j] < nums[j+1]){
					String tempS = userScores[j+1];
					int tempI = nums[j+1];
					nums[j+1] = nums[j];
					userScores[j+1] = userScores[j];
					nums[j] = tempI;
					userScores[j] = tempS;
				}
				
			}
		}
		repaint();
		
	}
	private static int getNumOfUsers(Object[] array){		// get the actul entries in the array aka. anything that isnt null
		
		int number = 0;
		
		for(number = 0; number < array.length; number++){
			if(array[number] == null){
				return number-1;
			}
		}
		
		return number;
		
	}

}












