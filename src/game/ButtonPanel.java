package game;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ex2.ClientReciever;
import ex2.ClientSender;

public class ButtonPanel extends JPanel implements Observer
{

	JLabel turn;
	NoughtsCrossesModel model;
	
	public ButtonPanel(NoughtsCrossesModel model,String opponent,JComponent comp)
	{
		super();
		this.model = model;

		turn = new JLabel("X");
		turn.setFont(new Font("Verdana",Font.BOLD,50));
		JButton quit = new JButton("QUIT");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClientSender.addMessage("quit: "+opponent);
				ClientReciever.removeOpponent(opponent);
				((JFrame) SwingUtilities.getRoot(comp)).dispose();;
			}
		});
		add(turn);
		add(quit);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		remoteUpdate();
		
	}
	public void remoteUpdate(){
		if(model.isCrossTurn()){
			turn.setText("X");
		}else{
			turn.setText("O");
		}
	}
}
