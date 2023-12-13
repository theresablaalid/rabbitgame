package inf101.gfx.gfxmode;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public interface IShape {

	/**
	 * Add another point to the line path
	 *
	 * @param xy
	 * @return
	 */
	IShape addPoint(double x, double y);

	/**
	 * Add another point to the line path
	 *
	 * @param xy
	 * @return
	 */
	IShape addPoint(Point xy);

	/**
	 * Set the arc angle for the subsequent draw commands
	 *
	 * <p>
	 * For use with {@link #arc()}
	 *
	 * @param a The angle, in degrees
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape angle(double a);

	/**
	 * Draw an arc with the current drawing parameters
	 *
	 * <p>
	 * Relevant parameters:
	 * <li>{@link #at(Point)}, {@link #x(double)}, {@link #gravity(double)}
	 * <li>{@link #length(double)}
	 * <li>{@link #angle(double)}
	 * <li>{@link #gravity(Gravity)}
	 * <li>{@link #stroke(Paint)}, {@link #fill(Paint)}
	 * <li>{@link #rotation(double)}
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape arc();

	/**
	 * Set the (x,y)-coordinates of the next draw command
	 *
	 * @param p Coordinates
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape at(Point p);

	/**
	 * Close the line path, turning it into a polygon.
	 *
	 * @return
	 */
	IShape close();

	void draw();

	void draw(GraphicsContext context);

	/**
	 * Draw an ellipse with the current drawing parameters
	 *
	 * <p>
	 * Relevant parameters:
	 * <li>{@link #at(Point)}, {@link #x(double)}, {@link #gravity(double)}
	 * <li>{@link #width(double)}, {@link #height(double)}
	 * <li>{@link #gravity(Gravity)}
	 * <li>{@link #stroke(Paint)}, {@link #fill(Paint)}
	 * <li>{@link #rotation(double)}
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape ellipse();

	/**
	 * Fill the current shape
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape fill();

	/**
	 * Set fill colour for the subsequent draw commands
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape fillPaint(Paint p);

	/**
	 * Set gravity for the subsequent draw commands
	 *
	 * Gravity determines the point on the shape that will be used for positioning
	 * and rotation.
	 *
	 * @param g The gravity
	 * @return
	 */
	IShape gravity(Gravity g);

	/**
	 * Set the height of the next draw command
	 *
	 * @param h The height
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape height(double h);

	/**
	 * Set the length of the following draw commands
	 *
	 * <p>
	 * For use with {@link #line()} and {@link #arc()}
	 *
	 * @param l The length
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape length(double l);

	/**
	 * Draw a line with the current drawing parameters
	 *
	 * <p>
	 * Relevant parameters:
	 * <li>{@link #at(Point)}, {@link #x(double)}, {@link #gravity(double)}
	 * <li>{@link #length(double)}
	 * <li>{@link #angle(double)}
	 * <li>{@link #gravity(Gravity)} (flattened to the horizontal axis, so, e.g.,
	 * {@link Gravity#NORTH} = {@link Gravity#SOUTH} = {@link Gravity#CENTER})
	 * <li>{@link #stroke(Paint)}
	 * <li>{@link #rotation(double)}
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape line();

	/**
	 * Draw a rectangle with the current drawing parameters
	 *
	 * <p>
	 * Relevant parameters:
	 * <li>{@link #at(Point)}, {@link #x(double)}, {@link #gravity(double)}
	 * <li>{@link #width(double)}, {@link #height(double)}
	 * <li>{@link #gravity(Gravity)}
	 * <li>{@link #stroke(Paint)}, {@link #fill(Paint)}
	 * <li>{@link #rotation(double)}
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape rectangle();

	/**
	 * Sets rotation for subsequent draw commands.
	 *
	 * <p>
	 * Shapes will be rotate around the {@link #gravity(Gravity)} point.
	 *
	 * @param angle Rotation in degrees
	 * @return
	 */
	IShape rotation(double angle);

	/**
	 * Stroke the current shape
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape stroke();

	/**
	 * Set stroke colour for the subsequent draw commands
	 *
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape strokePaint(Paint p);

	Shape toFXShape();

	String toSvg();

	/**
	 * Set the width of the next draw command
	 *
	 * @param w The width
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape width(double w);

	/**
	 * Set the x-coordinate of the next draw command
	 *
	 * @param x Coordinate
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape x(double x);

	/**
	 * Set the y-coordinate of the next draw command
	 *
	 * @param y Coordinate
	 * @return <code>this</code>, for adding more drawing parameters or issuing the
	 *         draw command
	 */
	IShape y(double y);

}
