package OOP.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import OOP.Solution.*;

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
}
