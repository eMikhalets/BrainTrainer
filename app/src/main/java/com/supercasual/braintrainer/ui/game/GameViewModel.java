package com.supercasual.braintrainer.ui.game;

import androidx.lifecycle.ViewModel;

import java.util.Random;

import timber.log.Timber;

public class GameViewModel extends ViewModel {

    private int firstOperand;
    private int secondOperand;
    private int result;
    private String operation;
    private int answers = 0;
    private int mistakes = 0;

    public GameViewModel() {
    }

    public int getFirstOperand() {
        return firstOperand;
    }

    public int getSecondOperand() {
        return secondOperand;
    }

    public int getResult() {
        return result;
    }

    public int getAnswers() {
        return answers;
    }

    public int getMistakes() {
        return mistakes;
    }

    public String getOperation() {
        return operation;
    }

    public void increaseAnswers() {
        answers++;
    }

    public void increaseMistakes() {
        mistakes++;
    }

    private void generateOperands() {
        Random random = new Random();
        firstOperand = random.nextInt(99) + 1;
        secondOperand = random.nextInt(99) + 1;
        if (random.nextBoolean()) firstOperand *= -1;
        if (random.nextBoolean()) secondOperand *= -1;
    }

    public void generateLevel(int mode) {
        generateOperands();

        if (mode == 0) {
            operation = "+";
            result = firstOperand + secondOperand;
        } else {
            Random random = new Random();
            int randomOperation = random.nextInt(mode);

            switch (randomOperation) {
                case 0:
                    operation = "+";
                    result = firstOperand + secondOperand;
                    break;
                case 1:
                    operation = "-";
                    result = firstOperand - secondOperand;
                    break;
                case 2:
                    operation = "*";
                    result = firstOperand * secondOperand;
                    break;
                case 3:
                    operation = "/";
                    result = firstOperand / secondOperand;
                    break;
            }
        }
        Timber.d("Example: %d %s %d = %d", firstOperand, operation, secondOperand, result);
    }

    public void generateOperation(int mode) {
        generateOperands();

        switch (mode) {
            case 0:
                operation = "+";
                result = firstOperand + secondOperand;
                break;
            case 1:
                operation = "-";
                result = firstOperand - secondOperand;
                break;
            case 2:
                operation = "*";
                result = firstOperand * secondOperand;
                break;
            case 3:
                operation = "/";
                result = firstOperand / secondOperand;
                break;
        }
        Timber.d("Example: %d %s %d = %d", firstOperand, operation, secondOperand, result);
    }
}