package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;

import game.NoughtsCrossesGUI;
import gui.Chat;

public class ClientReciever extends Observable implements Runnable{

	private BufferedReader fromServer;
	private ClientSender sender;
	private Chat chat;
	private NoughtsCrossesGUI mainView;
	
	private static String gameMessage;
	private ArrayList<String> opponents;
	
	public ClientReciever(BufferedReader fromServer, PrintStream toServer, User user, Chat chat,NoughtsCrossesGUI mainView){
		super();
		this.fromServer = fromServer;
		this.chat = chat;
		this.mainView = mainView;

		sender = new ClientSender(toServer,user);
	}

	@Override
	public void run() {

	    sender.start();
	    String message = "";
	    gameMessage = "";
	    opponents = new ArrayList<String>();
	    try{

	    	do{
	    	
	    		//Receive and handle requests appropriately
	    		
    			String cont = getContents(message);
	    		switch(getPrefix(message)){
	    		case "message":
	    			addMessage(cont);
	    			break;
	    		case "refresh":
	    			mainView.refresh(cont);
	    			break;
	    		case "connectToUser":
	    			if(!opponents.contains(cont)){
	    				int i = JOptionPane.showConfirmDialog(mainView.getGui(),cont+" would like to play Tic Tac Toe \ndo you accept?"
	    						,"Game Request",JOptionPane.YES_NO_OPTION);
	    				if(i == JOptionPane.YES_OPTION){
	    					addObserver(mainView.startGame(message));
	    					ClientSender.addMessage("playGame: "+cont);
	    				}
	    				opponents.add(cont);
	    			}
	    			break;
	    		case "playGame":
    				opponents.add(cont);
    				addObserver(mainView.startGame(message));
	    			break;
	    		case "quit":
	    			opponents.remove(cont);
	    			ClientSender.addMessage("win");
	    			break;
	    		case "":
	    			break;
	    		}
	    	
	    	
	    		message = fromServer.readLine();
	    		
	    	
	    		
	    	}while(message != null && !message.equals("exit"));
	    
			
	    }catch(IOException e){
			System.err.println("Somthing is breaking.");
		}
	    sender.interrupt();
	}
	
	public static String getPrefix(String message){
		
		String pre = "";
		for(int i = 0; i<message.length();i++){
			char letter = message.charAt(i);
			if(letter == ':'){
				break;
			}
			pre += letter;
		}
		return pre;
		
	}
	public static String getContents(String message){
		
		int i;
		for(i = 0; i<message.length();i++){
			char letter = message.charAt(i);
			if(letter == ':'){
				i += 2;
				break;
			}
		}
		return message.substring(i);
		
	}
	private void addMessage(String message){
		chat.addMessage(message);
	}
	public synchronized void upDateGames(String gameMessage){
		
		ClientReciever.gameMessage = gameMessage;
		setChanged();
		notifyObservers();
		
	}
	
	public static synchronized String getMessage(){
		return gameMessage;
	}
	
}









