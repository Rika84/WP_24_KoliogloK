
import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a wall in the maze.
 */
public class Wall extends MapSite {

    private Directions direction; // Wall orientation
    private boolean isDestroyed;  // True if the wall is destroyed

    /**
     * Constructs a wall with specified coordinates and direction.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param direction the direction of the wall.
     */
    public Wall(int x, int y, Directions direction) {
        super(x, y);
        this.direction = direction;
        this.isDestroyed = false; // By default, the wall is intact
        System.out.println("Wall created facing " + direction);
    }

    /**
     * Constructs a wall with only a direction (no coordinates).
     *
     * @param direction the direction of the wall.
     */
    public Wall(Directions direction) {
        super(-1, -1); // Default coordinates
        this.direction = direction;
        this.isDestroyed = false;
        System.out.println("Wall created without coordinates facing " + direction);
    }

    /**
     * Returns the wall's direction.
     *
     * @return the direction.
     */
    public Directions getDirection() {
        return direction;
    }

    /**
     * Checks whether the wall is destroyed.
     *
     * @return true if the wall is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Destroys the wall.
     */
    public void destroy() {
        this.isDestroyed = true;
        System.out.println("Wall facing " + direction + " destroyed!");
    }

    /**
     * Draws the wall.
     *
     * @param g the Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        if (isDestroyed) {
            System.out.println("Skipping drawing of destroyed wall facing " + direction);
            return;
        }

        g.setColor(Color.BLACK);
        int x = getX();
        int y = getY();

        // Draw the wall line based on its direction. Assumes MapSite.LENGTH is defined.
        switch (direction) {
            case North:
                g.drawLine(x, y, x + MapSite.LENGTH, y);
                break;
            case South:
                g.drawLine(x, y + MapSite.LENGTH, x + MapSite.LENGTH, y + MapSite.LENGTH);
                break;
            case East:
                g.drawLine(x + MapSite.LENGTH, y, x + MapSite.LENGTH, y + MapSite.LENGTH);
                break;
            case West:
                g.drawLine(x, y, x, y + MapSite.LENGTH);
                break;
        }
    }
}
