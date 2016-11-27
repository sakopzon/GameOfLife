package OOP.Tests;



import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import OOP.Solution.*;

/**
 * [[SuppressWarningsSpartan]]
 */
public class BoardTest {
	static final String LIVE = new LiveCell(null).toString();
	static final String DEAD = new DeadCell(null).toString();
	static final String DELIM = Board.BOARD_DELIM;

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
		for (int i = 0; i < 10; i++)
			b.moveTime();
		String result = b.toString();
		// the dead cell will come back to life, and afterwards no changes
		String expected = 
				LIVE + DELIM + LIVE + DELIM + "\n" + 
				LIVE + DELIM + LIVE + DELIM + "\n";
		assertEquals(expected, result);
	}
	
	class BoardReaderImp implements BoardReader {
		@Override
		public String read() throws IOException, ParseException {
			return 
					DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n" +
					LIVE + DELIM + DEAD + DELIM + LIVE + DELIM + "\n" +
					DEAD + DELIM + LIVE + DELIM + DEAD + DELIM + "\n";
		}
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
	public void testIterator() {
		// test valid iteration
		String image = 
				LIVE + DELIM + DEAD + DELIM + "\n" +
				DEAD + DELIM + LIVE + DELIM + "\n";
		Board b = new Board(image);
		Iterator<Cell> i = b.iterator();
		assertEquals(new LiveCell(new Position(0, 0)), i.next());
		assertEquals(new DeadCell(new Position(1, 0)), i.next());
		assertEquals(new DeadCell(new Position(0, 1)), i.next());
		assertEquals(new LiveCell(new Position(1, 1)), i.next());
		assertFalse(i.hasNext());
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
		b.strike(new Position(1, 2));
		assertEquals(expected, b.toString());
	}
}
