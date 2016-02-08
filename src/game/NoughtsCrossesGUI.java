package game;

import java.awt.Point;
import java.util.Observer;

import javax.swing.JFrame;

import ex2.ClientReciever;
import ex2.ClientSender;
import ex2.User;
import gui.Gui;

public class NoughtsCrossesGUI 
{
	private JFrame frame;
	private Gui lobby;
	public NoughtsCrossesGUI(User user)
	{
		lobby = new Gui();
		
		frame = new JFrame("Noughts and Crosses, User: "+user.toString());
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.setSize(500, 600);
	    frame.setLocation(new Point(500,200));
	    frame.setResizable(false);
	    frame.add(lobby);
	    frame.setVisible(true);
	}
	
	public Observer startGame(String start_opponent){
		// new game with opponent
		
		NoughtsCrosses game = new NoughtsCrosses();
		NoughtsCrossesComponent comp = new NoughtsCrossesComponent(game,start_opponent);
		
		JFrame frame = new JFrame("XOX against "+start_opponent);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				ClientSender.addMessage("quit: "+ClientReciever.getContents(start_opponent));
		    	frame.dispose();
		        
		    }
		});
	    frame.setSize(500, 600);
	    frame.setLocation(new Point(500,200));
	    frame.setResizable(false);
	    frame.add(comp);
	    frame.setVisible(true);
	    return comp;
	}
	public void close (){
		frame.dispose();
	}
	public Gui getGui(){
		return lobby;
	}
	public void refresh(String data){
		lobby.refresh(data);
	}
}
