package inf101.gfx;

public interface IPaintLayer {
	/**
	 * Clear the layer.
	 *
	 * <p>
	 * Everything on the layer is removed, leaving only transparency.
	 */
	void clear();

	/**
	 * Send this layer to the back, so it will be drawn behind any other layers.
	 *
	 * <p>
	 * There will still be background behind this layer. You may clear it or draw to
	 * it using {@link Screen#clearBackground()},
	 * {@link Screen#setBackground(javafx.scene.paint.Color)} and
	 * {@link Screen#getBackgroundContext()}.
	 */
	void layerToBack();

	/**
	 * Send this layer to the front, so it will be drawn on top of any other layers.
	 */
	void layerToFront();

	/**
	 * @return Width (in pixels) of graphics layer
	 */
	double getWidth();

	/**
	 * @return Height (in pixels) of graphics layer
	 */
	double getHeight();

}
