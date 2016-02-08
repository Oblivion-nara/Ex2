package ex2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	
	public void offer(String r){
		queue.offer(r);
	}
	
	public String take() throws InterruptedException{
		
		while(true){
				return queue.take();
		}
		
	}
}
