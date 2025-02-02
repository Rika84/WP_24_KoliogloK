
import java.awt.*;
import javax.swing.*;

/**
 * Panel for displaying the maze. Walls are drawn according to the following
 * rules: - Each room is fully drawn (all 4 walls). - If a side contains a Door
 * object, a doorway is drawn; if it contains a Wall object, a solid line is
 * drawn (if the wall is not destroyed). - Internal walls are not drawn after an
 * explosion.
 */
public class MyJPanel extends JPanel {

    private static final int ROOM_SIZE = 50;  // Room size (width and height)
    private static final int DOOR_GAP = 10;   // Gap size for the doorway

    private MazeBuilder mazeBuilder;

    public MyJPanel(MazeBuilder mazeBuilder) {
        super();
        this.mazeBuilder = mazeBuilder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fill the background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (mazeBuilder == null || mazeBuilder.getMaze() == null) {
            return;
        }

        Maze maze = mazeBuilder.getMaze();

        // Draw each room
        for (Room room : maze.getRooms()) {
            drawRoom(g, room, maze);
        }

        // Draw bombs
        for (Room room : maze.getRooms()) {
            for (Bomb bomb : room.getBombs()) {
                bomb.draw(g);
            }
        }
    }

    /**
     * Draws a room: fills the background, draws the room number, and then draws
     * all 4 walls.
     *
     * @param g Graphics context.
     * @param room Room to draw.
     * @param maze Maze (used for neighbor detection if needed).
     */
    private void drawRoom(Graphics g, Room room, Maze maze) {
        int x = room.getX();
        int y = room.getY();

        // Fill room background (e.g., LIGHT_GRAY)
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, ROOM_SIZE, ROOM_SIZE);

        // Draw room number (centered)
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(room.getRoomNumber()),
                x + ROOM_SIZE / 2 - 5,
                y + ROOM_SIZE / 2 + 5);

        // Check if an explosion occurred in the room
        boolean bombExploded = roomHasExplodedBomb(room);

        // Draw all four walls
        // North wall (index 0)
        drawSide(g, room.getSites()[0], x, y, x + ROOM_SIZE, y, bombExploded);
        // East wall (index 1)
        drawSide(g, room.getSites()[1], x + ROOM_SIZE, y, x + ROOM_SIZE, y + ROOM_SIZE, bombExploded);
        // South wall (index 2)
        drawSide(g, room.getSites()[2], x, y + ROOM_SIZE, x + ROOM_SIZE, y + ROOM_SIZE, bombExploded);
        // West wall (index 3)
        drawSide(g, room.getSites()[3], x, y, x, y + ROOM_SIZE, bombExploded);
    }

    /**
     * Draws a wall (line from (x1, y1) to (x2, y2)) based on the MapSite type.
     * If an explosion occurred (bombExploded == true), the wall is not drawn.
     * If site is null, a solid line is drawn by default.
     *
     * @param g Graphics context.
     * @param site Object representing the side (Wall or Door), can be null.
     * @param x1, y1 Start point of the line.
     * @param x2, y2 End point of the line.
     * @param bombExploded True if an explosion occurred in the room.
     */
    private void drawSide(Graphics g, MapSite site, int x1, int y1, int x2, int y2, boolean bombExploded) {
        // Do not draw the wall if an explosion occurred
        if (bombExploded) {
            return;
        }

        // If site is not set, assume a wall should be there
        if (site == null) {
            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x2, y2);
            return;
        }

        g.setColor(Color.BLACK);
        if (site instanceof Wall) {
            Wall wall = (Wall) site;
            if (wall.isDestroyed()) {
                // If the wall is destroyed, do not draw it
                return;
            } else {
                // Draw a solid line
                g.drawLine(x1, y1, x2, y2);
            }
        } else if (site instanceof Door) {
            // Draw a doorway – two lines with a gap in the middle.
            if (y1 == y2) { // Horizontal line (North or South)
                int mid = (x1 + x2) / 2;
                g.drawLine(x1, y1, mid - DOOR_GAP / 2, y1);
                g.drawLine(mid + DOOR_GAP / 2, y1, x2, y1);
            } else if (x1 == x2) { // Vertical line (East or West)
                int mid = (y1 + y2) / 2;
                g.drawLine(x1, y1, x1, mid - DOOR_GAP / 2);
                g.drawLine(x1, mid + DOOR_GAP / 2, x2, y2);
            }
        }
    }

    /**
     * Checks if the given room contains at least one exploded bomb. Assumes the
     * method isExploded() in Bomb is implemented as: return !isActive;
     *
     * @param room Room to check.
     * @return True if at least one bomb has exploded, otherwise false.
     */
    private boolean roomHasExplodedBomb(Room room) {
        for (Bomb bomb : room.getBombs()) {
            if (bomb.isExploded()) {
                return true;
            }
        }
        return false;
    }
}
