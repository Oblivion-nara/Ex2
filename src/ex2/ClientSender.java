package ex2;

import java.io.PrintStream;
import java.util.ArrayList;

import javax.sound.midi.Transmitter;

public class ClientSender extends Thread {

	public static User myUser;
	private PrintStream toServer;
	
	private static ArrayList<String> message;
	private static boolean transmit;  

	public ClientSender(PrintStream toServer, User myUser) {
		super();
		this.toServer = toServer;
		ClientSender.myUser = myUser;
	}
	
	public void run(){
		message = new ArrayList<String>();
		message.add("refresh");
		transmit = true;
		String text = "";
		do{
			while(!transmit){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					message.set(0, "exit");
					break;
				}
			}
			
			while(!message.isEmpty()){
				text = message.remove(0);
				toServer.println(text);
			}
			transmit = false;
			
		}while(!text.equals("exit"));
		
		
	}
	
	public static synchronized void addMessage(String message){
		ClientSender.message.add(message);
		ClientSender.transmit = true;
	}

}







