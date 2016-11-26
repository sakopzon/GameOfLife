package OOP.Solution;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * The container of the game.
 * The class implements Iterable\<Cell\> so the board could be iterated.
 */
public class Board implements Iterable<Cell> {
	
	public static final String BOARD_DELIM = "|";
	public static final String END_OF_LINE = "\n";

	private List<Row> rows = new ArrayList<>();
	private int rowsNum;
	
	public List<Row> getBoard() {
		return rows;
	}
	/**
	 * Create a new board based on the given image. The image is a string.
	 * Each row (including the last) ends with a new line.
	 * Each cell (including the last) is followed by BOARD_DELIM.
	 * Each cell is represented by the string returned by its toString() method.
	 * The first cell on the first row gets the location (0, 0). The x cell in the y row gets (x-1, y-1).
	 * @param image
	 * @throws IllegalArgumentException on the following cases:
	 * 			1. A wrong symbol is used (neither dead nor alive).
	 * 			2. The image is not a rectangle. Namely, not all rows have the same length.
	 */
	public Board(String image) throws IllegalArgumentException {
		initBoardFromString(image);
	}
	/**
	 * @param image
	 */
	private void initBoardFromString(String image) {
		rowsNum = 0;
		for (Scanner ¢ = new Scanner(image).useDelimiter("\\" + END_OF_LINE); ¢.hasNext();)
			rows.add(new Row(¢.next(), rowsNum++));
		// Check rectangle:
		for(Row ¢ : rows)
			if(rowsNum != ¢.columnsNum)
				throw new IllegalArgumentException();
	}
	
	/**
	 * Create a new board using the given board reader. If the reader throws one of the
	 * declared exceptions, the constructor should use a default: 2*2 board with all live cells.
	 * If the read succeeds, the constructor should initialize the board using the received image.
	 * @param reader the board supplier
	 */
	public Board(BoardReader reader) {
		String image;
		try{
			image = reader.read();
			initBoardFromString(image);
		} catch(IOException e){
			initDefaultBoard();
		} catch (ParseException e) {
			initDefaultBoard();
		}
	}
	private void initDefaultBoard() {
		rowsNum = 2;
		rows.add(new Row(0,2));
		rows.add(new Row(1,2));
		revive(new Position(0,0));
		revive(new Position(0,1));
		revive(new Position(1,0));
		revive(new Position(1,1));
	}
	/** 
	 * Create a new board using a given set of cells. The list is expected to contain only live cells. This should be validated. If validations are turned off, dead cells should be ignored.
	 * @param cells
	 * @throws ValidationException in case a dead cell is given.
	 * [[SuppressWarningsSpartan]]
	 */
	public Board(Set<Cell> cells) throws ValidationException{
		int maxPos_X = cells.stream().max((a, b) -> a.getPosition().x - b.getPosition().x).get().getPosition().x;
		int maxPos_Y = cells.stream().max((a, b) -> a.getPosition().y - b.getPosition().y).get().getPosition().y;
		// Build DeadCells board
		IntStream.range(0, maxPos_Y).forEach(i -> rows.add(new Row(i, maxPos_X)));
		for(Cell ¢ : cells) {
			//TODO: Alex, I'm not at all certain the Set can't contain dead cells.
			//It says that all the cells that are not in the Set should be considered dead, not that all those that are must be alive.
			if(¢ instanceof DeadCell)
				throw new ValidationException();
			int x = ¢.getPosition().x;
			int y = ¢.getPosition().y;
			rows.get(y).getColumns().get(x).setPosition(x, y);
		}
		
	}
	
	/**
	 * Bring a cell at the given position back to life.
	 * The cell at the given location is expected to be dead, 
	 * and that should be validated.
	 * @param ¢ The position of the cell to revive.
	 * @throws IllegalArgumentException in case the position is out of the board's bounds.
	 * 		   ValidationException in case the cell is already alive and validations are on.
	 */
	public void revive(Position ¢) throws IllegalArgumentException, ValidationException {
		if(¢ == null || ¢.y >= rowsNum || ¢.x >= rows.get(¢.y).getColumnsNum())
			throw new IllegalArgumentException();
		if(rows.get(¢.y).getColumns().get(¢.x) instanceof LiveCell)
			throw new ValidationException();
		rows.get(¢.y).getColumns().remove(¢.x);
		rows.get(¢.y).getColumns().add(¢.x, new LiveCell(¢));
	}
	
	/**
	 * Kill the cell at the given position.
	 * The cell at the given location is expected to be alive, 
	 * and that should be validated.
	 * @param p The position of the cell to kill.
	 * @throws IllegalArgumentException in case the position is out of the board's bounds.
	 * 		   ValidationException in case the cell is already dead and validations are on.
	 */
	public void strike(Position ¢) {
		if(¢ == null || ¢.y >= rowsNum || ¢.x >= rows.get(¢.y).getColumnsNum())
			throw new IllegalArgumentException();
		if(rows.get(¢.y).getColumns().get(¢.x) instanceof DeadCell)
			throw new ValidationException();
		rows.get(¢.y).getColumns().remove(¢.x);
		rows.get(¢.y).getColumns().add(¢.x, new DeadCell(¢));
	}
	
	/** 
	 * Create the next generation of the board.
	 * [[SuppressWarningsSpartan]]
	 */
	public void moveTime() {
		Map<Position,Integer> livingNeighbors = neighborTally();
		for(Row row : rows)
			for(Cell cell : row.getColumns())
				cell.getNextGeneration(livingNeighbors.get(cell.getPosition()));
	}

	private Map<Position,Integer> neighborTally() {
		Map<Position,Integer> $ = new HashMap<>();
		if(rowsNum == 0 || rows.get(0).getColumnsNum() == 0)
			return $;
		for(Integer i = Integer.valueOf(0); i < rowsNum; ++i)
			for(Integer j = Integer.valueOf(0); j < rows.get(0).getColumnsNum(); ++j)
				$.put(new Position(i, j), neighborTallyAtPosition(i, j));
		return $;
	}
	private Integer neighborTallyAtPosition(Integer i, Integer j) {
		//Determine which neighboring positions are legitimate
		Integer minimumRow = i.equals(0) ? i : i - 1;
		Integer maximumRow = i.equals(rowsNum - 1) ? i : i + 1;
		Integer minimumColumnn = j.equals(0) ? j : j - 1;
		Integer maximumColumn = j.equals(rows.get(0).getColumnsNum() - 1) ? j : j + 1;
		Integer $ = Integer.valueOf(0);
		//Go over all legitimate neighbors, and count the living ones.
		for(int x = minimumRow; x <= maximumRow; ++x)
			for(int y = minimumColumnn; y <= maximumColumn; ++y)
				$ += rows.get(x).getColumns().get(y) instanceof LiveCell ? 1 : 0;
		return $;
	}
	/**
	 * Return an iterator that will traverse the board for (left, bottom) to (top, right).
	 * The iterator should only support hasNext() and next() operations (make remove() an empty method).
	 * The iterator should only be valid for the generation it was created in - if the board has been changed
	 * by a call to moveTime(), subsequent calls to the iterator's hasNext() should return false, 
	 * and subsequent calls to the iterator's next() should throw a ConcurrentModificationException.
	 * Calls to revive() and strike() should not prevent further use of a previously created iterator.
	 */
	@Override
	public Iterator<Cell> iterator() {
		return null;
	}

	/**
	 * Return the representation of the board as a string. The sting should be formatted as follows:
	 * 1. Each row should end with a '\n'.
	 * 2. Each cell should have the appropriate symbol according to its type.
	 * 3. Cells should be separated from each other by the board delimiter.
	 * For example - "X| |X|\n |X| |\n" for a 3*2 board with a "v" of live cells. 
	 */
	@Override
	public String toString() {
		return null;
	}
	
	public class Row {
		List<Cell> columns = new ArrayList<>();
		private int columnsNum, rowNum;
		
		public int getColumnsNum() {
			return columnsNum;
		}
		
		public List<Cell> getColumns() {
			return columns;
		}
		
		private void addCell(String s, Position p) throws IllegalArgumentException {
			if(!Cell.LIVE_SIMBOL.equals(s) && !Cell.DEAD_SIMBOL.equals(s))
				throw new IllegalArgumentException();
			columns.add(Cell.LIVE_SIMBOL.equals(s) ? new LiveCell(p) :  new DeadCell(p));
		}
		
		Row(String s, int thisRowNum) throws IllegalArgumentException {
			rowNum = thisRowNum; 
			for (Scanner ¢ = new Scanner(s).useDelimiter("\\" + Board.BOARD_DELIM); ¢.hasNext();)
				addCell(¢.next(), new Position(thisRowNum, columnsNum++));
		}
		
		// Initializes a Row of DeadCells.
		Row(int thisRowNum, int numOfColumns) throws IllegalArgumentException {
			rowNum = thisRowNum;
			IntStream.range(0, numOfColumns).forEach(c -> columns.add(new DeadCell(new Position(rowNum, c))));
		}
	}
}
