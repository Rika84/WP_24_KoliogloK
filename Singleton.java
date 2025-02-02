// Wzorzec Singleton gwarantuje, że klasa ma tylko jeden egzemplarz i zapewnia globalny punkt dostępu do niego
// Шаблон Singleton гарантирует, что у класса будет только один экземпляр и предоставляется глобальная точка доступа

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Klasa Singleton
public class Singleton {

    // Prywatne statyczne pole przechowujące jedyną instancję Singleton
    // Приватное статическое поле, хранящее единственный экземпляр Singleton
    private static Singleton instance;
    private static int callCount = 0; // Licznik wywołań getInstance
    // Счетчик вызовов метода getInstance

    // Prywatny konstruktor zapobiegający tworzeniu instancji spoza klasy
    // Приватный конструктор, предотвращающий создание экземпляров извне класса
    private Singleton() {
        System.out.println("Tworzę jedyny egzemplarz Singleton.");
        // Создание единственного экземпляра Singleton
    }

    // Publiczna metoda dostępu do instancji Singleton (z synchronizacją dla bezpieczeństwa w środowiskach wielowątkowych)
    // Публичный метод доступа к экземпляру Singleton (с синхронизацией для безопасности в многопоточной среде)
    public static synchronized Singleton getInstance() {
        callCount++;
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Metoda do pobierania liczby wywołań getInstance
    // Метод для получения количества вызовов getInstance
    public static int getCallCount() {
        return callCount;
    }

    // Przykładowa metoda instancji Singleton
    // Пример метода экземпляра Singleton
    public void pokazInformacje() {
        System.out.println("To jest instancja Singleton: " + this);
        // Это экземпляр Singleton: " + this
    }

    // Klasa wewnętrzna do tworzenia GUI z użyciem Swing
    // Внутренний класс для создания GUI с использованием Swing
    public static class SingletonDemoSwing extends JFrame {

        private JTextArea logArea; // Pole tekstowe dla logów
        private JLabel statusLabel; // Etykieta statusu
        private JPanel instancePanel; // Panel instancji
        private JLabel callCountLabel; // Etykieta liczby wywołań

        public SingletonDemoSwing() {
            setTitle("Demonstracja wzorca Singleton");
            // Заголовок окна
            setSize(700, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Tworzenie GUI
            // Создание GUI
            JPanel mainPanel = new JPanel(new BorderLayout());
            logArea = new JTextArea();
            logArea.setEditable(false);
            logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(logArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            statusLabel = new JLabel("Status: Nie utworzono instancji Singleton", JLabel.CENTER);
            // Статус: Экземпляр Singleton не создан
            statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
            statusLabel.setOpaque(true);
            statusLabel.setBackground(Color.LIGHT_GRAY);
            mainPanel.add(statusLabel, BorderLayout.NORTH);

            instancePanel = new JPanel();
            instancePanel.setBackground(Color.LIGHT_GRAY);
            JLabel placeholderLabel = new JLabel("Brak instancji Singleton");
            // Нет экземпляра Singleton
            placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            instancePanel.add(placeholderLabel);
            mainPanel.add(instancePanel, BorderLayout.WEST);

            callCountLabel = new JLabel("Liczba wywołań getInstance: 0", JLabel.CENTER);
            // Количество вызовов getInstance: 0
            callCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            callCountLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            mainPanel.add(callCountLabel, BorderLayout.SOUTH);

            JButton createInstanceButton = new JButton("Utwórz instancję Singleton");
            // Создать экземпляр Singleton
            createInstanceButton.setFont(new Font("Arial", Font.BOLD, 14));
            createInstanceButton.setBackground(Color.CYAN);
            createInstanceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Singleton instance = Singleton.getInstance();
                    log("[INFO] Próba utworzenia instancji Singleton...");
                    // [INFO] Попытка создания экземпляра Singleton...
                    if (Singleton.getCallCount() == 1) {
                        log("[SUCCESS] Utworzono instancję Singleton: " + instance);
                        // [SUCCESS] Создан экземпляр Singleton
                        JOptionPane.showMessageDialog(SingletonDemoSwing.this, "Instancja Singleton została utworzona!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        // Экземпляр Singleton был создан!
                        statusLabel.setText("Status: Instancja Singleton utworzona");
                        // Статус: Экземпляр Singleton создан
                        statusLabel.setBackground(Color.GREEN);
                        updateInstancePanel(instance);
                    } else {
                        log("[INFO] Instancja Singleton już istnieje. Nie tworzę nowej.");
                        // [INFO] Экземпляр Singleton уже существует. Новый не создаётся.
                        JOptionPane.showMessageDialog(SingletonDemoSwing.this, "Instancja Singleton już istnieje!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        // Экземпляр Singleton уже существует!
                    }
                    updateCallCount();
                }
            });

            JButton showInfoButton = new JButton("Pokaż informacje o instancji");
            // Показать информацию об экземпляре
            showInfoButton.setFont(new Font("Arial", Font.BOLD, 14));
            showInfoButton.setBackground(Color.YELLOW);
            showInfoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Singleton.instance != null) {
                        log("[INFO] Wyświetlam informacje o instancji Singleton.");
                        // [INFO] Отображаю информацию об экземпляре Singleton
                        Singleton.instance.pokazInformacje();
                        log("[DETAIL] To jest instancja Singleton: " + Singleton.instance);
                        // [DETAIL] Это экземпляр Singleton
                    } else {
                        log("[WARNING] Instancja Singleton jeszcze nie została utworzona.");
                        // [WARNING] Экземпляр Singleton ещё не создан
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.DARK_GRAY);
            buttonPanel.add(createInstanceButton);
            buttonPanel.add(showInfoButton);

            mainPanel.add(buttonPanel, BorderLayout.EAST);
            add(mainPanel);
        }

        private void log(String message) {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength()); // Automatyczne przewijanie logów
            // Автоматическая прокрутка логов
        }

        private void updateInstancePanel(Singleton instance) {
            instancePanel.removeAll();
            instancePanel.setBackground(Color.GREEN);
            JLabel instanceLabel = new JLabel("Instancja Singleton: " + instance.toString());
            // Экземпляр Singleton: " + instance.toString()
            instanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
            instancePanel.add(instanceLabel);
            instancePanel.revalidate();
            instancePanel.repaint();
        }

        private void updateCallCount() {
            callCountLabel.setText("Liczba wywołań getInstance: " + Singleton.getCallCount());
            // Количество вызовов getInstance: " + Singleton.getCallCount()
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    SingletonDemoSwing demo = new SingletonDemoSwing();
                    demo.setVisible(true);
                }
            });
        }
    }
}
