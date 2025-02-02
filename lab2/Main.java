
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

class Model {

    private String expression = "";

    public void appendExpression(String value) {
        expression += value;
    }

    public void clearExpression() {
        expression = "";
    }

    public void deleteLastCharacter() {
        if (!expression.isEmpty()) {
            expression = expression.substring(0, expression.length() - 1);
        }
    }

    public String getExpression() {
        return expression;
    }

    public String calculate() {
        try {
            if (expression.endsWith(" ")) {
                expression = expression.substring(0, expression.length() - 3);
            }

            if (expression.isEmpty()) {
                return "";
            }

            String[] tokens = expression.split(" ");

            ArrayList<String> newTokens = new ArrayList<>();
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("*") || tokens[i].equals("/")) {
                    double left = Double.parseDouble(newTokens.remove(newTokens.size() - 1));
                    double right = Double.parseDouble(tokens[i + 1]);
                    if (tokens[i].equals("/") && right == 0) {
                        return "Error: Division by zero";
                    }
                    double result = tokens[i].equals("*") ? left * right : left / right;
                    newTokens.add(String.valueOf(result));
                    i++;
                } else {
                    newTokens.add(tokens[i]);
                }
            }

            double result = Double.parseDouble(newTokens.get(0));
            for (int i = 1; i < newTokens.size(); i++) {
                if (newTokens.get(i).equals("+") || newTokens.get(i).equals("-")) {
                    double nextNum = Double.parseDouble(newTokens.get(i + 1));
                    result += newTokens.get(i).equals("+") ? nextNum : -nextNum;
                    i++;
                }
            }

            expression = String.valueOf(result);
            return expression;

        } catch (Exception e) {
            return "Error";
        }
    }
}

class View extends JFrame {

    private JTextField textScreen = new JTextField();
    private JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
    private JButton[] numberButtons = new JButton[10];
    private JButton buttonPlus = new JButton("+");
    private JButton buttonMinus = new JButton("-");
    private JButton buttonMultiply = new JButton("*");
    private JButton buttonDivide = new JButton("/");
    private JButton buttonEqual = new JButton("=");
    private JButton buttonClear = new JButton("C");
    private JButton buttonDelete = new JButton("←");

    public View() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setTitle("Calculator - MVC");

        JPanel mainPanel = new JPanel(new BorderLayout());
        textScreen.setEditable(false);
        mainPanel.add(textScreen, BorderLayout.NORTH);

        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
        }

        for (int i = 7; i <= 9; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(buttonDivide);
        for (int i = 4; i <= 6; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(buttonMultiply);
        for (int i = 1; i <= 3; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(buttonMinus);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(buttonClear);
        buttonPanel.add(buttonDelete);
        buttonPanel.add(buttonPlus);
        buttonPanel.add(buttonEqual);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public JTextField getTextScreen() {
        return textScreen;
    }

    public JButton[] getNumberButtons() {
        return numberButtons;
    }

    public JButton getButtonPlus() {
        return buttonPlus;
    }

    public JButton getButtonMinus() {
        return buttonMinus;
    }

    public JButton getButtonMultiply() {
        return buttonMultiply;
    }

    public JButton getButtonDivide() {
        return buttonDivide;
    }

    public JButton getButtonEqual() {
        return buttonEqual;
    }

    public JButton getButtonClear() {
        return buttonClear;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }
}

class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        for (int i = 0; i <= 9; i++) {
            int finalI = i;
            view.getNumberButtons()[i].addActionListener(e -> {
                model.appendExpression(String.valueOf(finalI));
                view.getTextScreen().setText(model.getExpression());
            });
        }

        view.getButtonPlus().addActionListener(new OperatorListener("+"));
        view.getButtonMinus().addActionListener(new OperatorListener("-"));
        view.getButtonMultiply().addActionListener(new OperatorListener("*"));
        view.getButtonDivide().addActionListener(new OperatorListener("/"));

        view.getButtonClear().addActionListener(e -> {
            model.clearExpression();
            view.getTextScreen().setText("");
        });

        view.getButtonDelete().addActionListener(e -> {
            model.deleteLastCharacter();
            view.getTextScreen().setText(model.getExpression());
        });

        view.getButtonEqual().addActionListener(e -> {
            String result = model.calculate();
            view.getTextScreen().setText(result);
        });

        view.setVisible(true);
    }

    private class OperatorListener implements ActionListener {

        private String operator;

        public OperatorListener(String operator) {
            this.operator = operator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            model.appendExpression(" " + operator + " ");
            view.getTextScreen().setText(model.getExpression());
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        new Controller(model, view);
    }
}
