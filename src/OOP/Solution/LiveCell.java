package OOP.Solution;

/**
 * The concrete representation of a live cell.
 */
public class LiveCell extends TopCell implements Cell {
		
	// The only constructor you need - there's no cell without a position.
	public LiveCell(Position p) {
		setPosition(p);
	}

	@Override
	public Cell getNextGeneration(int liveNeighbors) {
		return (liveNeighbors < 2 || liveNeighbors >= 4) ? new DeadCell(getPosition()) : new LiveCell(getPosition());
	}

	@Override
	public String toString() {
		return LIVE_SIMBOL;
	}
}
