
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Main application class for displaying the maze.
 */
public class App extends JFrame {

    private MyJPanel panelFactoryMethod; // Panel for generating the maze using Factory Method
    private MyJPanel panelSingleton; // Panel for generating the maze using Singleton

    private MazeBuilder factoryMethodBuilder; // Maze builder for Factory Method
    private MazeBuilder singletonBuilder; // Maze builder for Singleton

    /**
     * Constructor for the main application window.
     */
    public App() {
        setTitle("Maze Generator with Factory Method & Singleton");
        setSize(1200, 600); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on window close
        setLayout(new BorderLayout());

        factoryMethodBuilder = new MazeBuilder();
        singletonBuilder = SingletonMazeBuilder.getInstance();

        panelFactoryMethod = new MyJPanel(factoryMethodBuilder);
        panelSingleton = new MyJPanel(singletonBuilder);

        JButton generateFactoryMethodButton = new JButton("Generate: Factory Method");
        generateFactoryMethodButton.addActionListener(e -> {
            System.out.println("🔄 Generating maze using Factory Method...");
            factoryMethodBuilder.generateMaze(5, 10);
            panelFactoryMethod.repaint();
        });

        JButton generateSingletonButton = new JButton("Generate: Singleton");
        generateSingletonButton.addActionListener(e -> {
            System.out.println("🔄 Generating maze using Singleton...");
            singletonBuilder.generateMaze(5, 10);
            panelSingleton.repaint();
        });

        JButton generateBombsButton = new JButton("Add Bombs");
        generateBombsButton.addActionListener(e -> {
            System.out.println("💣 Adding bombs...");
            for (Room room : factoryMethodBuilder.getMaze().getRooms()) {
                if (Math.random() < 0.3) { // 30% chance to add a bomb
                    Bomb bomb = new Bomb(room.getX() + 10, room.getY() + 10); // Offset for correct display
                    room.addBomb(bomb);
                }
            }
            panelFactoryMethod.repaint();
        });

        JButton explodeBombsButton = new JButton("Detonate Bombs");
        explodeBombsButton.addActionListener(e -> {
            System.out.println("💥 Detonating bombs...");
            for (Room room : factoryMethodBuilder.getMaze().getRooms()) {
                for (Bomb bomb : new ArrayList<>(room.getBombs())) { // Avoid ConcurrentModificationException
                    bomb.explode(room);
                }
            }
            panelFactoryMethod.repaint();
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4));
        controlPanel.add(generateFactoryMethodButton);
        controlPanel.add(generateSingletonButton);
        controlPanel.add(generateBombsButton);
        controlPanel.add(explodeBombsButton);

        JPanel splitPanel = new JPanel(new GridLayout(1, 2));
        splitPanel.add(panelFactoryMethod);
        splitPanel.add(panelSingleton);

        add(controlPanel, BorderLayout.NORTH);
        add(splitPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}
