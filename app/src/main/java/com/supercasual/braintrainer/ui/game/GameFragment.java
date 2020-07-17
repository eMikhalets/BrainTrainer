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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

public class GameFragment extends Fragment {

    private List<Button> buttonList;
    private GameViewModel viewModel;
    private FragmentGameBinding binding;
    private CountDownTimer gameTimer;

    private String gameMode;

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

        prepareGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        buttonList = null;
    }

    private void prepareGame() {
        setGameMode();

        // Init text answer and mistakes
        binding.textAnswers.setText(getString(R.string.game_text_answers, 0, 0));
        // Init text timer
        String seconds = "";
        if (Const.START_TIMER > 10000) seconds = String.valueOf(Const.START_TIMER / 1000);
        else seconds = "0" + Const.START_TIMER / 1000;
        binding.textTimer.setText(getString(R.string.game_text_timer, "00", seconds));

        // Init buttons
        buttonList = new ArrayList<>();
        buttonList.add(binding.btnAnswer1);
        buttonList.add(binding.btnAnswer2);
        buttonList.add(binding.btnAnswer3);
        buttonList.add(binding.btnAnswer4);

        // Set click listeners
        for (Button button : buttonList) {
            button.setOnClickListener(view -> checkAnswer(button.getText().toString()));
        }

        // Init and start prepare game timer
        Timber.d("Prepare timer started.");
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                binding.textPrepareGame.setText(String.valueOf(l / 1000));
                Timber.d("Prepare timer: %d sec left.", l / 1000);
            }

            @Override
            public void onFinish() {
                Timber.d("Prepare timer finished.");
                startGame();
            }
        }.start();

        // Init game timer
        gameTimer = new CountDownTimer(Const.START_TIMER, 1000) {
            @Override
            public void onTick(long l) {
                // Update Game timer text
                String seconds = "";
                if (l > 10000) seconds = String.valueOf(l / 1000);
                else seconds = "0" + l / 1000;
                binding.textTimer.setText(getString(R.string.game_text_timer,
                        "00", seconds));
                Timber.d("Game timer: %s sec left", seconds);
            }

            @Override
            public void onFinish() {
                Timber.d("Prepare timer finished.");
                endGame();
            }
        };
    }

    private void setGameMode() {
        Bundle args = getArguments();
        if (args != null) {
            gameMode = args.getString(Const.ARGS_GAME_MODE, Const.LEVEL_1);
        }
    }

    private void startGame() {
        binding.textPrepareGame.setVisibility(View.INVISIBLE);
        gameTimer.start();
        updateExample();
        binding.textExample.setVisibility(View.VISIBLE);
        Timber.d("Game timer started.");
    }

    private void updateExample() {
        generateExample();
        binding.textExample.setText(getString(R.string.game_text_example,
                String.valueOf(viewModel.getFirstOperand()),
                viewModel.getOperation(),
                String.valueOf(viewModel.getSecondOperand())));
        generateAnswers();
    }

    private void endGame() {
        Timber.d("Game is ended");
        Bundle args = new Bundle();
        args.putInt(Const.ARGS_ANSWERS, viewModel.getAnswers());
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_gameFragment_to_endGameFragment, args);
    }

    private void checkAnswer(String answer) {
        if (Integer.parseInt(answer) == viewModel.getResult()) {
            viewModel.increaseAnswers();
        } else {
            viewModel.increaseMistakes();
        }

        binding.textAnswers.setText(getString(R.string.game_text_answers,
                viewModel.getAnswers(), viewModel.getMistakes()));
        updateExample();
    }

    private void generateExample() {
        switch (gameMode) {
            case Const.LEVEL_1:
                Timber.d("Selected mode is LEVEL 1");
                viewModel.generateLevel(0);
                break;
            case Const.LEVEL_2:
                Timber.d("Selected mode is LEVEL 2");
                viewModel.generateLevel(1);
                break;
            case Const.LEVEL_3:
                Timber.d("Selected mode is LEVEL 3");
                viewModel.generateLevel(3);
                break;
            case Const.ADDITION:
                Timber.d("Selected mode is ADDITION");
                viewModel.generateOperation(0);
                break;
            case Const.SUBTRACTION:
                Timber.d("Selected mode is SUBTRACTION");
                viewModel.generateOperation(1);
                break;
            case Const.MULTIPLICATION:
                Timber.d("Selected mode is MULTIPLICATION");
                viewModel.generateOperation(2);
                break;
            case Const.DIVISION:
                Timber.d("Selected mode is DIVISION");
                viewModel.generateOperation(3);
                break;
        }
    }

    private void generateAnswers() {
        Random random = new Random();
        List<Integer> answers = new ArrayList<>();
        answers.add(viewModel.getResult());
        for (int i = 1; i < 4; i++) {
            int num = 0;
            boolean isContains = true;
            while (isContains) {
                num = random.nextInt(150);
                isContains = answers.contains(num);
            }
            answers.add(num);
        }
        Collections.shuffle(answers);
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setText(String.valueOf(answers.get(i)));
        }
    }
}
