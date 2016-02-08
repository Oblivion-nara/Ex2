package ex2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import game.NoughtsCrossesGUI;
import gui.Chat;

public class Client {

	public void start(String[] args){

		if (args.length != 3) {
			System.err.println("Usage: java Client <username> <port-number> <machine-name>");
		}
		

		// Initialize information:
		String username = args[0];
		int portnumber = -1;
		try{
			portnumber = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			System.err.println("please only enter numbers for your port.");
			System.exit(0);
		}
		String hostname = args[2];

		BufferedReader fromServer = null;
		PrintStream toServer = null;
		Socket server = null;

		User user = null;
		
		try {
			server = new Socket(hostname, portnumber);
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
			
			toServer.println(username);
			user = User.toUser(fromServer.readLine());
			
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}

		NoughtsCrossesGUI mainView = new NoughtsCrossesGUI(user);
		
		Chat chat = new Chat();
		
	    JFrame globalChat = new JFrame("Global chat");
	    
	    globalChat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    globalChat.setSize(400, 400);
	    globalChat.setLocation(new Point(1000, 400));
	    globalChat.setResizable(false);
	    globalChat.add(chat);
	    globalChat.setVisible(true);
		
	    ClientReciever receiver = new ClientReciever(fromServer,toServer,user,chat,mainView);
	    
	    Thread rec = new Thread(receiver);
	    rec.start();
	    
	    
	    
	    	
	    
	    // Wait for them to end and close sockets.
	    try {
	      rec.join();
	      toServer.close();
	      fromServer.close();
	      server.close();
	      mainView.close();
	      globalChat.dispose();
	    }
	    catch (IOException e) {
	      System.err.println("Something wrong " + e.getMessage());
	      System.exit(1); // Give up.
	    }
	    catch (InterruptedException e) {
	      System.err.println("Unexpected interruption " + e.getMessage());
	      System.exit(1); // Give up.
	    }
	    
	    System.exit(0);
	    
	    
	}

}
