package game;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class NoughtsCrossesComponent extends JPanel implements Observer
{
	public NoughtsCrossesComponent(NoughtsCrosses game, String opponent)
	{
		super();
		NoughtsCrossesModel model = new NoughtsCrossesModel(game);
		
		BoardView board = new BoardView(model);
		ButtonPanel controls = new ButtonPanel(model);
		
		model.addObserver(board);
		
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updateing");
	}
}
