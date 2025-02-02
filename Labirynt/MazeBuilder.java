
import java.util.*;

/**
 * Class responsible for building mazes.
 */
public class MazeBuilder {

    private Maze maze; // Maze to be built
    private Random random; // For generating random values

    /**
     * Constructor for creating MazeBuilder.
     */
    public MazeBuilder() {
        this.maze = new Maze();
        this.random = new Random();
    }

    /**
     * Generates a maze with the given parameters.
     *
     * @param gridSize Grid size (e.g., 5 for 5x5).
     * @param maxRooms Maximum number of rooms.
     */
    public void generateMaze(int gridSize, int maxRooms) {
        maze = new Maze(); // New maze
        Room[][] grid = new Room[gridSize][gridSize];
        int roomCount = Math.min(random.nextInt(maxRooms) + 1, gridSize * gridSize);
        int currentRooms = 0;

        // Random starting room
        int startRow = random.nextInt(gridSize);
        int startCol = random.nextInt(gridSize);
        Room startRoom = new Room(startCol * 50, startRow * 50, ++currentRooms);
        maze.addRoom(startRoom);
        grid[startRow][startCol] = startRoom;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startRow, startCol});

        // Directions for generation (North, East, South, West)
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        // Main maze generation loop
        while (!stack.isEmpty() && currentRooms < roomCount) {
            int[] current = stack.pop();
            int row = current[0];
            int col = current[1];

            // Shuffle directions randomly
            List<int[]> shuffledDirections = Arrays.asList(directions.clone());
            Collections.shuffle(shuffledDirections);

            for (int[] direction : shuffledDirections) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize && grid[newRow][newCol] == null) {
                    if (currentRooms < roomCount) {
                        // Create a new room
                        Room newRoom = new Room(newCol * 50, newRow * 50, ++currentRooms);
                        maze.addRoom(newRoom);
                        grid[newRow][newCol] = newRoom;
                        stack.push(new int[]{newRow, newCol});

                        // Create a door between the current and new room
                        Room currentRoom = grid[row][col];
                        Door door = new Door(currentRoom, newRoom);

                        if (direction[0] == -1) { // North
                            currentRoom.setSite(Directions.North, door);
                            newRoom.setSite(Directions.South, door);
                        } else if (direction[0] == 1) { // South
                            currentRoom.setSite(Directions.South, door);
                            newRoom.setSite(Directions.North, door);
                        } else if (direction[1] == -1) { // West
                            currentRoom.setSite(Directions.West, door);
                            newRoom.setSite(Directions.East, door);
                        } else if (direction[1] == 1) { // East
                            currentRoom.setSite(Directions.East, door);
                            newRoom.setSite(Directions.West, door);
                        }

                        // Add walls to unused sides
                        addWallsToRoom(currentRoom, grid, row, col, gridSize);
                    }
                }
            }
        }
    }

    /**
     * Adds walls to a room on all unused sides.
     *
     * @param room Current room.
     * @param grid Grid with rooms.
     * @param row Current row.
     * @param col Current column.
     * @param gridSize Grid size.
     */
    private void addWallsToRoom(Room room, Room[][] grid, int row, int col, int gridSize) {
        for (Directions direction : Directions.values()) {
            int newRow = row + (direction == Directions.North ? -1 : direction == Directions.South ? 1 : 0);
            int newCol = col + (direction == Directions.West ? -1 : direction == Directions.East ? 1 : 0);

            if (newRow < 0 || newRow >= gridSize || newCol < 0 || newCol >= gridSize || grid[newRow][newCol] == null) {
                if (room.getSites()[direction.ordinal()] == null) {
                    room.setSite(direction, new Wall(direction));
                }
            }
        }
    }

    /**
     * Returns the current maze.
     *
     * @return Maze object.
     */
    public Maze getMaze() {
        return maze;
    }
}
