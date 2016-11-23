package OOP.Solution;

/**
 * The concrete representation of a dead cell.
 */
public class DeadCell extends TopCell implements Cell {
	
	// The only constructor you need - there's no cell without a position.
	public DeadCell(Position p) {
		this.position = p;
	}

	@Override
	public Cell getNextGeneration(int liveNeighbors) {
		return liveNeighbors == 3 ? new LiveCell(position) : new DeadCell(position);
	}
	
	@Override
	public String toString() {
		return " ";
	}
}
