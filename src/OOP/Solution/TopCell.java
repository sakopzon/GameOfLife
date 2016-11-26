package OOP.Solution;

public abstract class TopCell implements Cell{
	
	protected Position position;
	
	@Override
	public Position getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}
	
	@Override
	public void setPosition(Position ¢) {
		position = ¢;
	}
}
