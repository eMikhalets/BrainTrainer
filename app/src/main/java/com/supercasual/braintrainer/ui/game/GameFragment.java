package com.supercasual.braintrainer.ui.game;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.supercasual.braintrainer.R;
import com.supercasual.braintrainer.databinding.FragmentGameBinding;
import com.supercasual.braintrainer.utils.Const;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class GameFragment extends Fragment {

    private List<Button> buttonList;
    private GameViewModel viewModel;
    private FragmentGameBinding binding;
    private CountDownTimer gameTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        Timber.d("Game fragment created");
        initButtons();
        initButtonsListeners();
        initGameInfo();
        initGameTimer();
        gameTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        buttonList = null;
    }

    private void getArgs() {
        Bundle args = getArguments();
        if (args != null) {
            String gameMode = args.getString(Const.ARGS_GAME_MODE);
            switch (gameMode) {
                case Const.LEVEL_1:
                    Timber.d("Selected mode is LEVEL 1");
                    break;
                case Const.LEVEL_2:
                    Timber.d("Selected mode is LEVEL 2");
                    break;
                case Const.LEVEL_3:
                    Timber.d("Selected mode is LEVEL 3");
                    break;
                case Const.ADDITION:
                    Timber.d("Selected mode is ADDITION");
                    break;
                case Const.SUBTRACTION:
                    Timber.d("Selected mode is SUBTRACTION");
                    break;
                case Const.MULTIPLICATION:
                    Timber.d("Selected mode is MULTIPLICATION");
                    break;
                case Const.DIVISION:
                    Timber.d("Selected mode is DIVISION");
                    break;
            }
        }
    }

    private void initButtons() {
        buttonList = new ArrayList<>(4);
        buttonList.add(binding.btnAnswer1);
        buttonList.add(binding.btnAnswer2);
        buttonList.add(binding.btnAnswer3);
        buttonList.add(binding.btnAnswer4);
        Timber.d("Button's list init");
    }

    private void initButtonsListeners() {
        for (Button button : buttonList) {
            button.setOnClickListener(view -> {
                String answer = button.getText().toString();
                checkAnswer(answer);
            });
        }
        Timber.d("Button's listeners init");
    }

    private void initGameInfo() {
        int startTime = Const.START_TIMER / 1000;
        if (startTime > 10) {
            binding.textTimer.setText(getString(R.string.game_text_timer,
                    "00", String.valueOf(startTime)));
        } else {
            binding.textTimer.setText(getString(R.string.game_text_timer,
                    "00", "0" + startTime));
        }
        binding.textAnswers.setText(getString(R.string.game_text_answers, 0, 0));
        binding.textExample.setText("0");
    }

    private void initGameTimer() {
        gameTimer = new CountDownTimer(Const.START_TIMER, 1000) {
            @Override
            public void onTick(long l) {
                long secondsLeft = l / 1000;
                Timber.d("Time left %s", secondsLeft);
                if (l / 1000 > 10) {
                    binding.textTimer.setText(getString(R.string.game_text_timer,
                            "00", String.valueOf(secondsLeft)));
                } else {
                    binding.textTimer.setText(getString(R.string.game_text_timer,
                            "00", "0" + secondsLeft));
                }
            }

            @Override
            public void onFinish() {
                Timber.d("Timer finish");
                endGame();
            }
        };
        Timber.d("Timer init");
    }

    private void checkAnswer(String answer) {
    }

    private void endGame() {
        Timber.d("Game is ended");
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_gameFragment_to_endGameFragment);
    }
}
