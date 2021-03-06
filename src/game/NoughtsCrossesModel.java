package game;

import java.util.Observable;

import ex2.ClientSender;

public class NoughtsCrossesModel extends Observable
{
	private NoughtsCrosses oxo;
	private String opponent;
	
	public NoughtsCrossesModel(NoughtsCrosses oxo,String opponent)
	{
		super();
		this.oxo = oxo;
		this.opponent = opponent;
	}
	
/**
Get symbol at given location
@param i the row
@param j the column
@return the symbol at that location
*/
	public int get(int i, int j)
	{
		return oxo.get(i, j);
	}


/**
Is it cross's turn?
@return true if it is cross's turn, false for nought's turn
*/	
	public boolean isCrossTurn()
	{
		return oxo.isCrossTurn();
	}

/**
Let the player whose turn it is play at a particular location
@param i the row
@param j the column
*/
	public void turn(int i, int j)
	{
		oxo.turn(i, j);
		setChanged();
		notifyObservers();
		ClientSender.addMessage("gameUpdate: "+opponent+": "+i+","+j);
	}
	public void remoteTurn(int i, int j)
	{
		oxo.turn(i, j);
	}
	
		
/**
Determine who (if anyone) has won
@return CROSS if cross has won, NOUGHT if nought has won, otherwise BLANK
*/
	public int whoWon()
	{
		return oxo.whoWon();
	}
	
	
/**
Start a new game
*/
	public void newGame()
	{
		oxo.newGame();
		setChanged();
		notifyObservers();
	}
	
}