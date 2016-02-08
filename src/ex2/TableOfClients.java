package ex2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TableOfClients {
	
	private ConcurrentMap<String,MessageQueue> userMessages = new ConcurrentHashMap<String, MessageQueue>();
	private ConcurrentMap<String,Integer> userScores = new ConcurrentHashMap<String, Integer>();
	
	public void add(String user){
		userMessages.put(user, new MessageQueue());
		userScores.put(user, 0);
	}
	public MessageQueue getQueue(String user){
		return userMessages.get(user);
	}
	public void removeUser(String user){
		userMessages.remove(user,userMessages.get(user));
		userScores.remove(user,userScores.get(user));
	}
	public void sendToAll(String message){
		
		for(MessageQueue queue : userMessages.values()){
			queue.offer(message);
		}
		
	}
	public void addScore(String user){
		userScores.replace(user, (userScores.get(user).intValue()+1));
	}
	public String getAllUsers(){
		String allUsers = "refresh: ";
		for(String user : userMessages.keySet()){
			allUsers += user+"-";
		}
		for(String user : userScores.keySet()){
			allUsers += userScores.get(user)+";"+user+"_";
		}
		return allUsers;
	}

}




















