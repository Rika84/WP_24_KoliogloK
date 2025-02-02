
/**
 * Singleton class for constructing mazes.
 */
public class SingletonMazeBuilder extends MazeBuilder {

    private static volatile SingletonMazeBuilder instance; // The single instance

    /**
     * Private constructor to prevent external instantiation.
     */
    private SingletonMazeBuilder() {
        super();
    }

    /**
     * Returns the single instance of SingletonMazeBuilder.
     *
     * @return the SingletonMazeBuilder instance.
     */
    public static SingletonMazeBuilder getInstance() {
        if (instance == null) {
            synchronized (SingletonMazeBuilder.class) {
                if (instance == null) {
                    instance = new SingletonMazeBuilder();
                }
            }
        }
        return instance;
    }

    /**
     * Generates a new maze.
     *
     * @param gridSize the grid size.
     * @param maxRooms the maximum number of rooms.
     */
    @Override
    public void generateMaze(int gridSize, int maxRooms) {
        super.generateMaze(gridSize, maxRooms);
    }
}
