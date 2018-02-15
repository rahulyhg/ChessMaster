package chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Game implements Serializable 
{
	private static final long serialVersionUID = -456686249905185825L;
	public final String player1, player2, colour1, colour2;
	public final Integer mode;
	private ArrayList<Move> allMoves;
	private final long startTime;
	private String toPlay;
	
	public Game(int mode, String player1, String player2, 
			String colour1, String colour2, String toPlay) throws Exception
	{
		System.out.println(player1+" "+player2+" "+colour1+" "+colour2); 
		if(player1 == null || player2 == null)
			throw new Exception("Null player exception in Game");
		
		if(colour1 != Board.White && colour1 != Board.Black 
			&& colour2 != Board.White && colour2 != Board.Black)
			throw new Exception("Invalid colour exception in Game");
		
		if(mode != 1 && mode != 2)
			throw new Exception("Invalid mode exception in Game");
		
		this.player1 = player1;
		this.player2 = player2;
		this.mode = mode;
		this.colour1 = colour1;
		this.colour2 = colour2;
		this.startTime = System.nanoTime();
		
		allMoves = new ArrayList<>();
		this.toPlay = toPlay;
	}
	
	public String toString()
	{
		return player1 + " vs " + player2 + " @ "+ startTime;
	}
	
	/*
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	@Override 
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Game))
			return false;
		return this.hashCode() == obj.hashCode();
	}*/
	
	/**
	 * Fetches previous games from a file named gameData.dat
	 * */
	public static ArrayList<Game> getGamesList()         
	{
		Game tempGame;
		ObjectInputStream input = null;
		ArrayList<Game> games = new ArrayList<Game>();
		try
		{
			File infile = new File(System.getProperty("user.dir")+ 
					File.separator + "gameData.dat");
			input = new ObjectInputStream(new FileInputStream(infile));
			try
			{
				while(true)
				{
					tempGame = (Game) input.readObject();
					games.add(tempGame);
				}
			}
			catch(EOFException e)
			{
				input.close();
			}
		}
		catch (FileNotFoundException e)
		{
			games.clear();
			return games;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			try {input.close();} 
			catch (IOException e1) {}
			JOptionPane.showMessageDialog(null, 
					"Unable to read the required Game files!");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,	"Game Data File Corrupted!");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return games;
	}
	
	/**
	 * Stores the the current game at the end.
	 **/
	public void storeGame()
	{
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		Game tempGame;
		File inputFile=null;
		File outputFile=null;
		try
		{
			inputFile = new File(System.getProperty("user.dir")+ File.separator 
						+ "gameData.dat");
			outputFile = new File(System.getProperty("user.dir")+ File.separator 
						+ "tempFile.dat");
		} 
		catch (SecurityException e)
		{
			JOptionPane.showMessageDialog(null, "Read-Write Permission Denied.");
			e.printStackTrace();
			assert(false);
		} 
		boolean gameDoesntExist;
		try
		{
			if(outputFile.exists() == false)
				outputFile.createNewFile();
			if(inputFile.exists() == false)
			{
				output = new ObjectOutputStream(
						new java.io.FileOutputStream(outputFile,true));
				output.writeObject(this);
			}
			else
			{
				input = new ObjectInputStream(new FileInputStream(inputFile));
				output = new ObjectOutputStream(
						new FileOutputStream(outputFile));
				gameDoesntExist=true;
				try
				{
					while(true)
					{
						tempGame = (Game)input.readObject();
						if (tempGame.equals(this))
						{
							output.writeObject(this);
							gameDoesntExist = false;
						}
						else
							output.writeObject(tempGame);
					}
				}
				catch(EOFException e)
				{
					input.close();
				}
				if(gameDoesntExist)
					output.writeObject(this);
			}
			inputFile.delete();
			output.close();
			File newf = new File(System.getProperty("user.dir")+ File.separator 
					+ "gameData.dat");
			if(outputFile.renameTo(newf) == false)
				System.out.println("File Renameing Unsuccessful");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					"Unable to read/write the required Game files!");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Stores move stack into ArrayList of moves of the game, so that
	 * reloading the game becomes easy.
	 * */
	public void storeMoves(Movement movement, String toPlay)
	{
		Stack<Move> moves = movement.getPastMoves();
		allMoves = new ArrayList<>();	//this has to be emptied.
		for(int i=0; i<moves.size(); i++)
		{
			allMoves.add(moves.get(i));
		}
		this.toPlay = toPlay;
		//converting stack to ArrayList.
	}
	
	public Movement reloadGame(Board board)
	{
		Movement movement = board.getMovement();
		for(Move move : allMoves)
		{
			//System.out.println(move);
			//board.print();
			movement.playMove(move);
		}
		return movement;
	}
}
