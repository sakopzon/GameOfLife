package OOP.Solution;


/**
 * The abstract representation of a board cell.
 */
public interface Cell {
	
	// Override to return a cell of the correct type, dead or alive,
	// based on its current state and the number of living neighbors
	Cell getNextGeneration(int liveNeighbors);
	

	//Implement this default method to get the desired behaviot for all implementing classes.
	//Should return the distance between p1 and p2 - which is the sqrt((p1.x-p2.x)^2+(p1.y-p2.y)^2).
	default Double getDistance(Position p1, Position p2) {
		return Math.sqrt(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2));
	}
}
