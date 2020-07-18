package com.supercasual.braintrainer.ui.menu;

import androidx.lifecycle.ViewModel;

import com.supercasual.braintrainer.utils.Const;

public class MenuViewModel extends ViewModel {

    public MenuViewModel() {
    }

    public String getGameMode(String parameter) {
        switch (parameter) {
            case "Сложение":
                return Const.ADDITION;
            case "Вычитание":
                return Const.SUBTRACTION;
            case "Умножение":
                return Const.MULTIPLICATION;
            case "Деление":
                return Const.DIVISION;
        }
        return null;
    }
}
