package ex2;

public class Request {

	public static final int CONNECTION = 0; 	// initial connection to server 
	public static final int MESSAGE = 1;		// message: (from)User: followed by message
	public static final int GAMEUPDATE = 2;		// game: opponent: game coordinates (x,y)
	public static final int CONNECTTOUSER = 3;	// connectToUser: user 
	public static final int PLAYUSER = 4;		// play: user (automatically launches game)
	public static final int REFRESH = 5;		// refresh (player list and scoreboard)
	public static final int GIVEUP = 6;			// quit: myUser
	public static final int EXIT = 7;			// exit
	
	
	
//	private final int requestType;
//	
//	private final String username;
//	private final int id;
//	
//	private final User user;
//	
//	public int getRequestType(){
//		return requestType;
//	}
//	
//	
//	public Request(String username){
//		requestType = CONNECTION;
//		this.username = username;
//		id = -1;
//		user = null;
//	}
//	public Request(String username,int id){
//		requestType = CONNECTTOUSER;
//		this.username = username;
//		this.id = id;
//		user = null;
//	}
//	public String getUsername(){
//		return username;
//	}
//	public int getId(){
//		return id;
//	}
//	
//	
//	public Request(User user){
//		requestType = RETURNUSER;
//		this.user = user;
//		username = null;
//		id = -1;
//	}
//	public User getUser(){
//		return user;
//	}
//	
//	
//	
	
}

















