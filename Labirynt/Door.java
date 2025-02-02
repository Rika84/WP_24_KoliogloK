
import java.awt.Color;
import java.awt.Graphics;

/**
 * Class representing a door between two rooms.
 */
public class Door extends MapSite {

    private Room roomOne; // First room
    private Room roomTwo; // Second room

    /**
     * Constructor for creating a door between two rooms.
     *
     * @param r1 first room
     * @param r2 second room
     */
    public Door(Room r1, Room r2) {
        super(-1, -1); // Doors do not have fixed coordinates on the screen
        this.roomOne = r1;
        this.roomTwo = r2;
    }

    /**
     * Returns the first room.
     *
     * @return first room
     */
    public Room getRoomOne() {
        return roomOne;
    }

    /**
     * Returns the second room.
     *
     * @return second room
     */
    public Room getRoomTwo() {
        return roomTwo;
    }

    @Override
    public void draw(Graphics g) {
        int x1 = roomOne.getX();
        int y1 = roomOne.getY();
        int x2 = roomTwo.getX();
        int y2 = roomTwo.getY();

        g.setColor(Color.BLACK); // Black color for walls

        if (x1 == x2) { // Rooms are vertically aligned
            int y = Math.max(y1, y2);
            g.clearRect(x1 + 20, y - 1, 10, 2); // Create an "opening" in the wall
        } else { // Rooms are horizontally aligned
            int x = Math.max(x1, x2);
            g.clearRect(x - 1, y1 + 20, 2, 10); // Create an "opening" in the wall
        }
    }

    /**
     * Reacts to an explosion, keeping the door intact.
     *
     * @param explodedRoom the room where the explosion occurred
     */
    public void handleExplosion(Room explodedRoom) {
        // The door remains intact, but additional effects can be added if needed
        System.out.println("The door between rooms " + roomOne.getRoomNumber() + " and " + roomTwo.getRoomNumber() + " remains intact.");
    }
}
