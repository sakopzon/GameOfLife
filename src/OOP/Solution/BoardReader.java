package OOP.Solution;

import java.io.IOException;
import java.text.ParseException;

/**
 * This interface can be implemented by various board providers.
 */
public interface BoardReader {

	/**
	 * This method reads a board from an external source, and returns
	 * it as an image (in the format accepted by the Board(String) constructor).
	 * @return a board formatted as a string
	 * @throws IOException in case the implementation gets the board from a file,
	 * 			and can't read the file's content.
	 * @throws ParseException in case the implementation gets the board from
	 * 			some format that requires parsing, and its format is invalid.
	 */
	String read() throws IOException, ParseException;
	
}
