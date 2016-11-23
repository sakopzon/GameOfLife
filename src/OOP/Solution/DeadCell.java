package OOP.Solution;

/**
 * The concrete representation of a dead cell.
 */
public class DeadCell implements Cell {
	
	// The only constructor you need - there's no cell without a position.
	public DeadCell(Position p) {
	}

	@Override
	public Cell getNextGeneration(int liveNeighbors) {
		// TODO Auto-generated method stub
		return null;
	}
}
