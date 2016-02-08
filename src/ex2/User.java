package ex2;

public class User {

	private long id;
	private String name;

	public User(String name,long id){
		this.name = name;
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return name + ": " + id;
	}
	
	public static User toUser(String nameId){
		
		String name = "";
		String sId = "";
		int id;
		boolean gotName = false;
		
		for (int i = 0; i < nameId.length(); i++) {
			
			
			char character = nameId.charAt(i);
			
			if(character == ':'){
					gotName = true;
					i++;
			}else if(!gotName){
				
				name += character; 
				
			}else{
				
				sId += character;
				
			}
			
		}
		System.out.println(""+name + "=="+sId);
		id = Integer.parseInt(sId);
		
		return new User(name,id);
		
	}
	
}
