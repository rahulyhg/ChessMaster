package piece;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

class KingTest {
	
	private Board board;
	private King king;
	@BeforeEach
	void setUp() throws Exception 
	{	
		board= new Board();	// b is a new empty board.
		king = null;		
		// king can't be constructed without an initial position.
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		board = null;
		king = null;
	}

	@Test
	void toStringTest() 
	{
		king = new King(Board.White, new Cell(Board.rowMin, Board.colMin));
		//Made a new white king at cell A1 of the board.
		assertEquals(Board.White.charAt(0)+"K", king.toString(),
				"toString method of a white King object should return WK");
		
		king = new King(Board.Black, new Cell(Board.rowMin, Board.colMin));
		//Made a new white king at cell A1 of the board.
		assertEquals(Board.Black.charAt(0)+"K", king.toString(),
				"toString method of black King object should return BK");
	}
	
	@Test
	void getColourTest() 
	{
		king = new King(Board.White, new Cell(Board.rowMin, Board.colMin));
		//Made a new white king at cell A1 of the board.
		assertEquals(Board.White, king.getColour(),
				"getColour method of a white King object should return White");
		
		king = new King(Board.Black, new Cell(Board.rowMax, Board.colMax));
		//Made a new black king at cell h8 of the board.
		assertEquals(Board.Black, king.getColour(),
				"getColour method of a black King object should return Black");
	}

	@Test
	void movesFromCornerTest()
	{
		king = new King(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
		//Made a new white king at cell A1 of the board.
		//king.getAllMoves(board);
		
		//king must be able to move to all 3 cells just beside him.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(Board.rowMin+1), Board.colMin), board));
		assertTrue(king.canMoveTo(
			board.getCellAt(Board.rowMin, (char)(Board.colMin+1)), board));
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+1)), board));
		//for(Cell c: king.moves)
			//System.out.println(c);
		assertEquals(3, king.moves.size());
	}
	
	@Test
	void movesFromTerminalRowTest()
	{
		Cell pos = board.getCellAt(Board.rowMin, (char)(Board.colMin+1));
		king = new King(Board.White, pos);
		//Made a new white king at cell b1 of the board.
		king.getAllMoves(board);
		
		//King must be able to move to all 5 cells just beside him.
		//This is b2.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), pos.col), board));
		//This is c1.
		assertTrue(king.canMoveTo(
			board.getCellAt(pos.row, (char)(pos.col+1)), board));
		//This is a1.
		assertTrue(king.canMoveTo(
			board.getCellAt(pos.row, (char)(pos.col-1)), board));
		//This is c2.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), (char)(pos.col+1)), board));
		//This is a2.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), (char)(pos.col-1)), board));
		
		//for(Cell c: king.moves)
			//System.out.println(c);
		assertEquals(5, king.moves.size());
	}
	
	@Test
	void movesFromTerminalColTest()
	{
		Cell pos = board.getCellAt((char)(Board.rowMin+1), Board.colMin);
		king = new King(Board.White, pos);
		//Made a new white king at cell a2 of the board.
		king.getAllMoves(board);
		
		//King must be able to move to all 5 cells just beside him.
		//This is a1.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row-1), pos.col), board));
		//This is a3.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), pos.col), board));
		//This is b1.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row-1), (char)(pos.col+1)), board));
		//This is b2.
		assertTrue(king.canMoveTo(
			board.getCellAt(pos.row, (char)(pos.col+1)), board));
		//This is b3.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), (char)(pos.col+1)), board));
		
		//for(Cell c: king.moves)
			//System.out.println(c);
		assertEquals(5, king.moves.size());
	}
	
	@Test
	void movesFromNormalCellTest()
	{
		Cell pos = board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+1));
		king = new King(Board.White, pos);
		//Made a new white king at cell b2 of the board.
		king.getAllMoves(board);
		
		//King must be able to move to all 8 cells just beside him.
		//This is a1.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row-1), (char)(pos.col-1)), board));
		//This is b1.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row-1), pos.col), board));
		//This is c1.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row-1), (char)(pos.col+1)), board));
		//This is a2.
		assertTrue(king.canMoveTo(
			board.getCellAt(pos.row, (char)(pos.col-1)), board));
		//This is a3.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), (char)(pos.col-1)), board));
		//This is c2.
		assertTrue(king.canMoveTo(
			board.getCellAt(pos.row, (char)(pos.col+1)), board));
		//This is c3.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), (char)(pos.col+1)), board));
		//This is b1.
		assertTrue(king.canMoveTo(
			board.getCellAt((char)(pos.row+1), pos.col), board));
				
		//for(Cell c: king.moves)
			//System.out.println(c);
		assertEquals(8, king.moves.size());
	}

}