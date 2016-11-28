package OOP.Tests;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

import OOP.Solution.*;
import OOP.Solution.Board;
import OOP.Solution.Board.Row;

public class GeneralTest {

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
	public void toString00() {
		String input = " |*|\n*| |";
		try {
			String output = new Board(input) + "";
			assert ((output + "").equals(input + "\n")) : "\nexpected \n" + input + "\n" + "\nbut was \n" + output;
		} catch (IllegalArgumentException e) {
			assert false : "shouldn't thow an exception";
		}
	}
}
