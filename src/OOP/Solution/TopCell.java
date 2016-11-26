package OOP.Solution;

public class TopCell {
	private Position position;
	
	protected Position getPosition() {
		return position;
	}
	
	protected void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}
	
	protected void setPosition(Position ¢) {
		position = ¢;
	}
}
