package OOP.Solution;

/**
 * Represent a position on the board.
 */
public class Position {
	int x;
	int y;
	
	public void set(Position ¢) {
		x = ¢.x;
		y = ¢.y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int ¢) {
		x = ¢;
	}

	public void setY(int ¢) {
		y = ¢;
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		return 31 * (x + 31) + y;
	}

	@Override
	public boolean equals(Object ¢) {
		return ¢ instanceof Position && equals((Position) ¢);
	}
	
	private boolean equals(Position ¢){
		return x == ¢.x && y == ¢.y;
	}

}
