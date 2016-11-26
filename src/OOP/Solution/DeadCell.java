package OOP.Solution;

/**
 * The concrete representation of a dead cell.
 */
public class DeadCell extends TopCell implements Cell {
	
	// The only constructor you need - there's no cell without a position.
	public DeadCell(Position p) {
		setPosition(p);
	}

	@Override
	public Cell getNextGeneration(int liveNeighbors) {
		return liveNeighbors == 3 ? new LiveCell(getPosition()) : new DeadCell(getPosition());
	}
	
	@Override
	public String toString() {
		return DEAD_SIMBOL;
	}
}
