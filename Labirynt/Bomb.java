
import java.awt.Color;
import java.awt.Graphics;

/**
 * Class representing a bomb in a room.
 */
public class Bomb extends MapSite {

    private boolean isActive; // Bomb state: active or exploded

    /**
     * Constructor for creating a bomb.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Bomb(int x, int y) {
        super(x, y);
        this.isActive = true; // Bomb is created as active
    }

    /**
     * Method to detonate the bomb. Changes state and notifies the room.
     *
     * @param room the room where the explosion occurred
     */
    public void explode(Room room) {
        if (isActive) {
            isActive = false;
            room.handleExplosion(this); // Notify the room about the explosion
        }
    }

    /**
     * Checks if the bomb is active.
     *
     * @return true if the bomb is active, otherwise false.
     */
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();

        // Draw the bomb: if active, draw a bomb icon, otherwise an explosion
        if (isActive) {
            g.setColor(Color.RED);
            g.fillOval(x + 15, y + 15, 20, 20); // Draw round bomb
            g.setColor(Color.BLACK);
            g.drawString("💣", x + 20, y + 30); // Bomb icon
        } else {
            g.setColor(Color.ORANGE);
            g.fillOval(x + 10, y + 10, 30, 30); // Draw explosion
            g.setColor(Color.RED);
            g.drawString("🔥", x + 20, y + 30); // Explosion icon
        }
    }

    public boolean isExploded() {
        return !isActive;
    }
}
