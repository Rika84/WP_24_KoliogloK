
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a maze.
 */
public class Maze {

    private List<Room> rooms; // List of rooms in the maze

    /**
     * Constructor to create an empty maze.
     */
    public Maze() {
        rooms = new ArrayList<>();
    }

    /**
     * Adds a room to the maze.
     *
     * @param room Room object to be added.
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Finds a room by its number.
     *
     * @param roomNumber the number of the room to find.
     * @return Room object or null if not found.
     */
    public Room getRoomNo(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    /**
     * Draws the maze using the specified Graphics object.
     *
     * @param g Graphics object for drawing.
     */
    public void drawMaze(Graphics g) {
        for (Room room : rooms) {
            room.draw(g);
        }
    }

    /**
     * Returns the list of all rooms in the maze.
     *
     * @return list of Room objects.
     */
    public List<Room> getRooms() {
        return rooms;
    }
}
