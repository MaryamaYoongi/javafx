package calculator.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controller {
    String[] operators = {"+", "-", "*", "/"};

    @FXML
    private TextField display;

    private boolean LOCK_CONTEXT = false;

    private SimpleStringProperty value = new SimpleStringProperty();

    @FXML
    public void initialize() {
        System.out.println("Controller is ready to work!!!");
        value.set("");
        display.textProperty().bind(value);
    }

    public void changeDisplayValue(ActionEvent actionEvent) {
        value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());
    }


    public void ceIsClicked(ActionEvent actionEvent) {
        value.set("");
        if (LOCK_CONTEXT) {
            LOCK_CONTEXT = false;
        }
    }


    public void cIsClicked(ActionEvent actionEvent) {
        value.set("");
        if (LOCK_CONTEXT) {
            LOCK_CONTEXT = false;
        }
    }


    public void backspaceIsClicked(ActionEvent actionEvent) {
        value.set(value.getValue());
    }

    public void plusMinusIsClicked(ActionEvent actionEvent) {
        System.out.println("The key *+-* was pressed.");
    }


    public void dotIsClicked(ActionEvent actionEvent) {
        if (!LOCK_CONTEXT) {
            value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());
            LOCK_CONTEXT = true;
        }
    }

    public void equalSignIsClicked(ActionEvent actionEvent) {
        String line = value.getValue();
        String currentOperator = "+";
        int indexCurrentOperator = 0;
        for (String operator : operators) {
            if (line.contains(operator)) {
                currentOperator = operator;
                indexCurrentOperator = line.indexOf(operator);
                break;
            }
        }
        String stringNumber1 = line.substring(0, indexCurrentOperator);
        String stringNumber2 = line.substring(indexCurrentOperator + 1, line.length());
        double number1 = Double.parseDouble(stringNumber1);
        double number2 = Double.parseDouble(stringNumber2);
        double result = 0;
        switch (currentOperator) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                result = number1 / number2;

        }
        value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());
        value.set(value.getValue() + result);
    }

    public void plusIsClicked(ActionEvent actionEvent) {
        value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());
    }

    public void minusIsClicked(ActionEvent actionEvent) {
        value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());
    }


    public void multiplyIsClicked(ActionEvent actionEvent) {
        value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());

    }


    public void divideIsClicked(ActionEvent actionEvent) {
        value.set(value.getValue() + ((Button) actionEvent.getTarget()).getText());
    }
}
