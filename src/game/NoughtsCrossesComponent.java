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

public class NoughtsCrossesComponent extends JPanel implements Observer {
	private String opponent;
	private NoughtsCrossesModel model;
	private BoardView board;
	private ButtonPanel controls;
	private int playerSymbol;

	public NoughtsCrossesComponent(NoughtsCrosses game, String opponent) {
		super();
		int i = -1;
		this.opponent = ClientReciever.getContents(opponent);
		if (ClientReciever.getPrefix(opponent).equals("playGame")) {
			i = 1;
			playerSymbol = NoughtsCrosses.CROSS;
		} else {
			playerSymbol = NoughtsCrosses.NOUGHT;
		}
		model = new NoughtsCrossesModel(game, this.opponent);

		board = new BoardView(model, i);
		controls = new ButtonPanel(model, this.opponent, this);
		model.addObserver(board);
		model.addObserver(controls);

		setLayout(new BorderLayout());

		add(board, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		String message = ClientReciever.getGameUpdate();
		String prefix = ClientReciever.getPrefix(message);
		switch (prefix) {
		case "quit":
			if (ClientReciever.getContents(message).equals(opponent)) {

				JOptionPane.showConfirmDialog(this, "Your opponent " + opponent + " has quit.", "You win",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION);
				ClientReciever.removeOpponent(opponent);
				((JFrame) SwingUtilities.getRoot(this)).dispose();

			}
			break;
		case "gameUpdate":

			if (getUsername(ClientReciever.getContents(message)).equals(opponent)) {

				String coords = ClientReciever
						.getContents(ClientReciever.getContents(ClientReciever.getContents(message)));
				int x = Integer.parseInt("" + coords.charAt(0));
				int y = Integer.parseInt("" + coords.charAt(2));
				model.remoteTurn(x, y);
				board.remoteUpdate();
				controls.remoteUpdate();

				if (model.whoWon() == playerSymbol) {

					ClientSender.addMessage("won: " + opponent);
					JOptionPane.showConfirmDialog(this, "You win", "You win", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.OK_CANCEL_OPTION);
					ClientReciever.removeOpponent(opponent);
					((JFrame) SwingUtilities.getRoot(this)).dispose();
					;

				} else if (model.whoWon() == NoughtsCrosses.BLANK && NoughtsCrosses.turnCount == 9) {

					ClientSender.addMessage("draw: " + opponent);
					JOptionPane.showConfirmDialog(this, "Its a draw", "Draw", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.OK_CANCEL_OPTION);
					ClientReciever.removeOpponent(opponent);
					((JFrame) SwingUtilities.getRoot(this)).dispose();
					;
				}
			}

			break;
		}
		
	}

	private String getUsername(String message) {

		String user = "";
		int spotted = 0;
		for (int i = 0; i < message.length(); i++) {
			char letter = message.charAt(i);
			if (letter == ':') {
				if (spotted > 0) {
					break;
				}
				spotted++;
			}

			user += letter;
		}
		return user;

	}
}
