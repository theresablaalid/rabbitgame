package inf101.rogue101.game;

public interface IMessageView {

	/**
	 * Displays a message in the debug area on the screen (bottom line)
	 *
	 * @param s A message
	 */
	void displayDebug(String s);

	/**
	 * Displays a message in the message area on the screen (below the map and the
	 * status line)
	 *
	 * @param s A message
	 */
	void displayMessage(String s);

	/**
	 * Displays a status message in the status area on the screen (right below the
	 * map)
	 *
	 * @param s A message
	 */
	void displayStatus(String s);

	/**
	 * Displays a message in the message area on the screen (below the map and the
	 * status line)
	 *
	 * @param s A message
	 * @see String#format(String, Object...)
	 */
	void formatDebug(String s, Object... args);

	/**
	 * Displays a formatted message in the message area on the screen (below the map
	 * and the status line)
	 *
	 * @param s A message
	 * @see String#format(String, Object...)
	 */
	void formatMessage(String s, Object... args);

	/**
	 * Displays a formatted status message in the status area on the screen (right
	 * below the map)
	 *
	 * @param s A message
	 * @see String#format(String, Object...)
	 */
	void formatStatus(String s, Object... args);

}
