
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lab1 extends JFrame {

    // кнопки цифр - przyciski cyfr
    private JButton button_1 = new JButton("1");
    private JButton button_2 = new JButton("2");
    private JButton button_3 = new JButton("3");
    private JButton button_4 = new JButton("4");
    private JButton button_5 = new JButton("5");
    private JButton button_6 = new JButton("6");
    private JButton button_7 = new JButton("7");
    private JButton button_8 = new JButton("8");
    private JButton button_9 = new JButton("9");
    private JButton button_0 = new JButton("0");

    // кнопки операций - przyciski operacji
    private JButton button_C = new JButton("C"); // кнопка очистки - przycisk czyszczenia
    private JButton button_minus = new JButton("-");
    private JButton button_plus = new JButton("+");
    private JButton button_divide = new JButton("/");
    private JButton button_multiply = new JButton("*");
    private JButton button_delete = new JButton("<-"); // кнопка удаления - przycisk usuwania
    private JButton button_equal = new JButton("=");   // кнопка равно - przycisk wyniku

    // текстовое поле - pole tekstowe
    private JTextField textScreen;

    // строка для хранения выражения - zmienna do przechowywania wyrażenia
    private String expression = "";
    private boolean isResultDisplayed = false; // флаг для отслеживания, был ли результат на экране - flaga sprawdzająca, czy wynik jest wyświetlany

    public lab1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        // основная панель - panel główny
        JPanel maiPanel = new JPanel(new BorderLayout());
        textScreen = new JTextField();
        maiPanel.add("North", textScreen);  // добавляем текстовое поле сверху - dodajemy pole tekstowe na górze

        // панель кнопок - panel przycisków
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        // добавляем кнопки на панель - dodajemy przyciski na panel
        buttonPanel.add(button_7);
        buttonPanel.add(button_8);
        buttonPanel.add(button_9);
        buttonPanel.add(button_divide);  // кнопка деления - przycisk dzielenia
        buttonPanel.add(button_4);
        buttonPanel.add(button_5);
        buttonPanel.add(button_6);
        buttonPanel.add(button_multiply);  // кнопка умножения - przycisk mnożenia
        buttonPanel.add(button_1);
        buttonPanel.add(button_2);
        buttonPanel.add(button_3);
        buttonPanel.add(button_minus);  // кнопка вычитания - przycisk odejmowania
        buttonPanel.add(button_0);
        buttonPanel.add(button_C);  // кнопка очистки - przycisk czyszczenia
        buttonPanel.add(button_delete);  // кнопка удаления - przycisk usuwania
        buttonPanel.add(button_plus);  // кнопка сложения - przycisk dodawania
        buttonPanel.add(button_equal);  // кнопка равно - przycisk wyniku

        maiPanel.add("Center", buttonPanel); // добавляем панель кнопок в центр - dodajemy panel przycisków w centrum
        add(maiPanel);

        // обработчики событий для кнопок - obsługa zdarzeń dla przycisków
        button_0.addActionListener(new NumberListener("0"));
        button_1.addActionListener(new NumberListener("1"));
        button_2.addActionListener(new NumberListener("2"));
        button_3.addActionListener(new NumberListener("3"));
        button_4.addActionListener(new NumberListener("4"));
        button_5.addActionListener(new NumberListener("5"));
        button_6.addActionListener(new NumberListener("6"));
        button_7.addActionListener(new NumberListener("7"));
        button_8.addActionListener(new NumberListener("8"));
        button_9.addActionListener(new NumberListener("9"));

        button_plus.addActionListener(new OperatorListener("+"));
        button_minus.addActionListener(new OperatorListener("-"));
        button_multiply.addActionListener(new OperatorListener("*"));
        button_divide.addActionListener(new OperatorListener("/"));

        button_equal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();  // расчет результата - obliczenie wyniku
            }
        });

        button_C.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textScreen.setText("");
                expression = "";  // очистка выражения - czyszczenie wyrażenia
                isResultDisplayed = false;  // сброс флага результата - resetowanie flagi wyniku
            }
        });

        // обработчик для кнопки удаления - obsługa przycisku usuwania
        button_delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (expression.length() > 0) {
                    // Проверяем, есть ли пробелы перед последним символом (то есть оператор с пробелами) - sprawdzamy, czy są odstępy przed ostatnim znakiem (czyli operator z odstępami)
                    if (expression.endsWith(" ")) {
                        // Удаляем последний оператор и пробелы - usuwamy ostatni operator i odstępy
                        expression = expression.substring(0, expression.length() - 3);
                    } else {
                        // Удаляем последний символ - usuwamy ostatni znak
                        expression = expression.substring(0, expression.length() - 1);
                    }
                    // Обновляем текстовое поле сразу после изменения - aktualizujemy pole tekstowe po zmianie
                    textScreen.setText(expression);
                }
            }
        });

        setVisible(true);  // делаем окно видимым - ustawiamy okno jako widoczne
    }

    // обработчик для числовых кнопок - obsługa przycisków numerycznych
    private class NumberListener implements ActionListener {

        private String value;

        public NumberListener(String value) {
            this.value = value;
        }

        public void actionPerformed(ActionEvent e) {
            if (isResultDisplayed) {
                // Если результат уже был показан и пользователь вводит новое число, мы начинаем новое выражение - Jeśli wynik został wyświetlony, zaczynamy nowe wyrażenie
                expression = "";
                isResultDisplayed = false;
            }
            expression += value;  // добавление числа к выражению - dodawanie liczby do wyrażenia
            textScreen.setText(expression);  // обновление экрана - aktualizacja ekranu
        }
    }

    // обработчик для кнопок операторов - obsługa przycisków operatorów
    private class OperatorListener implements ActionListener {

        private String op;

        public OperatorListener(String op) {
            this.op = op;
        }

        public void actionPerformed(ActionEvent e) {
            if (isResultDisplayed) {
                // Если был показан результат, продолжаем работать с ним - Jeśli wynik został wyświetlony, kontynuujemy pracę z nim
                isResultDisplayed = false;
            }
            if (expression.isEmpty()) {
                // Игнорируем операторы, если ничего нет в выражении - Ignorujemy operatory, jeśli wyrażenie jest puste
                return;
            }
            if (expression.endsWith(" ")) {
                // Если уже есть оператор в конце, заменяем его на новый - Jeśli operator już istnieje na końcu, zamieniamy go na nowy
                expression = expression.substring(0, expression.length() - 3) + " " + op + " ";
            } else {
                // Добавляем оператор, если его еще нет в конце - Dodajemy operator, jeśli go jeszcze nie ma na końcu
                expression += " " + op + " ";
            }
            textScreen.setText(expression);  // обновление экрана - aktualizacja ekranu
        }
    }

    // метод для выполнения расчета с учетом приоритета операций - metoda obliczająca z uwzględnieniem priorytetów operatorów
    private void calculate() {
        try {
            // Если выражение заканчивается на оператор, удаляем этот оператор - Jeśli wyrażenie kończy się operatorem, usuwamy ten operator
            if (expression.endsWith(" ")) {
                expression = expression.substring(0, expression.length() - 3);
            }

            if (expression.isEmpty()) {
                return;  // Если после этого выражение пустое, ничего не делаем - Jeśli po tym wyrażenie jest puste, nic nie robimy
            }

            String[] tokens = expression.split(" ");
            // первый проход: обработка умножения и деления - pierwszy krok: obsługa mnożenia i dzielenia
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("*") || tokens[i].equals("/")) {
                    double left = Double.parseDouble(tokens[i - 1]);
                    double right = Double.parseDouble(tokens[i + 1]);
                    double result = 0;

                    if (tokens[i].equals("*")) {
                        result = left * right;
                    } else if (tokens[i].equals("/")) {
                        if (right == 0) {
                            textScreen.setText("Error: Division by zero");  // ошибка деления на ноль - błąd dzielenia przez zero
                            return;
                        }
                        result = left / right;
                    }

                    // замена операции и чисел на результат - zamiana operacji i liczb na wynik
                    tokens[i - 1] = String.valueOf(result);
                    tokens[i] = "";
                    tokens[i + 1] = "";
                }
            }

            // второй проход: обработка сложения и вычитания - drugi krok: obsługa dodawania i odejmowania
            double result = Double.parseDouble(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].equals("+") || tokens[i].equals("-")) {
                    double nextNum = Double.parseDouble(tokens[i + 1]);

                    if (tokens[i].equals("+")) {
                        result += nextNum;
                    } else if (tokens[i].equals("-")) {
                        result -= nextNum;
                    }
                }
            }

            textScreen.setText(String.valueOf(result));  // отображение результата на экране - wyświetlanie wyniku na ekranie
            expression = String.valueOf(result);  // обновление выражения результатом - aktualizacja wyrażenia wynikiem
            isResultDisplayed = false;  // Сбрасываем флаг, чтобы пользователь мог продолжить работу с результатом - Resetujemy flagę, aby użytkownik mógł kontynuować pracę z wynikiem
        } catch (Exception e) {
            textScreen.setText("Error");  // вывод ошибки - wyświetlenie błędu
            expression = "";
            isResultDisplayed = false;  // сброс флага результата - resetowanie flagi wyniku
        }
    }

    public static void main(String[] args) {
        new lab1();  // запуск калькулятора - uruchomienie kalkulatora
    }
}
