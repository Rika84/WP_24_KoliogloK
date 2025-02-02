
import java.awt.Graphics;

/**
 * Abstract class representing a maze element.
 */
public abstract class MapSite {

    public static final int LENGTH = 50; // Common size of the element

    private int x, y; // Coordinates of the element

    /**
     * Constructor to initialize element coordinates.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public MapSite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coordinate.
     *
     * @return X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X coordinate.
     *
     * @param newX new X coordinate.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Returns the Y coordinate.
     *
     * @return Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y coordinate.
     *
     * @param newY new Y coordinate.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * Method to draw the maze element on the screen.
     *
     * @param g Graphics object used for drawing.
     */
    public abstract void draw(Graphics g);
}
