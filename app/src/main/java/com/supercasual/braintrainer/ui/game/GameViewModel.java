package com.supercasual.braintrainer.ui.game;

import androidx.lifecycle.ViewModel;

import com.supercasual.braintrainer.utils.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

/**
 * This class generates example parameters and updates game statistics
 */
public class GameViewModel extends ViewModel {

    // Elements of example
    private int firstOperand;
    private int secondOperand;
    private String operation;

    // Result of example and wrong answers
    private int result;
    private List<Integer> fakeResults = new ArrayList<>();

    // Game data
    private String gameMode;
    private int answers;
    private int mistakes;

    public GameViewModel() {
    }

    // Getters and setters
    public int getFirstOperand() {
        return firstOperand;
    }

    public int getSecondOperand() {
        return secondOperand;
    }

    public String getOperation() {
        return operation;
    }

    public int getResult() {
        return result;
    }

    public List<Integer> getFakeResults() {
        return fakeResults;
    }

    public int getAnswers() {
        return answers;
    }

    public int getMistakes() {
        return mistakes;
    }

    // Change game statistics
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void increaseAnswers() {
        answers++;
    }

    public void increaseMistakes() {
        mistakes++;
    }

    // Generate example methods

    public void generateExample() {
        Random random = new Random();
        determineGenerationMode(random);

        // Generation of fake results
        fakeResults.clear();
        for (int i = 0; i < 3; i++) {
            int num = 0;
            boolean isContains = true;
            while (isContains) {
                int deviation = random.nextInt(10);
                if (random.nextBoolean()) deviation *= -1;
                num = result + deviation;
                isContains = fakeResults.contains(num) || num == result;
            }
            fakeResults.add(num);
        }

        Timber.d("Example: %d %s %d = %d",
                firstOperand, operation, secondOperand, result);
        Timber.d("Fake results: %d %d %d ",
                fakeResults.get(0), fakeResults.get(1), fakeResults.get(2));
    }

    private void determineGenerationMode(Random random) {
        switch (gameMode) {
            case Const.LEVEL_1:
                generateAdditionExample(random);
                break;
            case Const.LEVEL_2:
                switch (random.nextInt(2)) {
                    case 0:
                        generateAdditionExample(random);
                        break;
                    case 1:
                        generateSubtractionExample(random);
                        break;
                }
                break;
            case Const.LEVEL_3:
                switch (random.nextInt(4)) {
                    case 0:
                        generateAdditionExample(random);
                        break;
                    case 1:
                        generateSubtractionExample(random);
                        break;
                    case 2:
                        generateMultiplicationExample(random);
                        break;
                    case 3:
                        generateDivisionExample(random);
                        break;
                }
                break;
            case Const.ADDITION:
                generateAdditionExample(random);
                break;
            case Const.SUBTRACTION:
                generateSubtractionExample(random);
                break;
            case Const.MULTIPLICATION:
                generateMultiplicationExample(random);
                break;
            case Const.DIVISION:
                generateDivisionExample(random);
                break;
        }
    }

    private void generateAdditionExample(Random random) {
        operation = "+";
        firstOperand = random.nextInt(99) + 1;
        secondOperand = random.nextInt(99) + 1;
        if (random.nextBoolean()) firstOperand *= -1;
        result = firstOperand + secondOperand;
    }

    private void generateSubtractionExample(Random random) {
        operation = "-";
        firstOperand = random.nextInt(99) + 1;
        secondOperand = random.nextInt(99) + 1;
        if (random.nextBoolean()) firstOperand *= -1;
        result = firstOperand - secondOperand;
    }

    private void generateMultiplicationExample(Random random) {
        operation = "*";
        firstOperand = random.nextInt(10);
        secondOperand = random.nextInt(10);
        result = firstOperand * secondOperand;
    }

    private void generateDivisionExample(Random random) {
        operation = "/";
        firstOperand = random.nextInt(100);

        boolean isRemainder = true;
        while (isRemainder) {
            secondOperand = random.nextInt(99) + 1;
            if (firstOperand % secondOperand == 0) isRemainder = false;
        }

        result = firstOperand / secondOperand;
    }
}