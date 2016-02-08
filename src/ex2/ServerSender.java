package ex2;

import java.io.PrintStream;

public class ServerSender extends Thread {

	public MessageQueue queue;
	private PrintStream toClient;
	
	
	public ServerSender(MessageQueue queue, PrintStream toClient) {
		super();
		this.queue = queue;
		this.toClient = toClient;
		
	}
	public void run(){
		
		try{
			while (true){
		
				String r = queue.take();
				toClient.println(r);
				Thread.sleep(20);
		
			}
		}catch(InterruptedException e){
			toClient.println("exit");
		}
		
	}

}
