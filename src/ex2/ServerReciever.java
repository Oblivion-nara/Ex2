package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ServerReciever extends Thread{
	private String myUser;
	private BufferedReader fromClient;
	private TableOfClients allClients;
	private ServerSender sender;

	public ServerReciever(String user, BufferedReader fromClient, TableOfClients allClients,MessageQueue queue, PrintStream toClient) {
		super();
		this.myUser = user;
		this.fromClient = fromClient;
		this.allClients = allClients;
		
		sender = new ServerSender(queue,toClient);
	}
	
	public void run(){
		
		sender.start();
		
		String message = "";
		String prefix = "";
		try {
			do{
			
				prefix = getPrefix(message);
				String aUser = "";
				switch(prefix){
				case "message":
					allClients.sendToAll(message);
					break;
				case "refresh":
					sender.queue.offer(allClients.getAllUsers());
					break;
				case "connectToUser":
				case "playGame":
					aUser = getContents(message);
					if(!aUser.equals(myUser)){
						allClients.getQueue(aUser).offer(prefix+": "+myUser);
					}
					break;
				case "quit":
					aUser = getContents(message);
					if(!aUser.equals(myUser)){
						allClients.getQueue(aUser).offer(prefix+": "+myUser);
						allClients.getQueue(myUser).offer("quitter: "+aUser);
						allClients.addScore(aUser);
					}
					break;
				case "gameUpdate":
					aUser = getUsername(getContents(message));
					if(!aUser.equals(myUser)){
						allClients.getQueue(aUser).offer(prefix+": "+myUser+": "+getContents(getContents(getContents(message))));
					}
					break;
				case "won":
				case "draw":
					aUser = getContents(message);
					if(!aUser.equals(myUser)){
						allClients.getQueue(aUser).offer(prefix+": "+myUser);
					}
					break;
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					System.err.println("ServerReceiver was interrupted");
				}
				message = fromClient.readLine();
				
				
				
			}while(message != null && !message.equals("exit"));
		} catch (IOException e) {
				System.err.println("was disconnected from user "+myUser);
		}
		allClients.removeUser(myUser);
		System.out.println(myUser+" disconnected");
		sender.interrupt();
	}
	
	private String getPrefix(String message){
		
		String pre = "";
		for(int i = 0; i < message.length();i++){
			char letter = message.charAt(i);
			if(letter == ':'){
				break;
			}
			pre += letter;
		}
		return pre;
		
	}
	private String getContents(String message){
		
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
	private String getUsername(String message){
		
		String user = "";
		int spotted = 0;
		for(int i = 0; i<message.length();i++){
			char letter = message.charAt(i);
			if(letter == ':' ){
				if(spotted > 0){
					break;
				}
				spotted++;
			}

			user += letter;
		}
		return user;
		
	}
}



















