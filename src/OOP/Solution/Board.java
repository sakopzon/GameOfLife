package OOP.Solution;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
	private static int rowsNum;
	private static int minColumnIndex;
	private static int maxColumnIndex;
	private static int minRowIndex;
	private int maxRowIndex;
	private int generationNum;
	
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
	private void initBoardFromString(String image) throws IllegalArgumentException{
		rowsNum = minRowIndex = minColumnIndex = 0;
		for (Scanner ¢ = new Scanner(image).useDelimiter("\\" + END_OF_LINE); ¢.hasNext();)
			rows.add(new Row(¢.next(), rowsNum++));
		// Check rectangle:
		for(Row ¢ : rows)
			if(rowsNum != ¢.getRowList().size())
				throw new IllegalArgumentException();
		maxColumnIndex = maxRowIndex = rowsNum - 1;
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
		minColumnIndex = minRowIndex = 0;
		maxColumnIndex = maxRowIndex = 1;
		rowsNum = 2;
		rows.add(new Row(0,0,1));
		rows.add(new Row(1,0,1));
		revive(new Position(0,0));
		revive(new Position(0,1));
		revive(new Position(1,0));
		revive(new Position(1,1));
	}
	
	private void initBoardBySize() {
		IntStream.rangeClosed(minRowIndex, maxRowIndex).forEach(i -> rows.add(new Row(i,minColumnIndex,maxColumnIndex)));
	}
	
//	private void initBoardBySize() {
//		IntStream.rangeClosed(minRowIndex, maxRowIndex).forEach(i -> rows.add(new Row(i,minColumnIndex,maxColumnIndex)));
//	}
	
	private void validateInput(Set<Cell> cs) throws ValidationException{
		for(Cell ¢ : cs)
			if(¢ instanceof DeadCell)
				throw new ValidationException();
	}
	
	private void initBoardSize(Set<Cell> cs) {
		minRowIndex = cs.stream().min((a, b) -> a.xPosition() - b.xPosition()).get().xPosition();
		minColumnIndex = cs.stream().min((a, b) -> a.yPosition() - b.yPosition()).get().yPosition();
		maxRowIndex = cs.stream().max((a, b) -> a.xPosition() - b.xPosition()).get().xPosition();
		maxColumnIndex = cs.stream().max((a, b) -> a.yPosition() - b.yPosition()).get().yPosition();
		rowsNum = maxColumnIndex - minColumnIndex + 1;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return {@link Wrapper<Cell>} which includes the cell on the (x, y) position on board.
	 * The ({@link Wrapper<Cell>} needed for being able to use this method as an L-value!
	 * TODO: Not tested yet.
	 */
	@SuppressWarnings("unused")
	private Wrapper<Cell> board(Position ¢) {
		return new Wrapper<Cell>(rows.get(¢.y).rowList.get(¢.x));
	}

	private void putCellsOnBoard(Set<Cell> cs) {
		for(Cell ¢ : cs)
			replaceCell(¢);
	}
	
	/** 
	 * Create a new board using a given set of cells. The list is expected to contain only live cells.
	 * This should be validated. 
	 * TODO: understand: If validations are turned off, dead cells should be ignored.
	 * @param cells
	 * @throws ValidationException in case a dead cell is given.
	 * [[SuppressWarningsSpartan]]
	 */
	public Board(Set<Cell> cs) throws ValidationException{
		validateInput(cs);
		initBoardSize(cs);
		initBoardBySize();
		putCellsOnBoard(cs);
	}
	
	
	private List<Cell> getRowListOf(Cell ¢) {
		return rows.get(fixed.rowIndex(¢)).getRowList();
	}
	
	// TODO: Maybe should be renamed to putCell()?
	private void replaceCell(Cell ¢) {
		getRowListOf(¢).set(fixed.rowListIndex(¢), ¢);
	}
	
	/** 
	 * Bring a cell at the given position back to life. The cell at the given location is expected to be dead,
	 * and that should be validated.
	 * @param ¢ The position of the cell to revive.
	 * @throws IllegalArgumentException in case the position is out of the board's bounds.
	 * ValidationException in case the cell is already alive and validations are on.
	 * [[SuppressWarningsSpartan]]
	 */
	public void revive(Position ¢) throws IllegalArgumentException, ValidationException {
		if(¢ == null || ¢.y > maxColumnIndex || ¢.y < minColumnIndex || ¢.x > maxRowIndex || ¢.x < minRowIndex)
			throw new IllegalArgumentException();
		for(Row row : rows)
			row.getRowList().replaceAll(
					(cell)->{
						if(cell.getPosition().equals(¢) && cell instanceof LiveCell) 
							throw new ValidationException(); 
						return cell.getPosition().equals(¢) ? new LiveCell(¢) : cell;
						});
	}
	
	/**
	 * Kill the cell at the given position.
	 * The cell at the given location is expected to be alive, 
	 * and that should be validated.
	 * @param p The position of the cell to kill.
	 * @throws IllegalArgumentException in case the position is out of the board's bounds.
	 * 		   ValidationException in case the cell is already dead and validations are on.
	 * [[SuppressWarningsSpartan]]
	 */
	public void strike(Position ¢) {
		if(¢ == null || ¢.y > maxColumnIndex || ¢.y < minColumnIndex || ¢.x > maxRowIndex || ¢.x < minRowIndex)
			throw new IllegalArgumentException();
		for(Row row : rows)
			row.getRowList().replaceAll(
					(cell)->{
						if(cell.getPosition().equals(¢) && cell instanceof DeadCell) 
							throw new ValidationException(); 
						return cell.getPosition().equals(¢) ? new DeadCell(¢) : cell;
						});
		
	}
	
	/** 
	 * Create the next generation of the board.
	 * [[SuppressWarningsSpartan]]
	 */
	public void moveTime() {
		Map<Position,Integer> livingNeighbors = neighborTally();
		rowAddChecker(livingNeighbors, minRowIndex - 1);
		rowAddChecker(livingNeighbors, maxRowIndex + 1);
		columnAddChecker(livingNeighbors, minColumnIndex - 1);
		columnAddChecker(livingNeighbors, maxColumnIndex + 1);
		for(Row row : rows)
			for(Cell cell : row.getRowList())
				replaceCell(cell.getNextGeneration(livingNeighbors.get(cell.getPosition())));
		++generationNum;
	}

	private void columnAddChecker(Map<Position, Integer> livingNeighbors, int columnIndex) {
		for(Integer ¢ = minRowIndex; ¢ <= maxRowIndex + 1; ++¢){
			Integer neighborNum = livingNeighbors.get(new Position(¢, columnIndex));
			if (neighborNum != null && neighborNum.equals(3)) {
				columnAdd(columnIndex);
				break;
			}
		}		
	}
	
	/**
	 * Adds a new column. Assumes the new column is either greater than maxColumnIndex or lesser than minColumnIndex;
	 * @param columnIndex
	 */
	private void columnAdd(int columnIndex) {
		minColumnIndex = columnIndex < minColumnIndex ? columnIndex : minColumnIndex;
		maxColumnIndex = columnIndex > maxColumnIndex ? columnIndex : maxColumnIndex;
		for(Row ¢ : rows)
			¢.addDeadCell(columnIndex);
	}
	
	private void rowAddChecker(Map<Position, Integer> livingNeighbors, int rowIndex) {
		for(Integer ¢ = minColumnIndex; ¢ <= maxColumnIndex + 1; ++¢){
			Integer neighborNum = livingNeighbors.get(new Position(rowIndex, ¢));
			if (neighborNum != null && neighborNum.equals(3)) {
				rowAdd(rowIndex);
				break;
			}
		}
	}
	
	/**
	 * Adds a new row. Assumes the new row is either greater than maxRowIndex or lesser than minRowIndex
	 * @param rowIndex
	 */
	private void rowAdd(int rowIndex) {
		minRowIndex = rowIndex < minRowIndex ? rowIndex : minRowIndex;
		maxRowIndex = rowIndex > maxRowIndex ? rowIndex : maxRowIndex;
		rows.add(fixed.rowIndex(rowIndex), new Row(rowIndex));
	}
	
	public Map<Position,Integer> neighborTally() {
		Map<Position,Integer> $ = new HashMap<>();
		if(rowsNum == 0 || rows.get(0).getRowList().isEmpty())
			return $;
		for(Integer i = Integer.valueOf(minRowIndex - 1); i <= Integer.valueOf(maxRowIndex + 1); ++i)
			for(Integer j = Integer.valueOf(minColumnIndex - 1); j <= Integer.valueOf(maxColumnIndex + 1); ++j)
				$.put(new Position(i, j), neighborTallyAtPosition(i, j));
		return $;
	}
	
	private Integer neighborTallyAtPosition(Integer i, Integer j) {
		//Determine which neighboring positions are legitimate
		Integer minimumRow = i > Integer.valueOf(minRowIndex) ? i - 1 : Integer.valueOf(minRowIndex);
		Integer maximumRow = i < Integer.valueOf(maxRowIndex) ? i + 1 : Integer.valueOf(maxRowIndex);
		Integer minimumColumn = j > Integer.valueOf(minColumnIndex) ? j - 1 : Integer.valueOf(minColumnIndex);
		Integer maximumColumn = j < Integer.valueOf(maxColumnIndex) ? j + 1 : Integer.valueOf(maxColumnIndex);
		Integer $ = Integer.valueOf(0);
		//Go over all legitimate neighbors, and count the living ones.
		for(Row row : rows){
			if(row.getRowNum() < minimumRow || row.getRowNum() > maximumRow)
				continue;
			for(Cell ¢ : row.getRowList()){
				if(¢.getPosition().getY() < minimumColumn || ¢.getPosition().getY() > maximumColumn || (¢.getPosition().getX() == i.intValue() && ¢.getPosition().getY() == j.intValue()))
					continue;
				$ += ¢ instanceof LiveCell ? 1 : 0;
			}
		}
		return $;
	}
	
	private Cell getCell(Position ¢) {
		return rows.get(fixed.rowIndex(¢)).rowList.get(fixed.rowListIndex(¢));
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
		return new Iterator<Cell>() {
			
			// TODO: the first Cell is (0, 0)? or this:
			Position curr = new Position(maxRowIndex, minColumnIndex);
			int genNum = generationNum;
			
			boolean sameGeneration() {
				return genNum == generationNum;
			}
			
			@Override
			public boolean hasNext() {
				return sameGeneration() && curr.x >= minRowIndex;
			}

			@Override
			public Cell next() throws ConcurrentModificationException {
				if(!sameGeneration())
					throw new ConcurrentModificationException();
				Position prev = new Position(curr);
				if(prev.y < maxColumnIndex)
					curr.setY(prev.y + 1);
				else {
					curr.setX(prev.x - 1);
					curr.setY(minColumnIndex);
				}
				return getCell(prev);
			}
			
			@Override
			public void remove() {
			}

		};
	}

	/**
	 * Return the representation of the board as a string. The sting should be formatted as follows:
	 * 1. Each row should end with a '\n'. TODO: The HW documentation says that '\n' is a separator!
	 * 2. Each cell should have the appropriate symbol according to its type.
	 * 3. Cells should be separated from each other by the board delimiter.
	 * For example - "X| |X|\n |X| |\n" for a 3*2 board with a "v" of live cells.
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("");
		for (Row r : getBoard()) {
			for (Cell ¢ : r.getRowList())
				out.append((¢ instanceof LiveCell ? Cell.LIVE_SIMBOL : Cell.DEAD_SIMBOL) + Board.BOARD_DELIM);
			out.append(Board.END_OF_LINE);
		}
		return out + "";
	}
	
	public class Row {
		List<Cell> rowList = new ArrayList<>();
		private int rowNum;
		
		public List<Cell> getRowList() {
			return rowList;
		}
		
		public int getRowNum(){
			return rowNum;
		}
		
		private void addDeadCell(int columnIndex) {
			rowList.add(fixed.rowListIndex(columnIndex), new DeadCell(new Position(rowNum, columnIndex)));
		}
		
		private void addCell(String s, Position p) throws IllegalArgumentException {
			if(!Cell.LIVE_SIMBOL.equals(s) && !Cell.DEAD_SIMBOL.equals(s))
				throw new IllegalArgumentException();
			rowList.add(Cell.LIVE_SIMBOL.equals(s) ? new LiveCell(p) :  new DeadCell(p));
		}
		
		Row(String s, int thisRowNum) throws IllegalArgumentException {
			rowNum = thisRowNum; 
			Integer columnsNum = 0;
			for (Scanner ¢ = new Scanner(s).useDelimiter("\\" + Board.BOARD_DELIM); ¢.hasNext();)
				addCell(¢.next(), new Position(thisRowNum, columnsNum++));
		}
		
		/*
		 *  Initializes a Row of DeadCells. Indexes are given.
		 */
		Row(int thisRowNum, int minColumn, int maxColumn) throws IllegalArgumentException {
			rowNum = thisRowNum;
			IntStream.rangeClosed(minColumn, maxColumn).forEach(i -> rowList.add(new DeadCell(new Position(rowNum, i))));
		}
		
		/*
		 *  Initializes a Row of DeadCells. Indexes are the Board private variables.
		 */
		Row(int thisRowNum) throws IllegalArgumentException {
			rowNum = thisRowNum;
			IntStream.rangeClosed(minRowIndex, maxRowIndex).forEach(i -> rowList.add(new DeadCell(new Position(rowNum, i))));
		}
		
	}
	
	private enum fixed {
		;
		/**
		 * @param ¢ the xPosition of the cell
		 * @return Index (0 indexed) of the {@link Cell} in columns list.
		 * Not tested yet.
		 */
		private static int rowListIndex(Cell ¢) {
			return ¢.yPosition() + Math.abs(minColumnIndex);
		}
		
		private static int rowListIndex(Integer ¢) {
			return ¢ + Math.abs(minColumnIndex);
		}

		public static int rowIndex(Cell ¢) {
			return ¢.xPosition() + Math.abs(minRowIndex);
		}
		
		public static int rowIndex(Integer ¢) {
			return ¢ + Math.abs(minRowIndex);
		}

		private static int rowListIndex(Position ¢) {
			return ¢.y + Math.abs(minColumnIndex);
		}

		public static int rowIndex(Position ¢) {
			return ¢.x + Math.abs(minRowIndex);
		}
	}
}
