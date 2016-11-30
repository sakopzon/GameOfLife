package OOP.Tests;



import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

import OOP.Solution.*;
import OOP.Solution.Board.Row;

/**
 * [[SuppressWarningsSpartan]]
 */
public class BoardTest {
	class BoardReaderImp implements BoardReader {
		@Override
		public String read() throws IOException, ParseException {
			return 
					DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
					LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
					DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		}
	}
	static final String LIVE = new LiveCell(new Position(0,0)).toString();
	static final String DEAD = new DeadCell(new Position(0,0)).toString();

	static final String DELIM = Board.BOARD_DELIM;

	@Test
	public void boardFromSetConstructor00() {
		String inputString = " |*|\n*| |";
		Set<Cell> input = new HashSet<>();
		input.add(new DeadCell(0,0));
		input.add(new LiveCell(1,0));
		input.add(new LiveCell(0,1));
		input.add(new DeadCell(1,1));
		try {
			String output = new Board(input) + "";
			assert false : "Set shouldn't contain DeadCells";
		} catch (ValidationException e) {
		}
	}
	
	@Test
	public void boardFromSetConstructor01() {
		String inputString = " |*|\n*| |";
		Set<Cell> input = new HashSet<>();
		input.add(new LiveCell(1,0));
		input.add(new LiveCell(0,1));
		try {
			String output = new Board(input) + "";
			assert ((output + "").equals(inputString + "\n")) : "\nexpected \n" + inputString + "\n" + "\nbut was \n" + output;
		} catch (ValidationException e) {
			assert false : "shouldn't thow an exception";
		}
	}
	
	@Test
	public void boardFromSetConstructor02() {
		String inputString = " |*|\n*| |";
		Set<Cell> input = new HashSet<>();
		input.add(new LiveCell(0,-1));
		input.add(new LiveCell(-1,0));
		try {
			String output = new Board(input) + "";
			assert ((output + "").equals(inputString + "\n")) : "\nexpected \n" + inputString + "\n" + "\nbut was \n" + output;
		} catch (ValidationException e) {
			assert false : "shouldn't thow an exception";
		}
	}
	
	@Test
	public void boardFromSetConstructor03() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(2, 0)));
		Board b = new Board(liveCells);
		String expected = 
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n";
		assertEquals(expected, b.toString());
	}
	
	@Test
	public void boardFromSetConstructor04() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(0, 2)));
		Board b = new Board(liveCells);
		String expected = 
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n";
		assertEquals(expected, b.toString());
	}
	
	@Test
	public void boardFromSetConstructor05() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(-5, -5)));
		Board b = new Board(liveCells);
		String expected = 
				LIVE + DELIM + "\n";
		assertEquals(expected, b.toString());
	}
	
	@Test
	public void boardFromSetConstructor06() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(-5, -5)));
		liveCells.add(new LiveCell(new Position(-4, -5)));
		Board b = new Board(liveCells);
		String expected = 
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n";
		assertEquals(expected, b.toString());
	}
	
	@Test
	public void boardFromSetConstructor07() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(-5, -5)));
		liveCells.add(new LiveCell(new Position(-5, -3)));
		Board b = new Board(liveCells);
		String expected = 
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n";
		assertEquals(expected, b.toString());
	}
	
	@Test
	public void boardFromStringConstructor00() {
		String input = "";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getRowList())
				output.append(¢ instanceof LiveCell ? "*" : " ");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}
	
	@Test
	public void boardFromStringConstructor01() {
		String input = "*|";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getRowList())
				output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}

	@Test
	public void boardFromStringConstructor02() {
		String input = " |";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getRowList())
				output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}

	@Test
	public void boardFromStringConstructor03() {
		String input = "*|";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getRowList())
				output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}

	@Test
	public void boardFromStringConstructor04_illegalSimbolException00() {
		String input = "h|";
		StringBuilder output = new StringBuilder("");
		try {
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getRowList())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void boardFromStringConstructor04_illegalSimbolException01() {
		String input = " | |\n |k|";
		StringBuilder output = new StringBuilder("");
		try {
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getRowList())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void boardFromStringConstructor04_notRectangleException00() {
		String input = " |*| | | |*|";
		StringBuilder output = new StringBuilder("");
		try {
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getRowList())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void boardFromStringConstructor04_notRectangleException01() {
		String input = " |*|\n | | |\n*|*|*|";
		StringBuilder output = new StringBuilder("");
		try {
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getRowList())
					output.append((¢ instanceof LiveCell ? Cell.LIVE_SIMBOL : Cell.DEAD_SIMBOL) + Board.BOARD_DELIM);
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void boardFromStringConstructor05() {
		String input = " |*|\n*| |";
		StringBuilder output = new StringBuilder("");
		try {
			for (Row r : (new Board(input)).getBoard()) {
				for (Cell ¢ : r.getRowList())
					output.append((¢ instanceof LiveCell ? Cell.LIVE_SIMBOL : Cell.DEAD_SIMBOL) + Board.BOARD_DELIM);
				output.append(Board.END_OF_LINE);
			}
			assert ((output + "").equals(input + "\n")) : "\nexpected \n" + input + "\n" + "\nbut was \n" + output;
		} catch (IllegalArgumentException e) {
			assert false : "shouldn't thow an exception";
		}
	}

	@Test
	public void boardFromStringConstructor06() {
		String in = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n"; 
		Board b = new Board(in);
		String out = b.toString();
		assertEquals(in, out);
	}

	@Test
	public void getDistanceTest00() {
		Position p1 = new Position(1, 2);
		Position p2 = new Position(3, 4);
		LiveCell c1 = new LiveCell(p1);
		assert (c1.getDistance(p1, p2).equals(Math.sqrt(8))) : "expected: " + Math.sqrt(2) + "but was: "
				+ c1.getDistance(p1, p2);
	}

	@Test
	public void getNextGenerationTest00() {
		Position p1 = new Position(1, 2);
		LiveCell c1 = new LiveCell(p1);
		assert (c1.getNextGeneration(-1) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(-1);
	}

	@Test
	public void getNextGenerationTest01() {
		Position p1 = new Position(1, 2);
		LiveCell c1 = new LiveCell(p1);
		assert (c1.getNextGeneration(1) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(1);
	}

	@Test
	public void getNextGenerationTest02() {
		Position p1 = new Position(1, 2);
		LiveCell c1 = new LiveCell(p1);
		assert (c1.getNextGeneration(2) instanceof LiveCell) : "expected LiveCell but was " + c1.getNextGeneration(2);
	}

	@Test
	public void getNextGenerationTest03() {
		Position p1 = new Position(1, 2);
		LiveCell c1 = new LiveCell(p1);
		assert (c1.getNextGeneration(4) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(4);
	}
	
	@Test
	public void getNextGenerationTest04() {
		Position p1 = new Position(1, 2);
		DeadCell c1 = new DeadCell(p1);
		assert (c1.getNextGeneration(4) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(4);
	}
	
	@Test
	public void getNextGenerationTest05() {
		Position p1 = new Position(1, 2);
		DeadCell c1 = new DeadCell(p1);
		assert (c1.getNextGeneration(3) instanceof LiveCell) : "expected LiveCell but was " + c1.getNextGeneration(3);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void scanner00() {
		String input = "a|b|c|d|e| |*|";
		String expected = "abcde *";
		Scanner s = new Scanner(input);
		// s.useDelimiter("\\|");
		s.useDelimiter("\\" + Board.BOARD_DELIM);
		StringBuilder output = new StringBuilder("");
		while (s.hasNext())
			output.append(s.next());
		assert ((output + "").equals(expected)) : "expected \"" + expected + "\" but was \"" + output + "\"";
	}

	@Test
	public void testCtor1() {
		// test constructor that gets a valid image
		String image = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		// same image - this board will not change over time
		String expected = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" + 
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n"; 
		Board b = new Board(image);
		b.moveTime();
		String result = b.toString();
		assertEquals(expected, result);
	}

	@Test
	public void testCtor2() {
		// test constructor that gets a valid set of live cells
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(1, 1)));
		Board b = new Board(liveCells);
		String result = b.toString();
		String expected = 
				LIVE + DELIM + DEAD + DELIM + "\n" + 
				LIVE + DELIM + LIVE + DELIM + "\n";
		assertEquals(expected, result);
	}

	@Test
	public void testCtor3() {
		BoardReader reader = new BoardReaderImp();
		Board b = new Board(reader);
		String expected = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" + 
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n"; 
		b.moveTime();
		String result = b.toString();
		assertEquals(expected, result);
	}

	@Test
	public void testCtor4() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(1, 1)));
		Board b = new Board(liveCells);
		String result = b.toString();
		String expected = 
				LIVE + DELIM + DEAD + DELIM + "\n" + 
				LIVE + DELIM + LIVE + DELIM + "\n";
		assertEquals(expected, result);
	}

	@Test
	public void testIterator00() {
		// test valid iteration
		String image = 
				LIVE + DELIM + DEAD + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + "\n";
		Board b = new Board(image);
		Iterator<Cell> i = b.iterator();
		// TODO: Implement Cell equals().
		assertEquals(new LiveCell(new Position(0, 0)), i.next());
		assertEquals(new DeadCell(new Position(1, 0)), i.next());
		assertEquals(new DeadCell(new Position(0, 1)), i.next());
		assertEquals(new LiveCell(new Position(1, 1)), i.next());
		assertFalse(i.hasNext());
	}

	@Test
	public void testIterator01() {
		// test valid iteration
		String image = 
				LIVE + DELIM + DEAD + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + "\n";
		Board b = new Board(image);
		Iterator<Cell> i = b.iterator();
		assertEquals(new Position(0, 0), i.next().getPosition());
		assertEquals(new Position(1, 0), i.next().getPosition());
		assertEquals(new Position(0, 1), i.next().getPosition());
		assertEquals(new Position(1, 1), i.next().getPosition());
		assertFalse(i.hasNext());
	}

	@Test
	public void testIterator02() {
		String image = 
				LIVE + DELIM + DEAD + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + "\n";
		Board b = new Board(image);
		Iterator<Cell> i = b.iterator();
		assertEquals(0, i.next().xPosition());
		assertEquals(1, i.next().xPosition());
		assertEquals(0, i.next().xPosition());
		assertEquals(1, i.next().xPosition());
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testIterator03() {
		String image = 
				LIVE + DELIM + DEAD + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + "\n";
		Board b = new Board(image);
		Iterator<Cell> i = b.iterator();
		assertEquals(0, i.next().yPosition());
		assertEquals(0, i.next().yPosition());
		assertEquals(1, i.next().yPosition());
		assertEquals(1, i.next().yPosition());
		assertFalse(i.hasNext());
	}

	@Test
	public void testMoveTime00() {
		// test constructor that gets a valid set of live cells
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(2, 0)));
		Board b = new Board(liveCells);
		String expected0 = 
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n";
		assertEquals(expected0, b.toString());
		b.moveTime();
		String result = b.toString();
		// the dead cell will come back to life, and afterwards no changes
		String expected = 
				DEAD + DELIM + DEAD + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + LIVE + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + DEAD + DELIM + DEAD + DELIM + "\n";
		assertEquals(expected, result);
	}
	
	@Test
	public void testMoveTime01() {
		// test constructor that gets a valid set of live cells
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(0, 1)));
		Board b = new Board(liveCells);
		String expected0 = 
				LIVE + DELIM + LIVE + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + "\n";
		assertEquals(expected0, b.toString());
		for(int i = 0; i < 10; ++i)
			b.moveTime();
		String result = b.toString();
		// the dead cell will come back to life, and afterwards no changes
		String expected = 
				LIVE + DELIM + LIVE + DELIM + "\n" +
				LIVE + DELIM + LIVE + DELIM + "\n";
		assertEquals(expected, result);
	}
	
	@Test
	public void testMoveTime02() {
		// test constructor that gets a valid set of live cells
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		Board b = new Board(liveCells);
		String expected0 = 
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n";
		assertEquals(expected0, b.toString());
		for(int i = 0; i < 10; ++i)
			b.moveTime();
		String result = b.toString();
		// the dead cell will come back to life, and afterwards no changes
		String expected = 
				DEAD + DELIM + "\n" +
				DEAD + DELIM + "\n";
		assertEquals(expected, result);
	}
	
	@Test
	public void testMoveTime03() {
		// test constructor that gets a valid set of live cells
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(2, 0)));
		liveCells.add(new LiveCell(new Position(3, 0)));
		Board b = new Board(liveCells);
		String expected0 = 
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n" +
				LIVE + DELIM + "\n";
		assertEquals(expected0, b.toString());
		b.moveTime();
		String result = b.toString();
		// the dead cell will come back to life, and afterwards no changes
		String expected1 =
				DEAD + DELIM + DEAD + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + LIVE + DELIM + LIVE + DELIM + "\n" +
				LIVE + DELIM + LIVE + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + DEAD + DELIM + DEAD + DELIM + "\n";
		assertEquals(expected1, result);
		String expected2 = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		for(int i = 0; i < 10; ++i){
			b.moveTime();
			result = b.toString();
			assertEquals(expected2, result);
		}		
	}

	@Test
	public void testRevive() {
		// test revival of a dead cell
		String image = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		String expected = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" + 
				LIVE + DELIM + LIVE + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		Board b = new Board(image);
		b.revive(new Position(1, 1));
		assertEquals(expected, b.toString());
	}

	@Test
	public void testStrike() {
		// test strike of a live cell
		String image = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		String expected = 
				DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" + 
				LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
				DEAD + DELIM + DEAD + DELIM + DEAD + DELIM + "\n";
		Board b = new Board(image);
		b.strike(new Position(2, 1));
		assertEquals(expected, b.toString());
	}

	@Test
	public void toString00() {
		String input = " |*|\n*| |";
		try {
			String output = new Board(input) + "";
			assert ((output + "").equals(input + "\n")) : "\nexpected \n" + input + "\n" + "\nbut was \n" + output;
		} catch (IllegalArgumentException e) {
			assert false : "shouldn't thow an exception";
		}
	}
	
	@Test public void testNeighborTally00() {
		Set<Cell> liveCells = new HashSet<>();
		liveCells.add(new LiveCell(new Position(0, 0)));
		liveCells.add(new LiveCell(new Position(1, 0)));
		liveCells.add(new LiveCell(new Position(2, 0)));
		Board b = new Board(liveCells);
		Map<Position,Integer> checked = b.neighborTally();
		assert checked.get(new Position(-1, -1)).equals(1);
		assert checked.get(new Position(-1, 0)).equals(1);
		assert checked.get(new Position(-1, 1)).equals(1);
		assert checked.get(new Position(0, -1)).equals(2);
		assert checked.get(new Position(0,0)).equals(1);
		assert checked.get(new Position(0,1)).equals(2);
		assert checked.get(new Position(1,-1)).equals(3);
		assert checked.get(new Position(1,0)).equals(2);
		assert checked.get(new Position(1,1)).equals(3);
		assert checked.get(new Position(2,-1)).equals(2);
		assert checked.get(new Position(2,0)).equals(1);
		assert checked.get(new Position(2,1)).equals(2);
		assert checked.get(new Position(3,-1)).equals(1);
		assert checked.get(new Position(3,0)).equals(1);
		assert checked.get(new Position(3,1)).equals(1);
	}
}
