
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the maze.
 */
public class Room extends MapSite {

    private static final int ROOM_SIZE = 50;   // Room size (width and height)
    private static final int DOOR_GAP = 10;      // Gap for door opening

    private int roomNumber;                     // Room number
    private MapSite[] sites = new MapSite[4];     // Sides (order: 0 - North, 1 - East, 2 - South, 3 - West)
    private List<Bomb> bombs = new ArrayList<>(); // Bombs in the room
    private boolean exploded = false;           // True if a bomb has exploded in this room

    public Room(int x, int y, int roomNumber) {
        super(x, y);
        this.roomNumber = roomNumber;
        System.out.println("Room " + roomNumber + " created at (" + x + ", " + y + ")");
    }

    public void setSite(Directions direction, MapSite mapSite) {
        sites[direction.ordinal()] = mapSite;
        System.out.println(mapSite.getClass().getSimpleName() + " set on " + direction + " side of room " + roomNumber);
    }

    public MapSite[] getSites() {
        return sites;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
        System.out.println("Bomb added to room " + roomNumber + " at (" + bomb.getX() + ", " + bomb.getY() + ")");
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Handles the explosion of a bomb in this room. Only walls in this room are
     * destroyed; doors remain unchanged.
     *
     * @param bomb the exploded bomb.
     */
    public void handleExplosion(Bomb bomb) {
        System.out.println("Explosion in room " + roomNumber);
        bombs.remove(bomb);
        exploded = true; // Mark this room as exploded

        // Destroy walls in this room (do not touch doors)
        for (int i = 0; i < sites.length; i++) {
            if (sites[i] instanceof Wall wall) {
                wall.destroy();
                System.out.println("Wall " + Directions.values()[i] + " destroyed in room " + roomNumber);
            }
            // Do not modify doors!
        }
        // Do not propagate explosion to adjacent rooms
    }

    /**
     * (Optional) Called when an adjacent explosion occurs. In this
     * implementation, adjacent rooms are not affected.
     */
    public void onAdjacentExplosion() {
        // If desired, you can implement visual effects for adjacent explosions here.
        System.out.println("Room " + roomNumber + " felt an explosion nearby.");
    }

    /**
     * Removes the wall on the given side.
     *
     * @param direction the direction of the wall.
     */
    public void removeWall(Directions direction) {
        if (sites[direction.ordinal()] instanceof Wall wall) {
            wall.destroy();
            System.out.println("Wall " + direction + " marked as destroyed in room " + roomNumber);
        }
    }

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();

        // Draw room background and outline with room number
        g.setColor(Color.WHITE);
        g.fillRect(x, y, ROOM_SIZE, ROOM_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, ROOM_SIZE, ROOM_SIZE);
        g.drawString(String.valueOf(roomNumber), x + ROOM_SIZE / 4, y + ROOM_SIZE / 2);

        // If the room exploded, do not draw walls (doors remain drawn)
        for (int i = 0; i < sites.length; i++) {
            MapSite site = sites[i];
            if (site instanceof Wall) {
                // If wall is destroyed or room exploded, skip drawing it.
                continue;
            } else if (site instanceof Door) {
                drawDoor(g, i, x, y);
            } else if (site == null) {
                // If no site is set, assume a default intact wall should be drawn,
                // but if the room exploded, do not draw default walls.
                if (!exploded) {
                    drawDefaultWall(g, i, x, y);
                }
            } else {
                // In any other case, if the room has not exploded, draw default wall.
                if (!exploded) {
                    drawDefaultWall(g, i, x, y);
                }
            }
        }

        // Draw bombs (they remain visible even after explosion, if desired)
        for (Bomb bomb : bombs) {
            bomb.draw(g);
        }
    }

    private void drawDefaultWall(Graphics g, int side, int x, int y) {
        g.setColor(Color.BLACK);
        switch (side) {
            case 0 ->
                g.drawLine(x, y, x + ROOM_SIZE, y);           // North
            case 1 ->
                g.drawLine(x + ROOM_SIZE, y, x + ROOM_SIZE, y + ROOM_SIZE); // East
            case 2 ->
                g.drawLine(x, y + ROOM_SIZE, x + ROOM_SIZE, y + ROOM_SIZE); // South
            case 3 ->
                g.drawLine(x, y, x, y + ROOM_SIZE);           // West
        }
    }

    private void drawDoor(Graphics g, int side, int x, int y) {
        g.setColor(Color.BLACK);
        if (side == 0 || side == 2) { // Horizontal (North or South)
            int mid = x + ROOM_SIZE / 2;
            int yPos = (side == 0) ? y : y + ROOM_SIZE;
            g.drawLine(x, yPos, mid - DOOR_GAP / 2, yPos);
            g.drawLine(mid + DOOR_GAP / 2, yPos, x + ROOM_SIZE, yPos);
        } else if (side == 1 || side == 3) { // Vertical (East or West)
            int mid = y + ROOM_SIZE / 2;
            int xPos = (side == 3) ? x : x + ROOM_SIZE;
            g.drawLine(xPos, y, xPos, mid - DOOR_GAP / 2);
            g.drawLine(xPos, mid + DOOR_GAP / 2, xPos, y + ROOM_SIZE);
        }
    }
}
