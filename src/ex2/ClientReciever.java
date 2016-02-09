package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.JOptionPane;

import game.NoughtsCrossesGUI;
import gui.Chat;

public class ClientReciever extends Observable implements Runnable{

	private BufferedReader fromServer;
	private ClientSender sender;
	private Chat chat;
	private NoughtsCrossesGUI mainView;
	
	private static String gameMessage;
	private static ConcurrentMap<String, Observer> opponents;
	
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
	    opponents = new ConcurrentHashMap<String, Observer>();
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
	    			if(!opponents.containsKey(cont)){
	    				int i = JOptionPane.showConfirmDialog(mainView.getGui(),cont+""
	    						+ ", would like to play Tic Tac Toe \ndo you accept?"
	    						,"Game Request",JOptionPane.YES_NO_OPTION);
	    				if(i == JOptionPane.YES_OPTION){
	    					Observer opp = mainView.startGame(message);
	    					addObserver(opp);
	    					opponents.putIfAbsent(cont, opp);
	    					ClientSender.addMessage("playGame: "+cont);
	    				}
	    			}else{
	    				System.out.println("its still here");
	    			}
	    			break;
	    		case "playGame":
					Observer opp = mainView.startGame(message);
					addObserver(opp);
					opponents.putIfAbsent(cont, opp);
	    			break;
	    		case "quit":
	    			System.out.println("this switch is running");
	    			upDateGames("quit: "+cont);
	    			deleteObserver(opponents.get(cont));
	    			opponents.remove(cont);
	    			break;
	    		case "gameUpdate":
	    			upDateGames(message);
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
	
	public static void removeOpponent(String opponent){
		opponents.remove(opponents);
	}
	
	public synchronized void upDateGames(String gameMessage){
		
		ClientReciever.gameMessage = gameMessage;
		setChanged();
		notifyObservers();
		
	}
	
	public static synchronized String getGameUpdate(){
		return gameMessage;
	}
	
}









