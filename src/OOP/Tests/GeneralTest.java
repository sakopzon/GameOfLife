package OOP.Tests;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import OOP.Solution.*;
import OOP.Solution.Board;
import OOP.Solution.Board.Row;

public class GeneralTest {
	
	@Test
	public void getDistanceTest00() {
		Position p1 = new Position(1,2);
		Position p2 = new Position(3,4);
		LiveCell c1 = new LiveCell(p1);
		// TODO: Dan why is the (Double) cast needed?
		assert(c1.getDistance(p1, p2).equals(Math.sqrt(8))) : "expected: " + (Double)Math.sqrt(2) + "but was: " + (Double)c1.getDistance(p1, p2);
	}
	
	@Test
	public void getNextGenerationTest00() {
		Position p1 = new Position(1,2);
		LiveCell c1 = new LiveCell(p1);
		assert(c1.getNextGeneration(-1) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(-1);
	}
	
	@Test
	public void getNextGenerationTest01() {
		Position p1 = new Position(1,2);
		LiveCell c1 = new LiveCell(p1);
		assert(c1.getNextGeneration(1) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(1);
	}
	
	@Test
	public void getNextGenerationTest02() {
		Position p1 = new Position(1,2);
		LiveCell c1 = new LiveCell(p1);
		assert(c1.getNextGeneration(2) instanceof LiveCell) : "expected LiveCell but was " + c1.getNextGeneration(2);
	}
	
	@Test
	public void getNextGenerationTest03() {
		Position p1 = new Position(1,2);
		LiveCell c1 = new LiveCell(p1);
		assert(c1.getNextGeneration(4) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(4);
	}
	
	@Test
	public void getNextGenerationTest04() {
		Position p1 = new Position(1,2);
		DeadCell c1 = new DeadCell(p1);
		assert(c1.getNextGeneration(4) instanceof DeadCell) : "expected DeadCell but was " + c1.getNextGeneration(4);
	}
	
	@Test
	public void getNextGenerationTest05() {
		Position p1 = new Position(1,2);
		DeadCell c1 = new DeadCell(p1);
		assert(c1.getNextGeneration(3) instanceof LiveCell) : "expected LiveCell but was " + c1.getNextGeneration(3);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void scanner00() {
		String input = "a|b|c|d|e| |*|";
		String expected = "abcde *";
		Scanner s = new Scanner(input);
		//s.useDelimiter("\\|");
		s.useDelimiter("\\" + Board.BOARD_DELIM);
		StringBuilder output = new StringBuilder("");
		while(s.hasNext())
			output.append(s.next());
		assert ((output + "").equals(expected)) : "expected \"" + expected + "\" but was \"" + output + "\"";
	}
	
	@Test
	public void boardFromStringConstructor00() {
		String input = "";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getColumns())
				output.append(¢ instanceof LiveCell ? "*" : " ");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}
	
	@Test
	public void boardFromStringConstructor01() {
		String input = "*|";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getColumns())
				output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}
	
	@Test
	public void boardFromStringConstructor02() {
		String input = " |";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getColumns())
				output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}
	
	@Test
	public void boardFromStringConstructor03() {
		String input = "*|";
		StringBuilder output = new StringBuilder("");
		for (Row r : (new Board(input)).getBoard())
			for (Cell ¢ : r.getColumns())
				output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
		assert ((output + "").equals(input)) : "expected \"" + input + "\" but was \"" + output + "\"";
	}
	
	@Test
	public void boardFromStringConstructor04_illegalSimbolException00() {
		String input = "h|";
		StringBuilder output = new StringBuilder("");
		try {	
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getColumns())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void boardFromStringConstructor04_illegalSimbolException01() {
		String input = " | |\n |k|";
		StringBuilder output = new StringBuilder("");
		try {	
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getColumns())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {}
	}

	@Test
	public void boardFromStringConstructor04_notRectangleException00() {
		String input = " |*| | | |*|";
		StringBuilder output = new StringBuilder("");
		try {	
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getColumns())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void boardFromStringConstructor04_notRectangleException01() {
		String input = " |*|\n | | |\n*|*|*|";
		StringBuilder output = new StringBuilder("");
		try {	
			for (Row r : (new Board(input)).getBoard())
				for (Cell ¢ : r.getColumns())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
			assert false : "should throw IllegalArgumentException";
		} catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void boardFromStringConstructor05() {
		String input = " |*|\n*| |";
		StringBuilder output = new StringBuilder("");
		try {	
			for (Row r : (new Board(input)).getBoard()) {
				for (Cell ¢ : r.getColumns())
					output.append((¢ instanceof LiveCell ? "*" : " ") + "|");
				output.append("\n");
			}
			assert ((output + "").equals(input + "\n")) : "\nexpected \n" + input + "\n" + "\nbut was \n" + output;
		} catch (IllegalArgumentException e) {
			assert false : "shouldn't thow an exception";
		}
	}
	
	
}