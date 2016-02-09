package game;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ex2.ClientReciever;
import ex2.ClientSender;

public class NoughtsCrossesComponent extends JPanel implements Observer
{
	private String opponent;
	private NoughtsCrossesModel model;
	
	public NoughtsCrossesComponent(NoughtsCrosses game, String opponent)
	{
		super();
		this.opponent = ClientReciever.getContents(opponent);
		if(ClientReciever.getPrefix(opponent).equals("playGame")){
			model = new NoughtsCrossesModel(game,NoughtsCrosses.CROSS);
		}else{
			model = new NoughtsCrossesModel(game,NoughtsCrosses.NOUGHT);
		}
		
		BoardView board = new BoardView(model);
		ButtonPanel controls = new ButtonPanel(model);
		model.addObserver(board);
		
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updating");
		String message = ClientReciever.getGameUpdate();
		String prefix = ClientReciever.getPrefix(message);
		if(prefix.equals("quit")){
			
			System.out.println(ClientReciever.getContents(message));
			System.out.println(opponent);
			if(ClientReciever.getContents(message).equals(opponent)){
				JOptionPane.showConfirmDialog(this, "Your opponent "+opponent+" has quit.", "You win"
						, JOptionPane.OK_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION);
			}
			
		}else if(ClientReciever.getPrefix(ClientReciever.getGameUpdate()).equals(opponent)){
			
			
		}
		
	}
}
