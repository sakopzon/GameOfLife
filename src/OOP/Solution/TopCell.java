package OOP.Solution;

public abstract class TopCell implements Cell{
	
	protected final Position position = new Position(0, 0);
	
	@Override
	public int hashCode() {
		return 31 + ((position == null) ? 0 : position.hashCode());
	}

	@Override
	public boolean equals(Object ¢) {
		return ¢ == this || this != null && ¢ != null && this.getClass().equals(¢.getClass())
				&& this.getPosition().equals(((TopCell) ¢).getPosition());
	}
	
	// Pay attention, position is final, use it's API to set values
	@Override
	public Position getPosition() {
		return position;
	}
	
	@Override
	public int xPosition() {
		return position.getX();
	}
	
	@Override
	public int yPosition() {
		return position.getY();
	}
	
	@Override
	public void setPosition(int x, int y) {
		position.set(x, y);
	}
	
	@Override
	public void setPosition(Position ¢) {
		if(¢ == null)
			return;
		position.set(¢);
	}
}
