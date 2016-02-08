package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public void start(String[] args){
		
		if(args.length != 1){
			System.err.println("Usage: java Server <port-number>");
			System.exit(0);
		}
		
		TableOfClients allClients = new TableOfClients();
		
		long nextId = 1;
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			System.out.println("listening on port "+args[0]);
		} catch (NumberFormatException | IOException e1) {
		    System.err.println("Couldn't listen on port " + args[0]);
		    System.exit(1); // Give up.
		}
		while(true){
			try {
			
				
				Socket socket = serverSocket.accept();

				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String username = "";
				
				username = fromClient.readLine();
				
				String client = username+": "+nextId;

				allClients.add(client);

				PrintStream toClient = new PrintStream(socket.getOutputStream());

				toClient.println(client.toString());
				
				(new ServerReciever(client,fromClient,allClients,allClients.getQueue(client),toClient)).start();
				
				
				nextId++;
			
			} catch (IOException e) {
				System.err.println("A client failed to connect");
			}
		}
		
	}

}







































