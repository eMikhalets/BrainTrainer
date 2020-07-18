package com.supercasual.braintrainer.ui.game;

import android.animation.ObjectAnimator;
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

import timber.log.Timber;

public class GameFragment extends Fragment {

    private CountDownTimer gameTimer;
    private CountDownTimer prepareTimer;
    private List<Button> buttonList;
    private GameViewModel viewModel;
    private FragmentGameBinding binding;

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
        setGameViewsPosition();
        prepareGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        prepareTimer.cancel();
        gameTimer.cancel();
        Timber.d("Timer canceled");
        binding = null;
        buttonList = null;
    }

    private void setGameViewsPosition() {
        binding.textAnswers.setTranslationY(-100f);
        binding.textTimer.setTranslationY(-100f);
        binding.btnAnswer1.setTranslationX(-800f);
        binding.btnAnswer2.setTranslationX(800f);
        binding.btnAnswer3.setTranslationX(-500f);
        binding.btnAnswer4.setTranslationX(500f);
    }

    private void prepareGame() {
        setGameMode();

        // Init text answer and mistakes
        binding.textAnswers.setText(getString(R.string.game_text_answers, 0, 0));
        // Init text timer
        String seconds;
        if (Const.START_TIMER > 10000) seconds = String.valueOf(Const.START_TIMER / 1000);
        else seconds = "0" + Const.START_TIMER / 1000;
        binding.textTimer.setText(getString(R.string.game_text_timer, "00", seconds));

        // Init buttons
        buttonList = new ArrayList<>();
        buttonList.add(binding.btnAnswer1);
        buttonList.add(binding.btnAnswer2);
        buttonList.add(binding.btnAnswer3);
        buttonList.add(binding.btnAnswer4);

        for (Button button : buttonList) button.setEnabled(false);

        // Set click listeners
        for (Button button : buttonList) {
            button.setOnClickListener(view -> checkAnswer(button.getText().toString()));
        }

        // Init and start prepare game timer
        Timber.d("Prepare timer started.");
        prepareTimer = new CountDownTimer(3000, 500) {
            @Override
            public void onTick(long l) {
                long leftRemain = (l / 100) % 10;
                if (leftRemain > 7) {
                    long timeLeft = l / 1000 + 1;
                    binding.textPrepareGame.setText(String.valueOf(timeLeft));
                    Timber.d("Prepare timer: %d sec left.", timeLeft);
                }
            }

            @Override
            public void onFinish() {
                Timber.d("Prepare timer finished.");
                startGame();
            }
        };
        prepareTimer.start();

        // Init game timer
        gameTimer = new CountDownTimer(Const.START_TIMER, 500) {
            @Override
            public void onTick(long l) {
                long leftRemain = (l / 100) % 10;
                if (leftRemain > 7) {
                    String seconds;
                    long timeLeft = l / 1000 + 1;
                    if (timeLeft >= 10) seconds = String.valueOf(timeLeft);
                    else seconds = "0" + timeLeft;
                    binding.textTimer.setText(getString(R.string.game_text_timer,
                            "00", seconds));
                    Timber.d("Game timer: %s sec left", seconds);
                }
            }

            @Override
            public void onFinish() {
                Timber.d("Game timer finished.");
                endGame();
            }
        };
    }

    private void setGameMode() {
        Bundle args = getArguments();
        if (args != null) {
            gameMode = args.getString(Const.ARGS_GAME_MODE, Const.LEVEL_1);
            Timber.d("Game mode is %s", gameMode);
            viewModel.setGameMode(gameMode);
        }
    }

    private void startGame() {
        animateGameViews();
        binding.textPrepareGame.animate().alpha(0).setDuration(300).start();
        gameTimer.start();
        updateExample();
        binding.textExample.animate().alpha(1).setDuration(300).start();
        Timber.d("Game timer started.");
    }

    private void animateGameViews() {
        binding.textAnswers.animate().translationY(0f).start();
        binding.textTimer.animate().translationY(0f).start();
        binding.btnAnswer1.animate().translationX(0f).start();
        binding.btnAnswer2.animate().translationX(0f).start();
        binding.btnAnswer3.animate().translationX(0f).start();
        binding.btnAnswer4.animate().translationX(0f).start();
    }

    private void updateExample() {
        viewModel.generateExample();
        binding.textExample.setText(getString(R.string.game_text_example,
                String.valueOf(viewModel.getFirstOperand()),
                viewModel.getOperation(),
                String.valueOf(viewModel.getSecondOperand())));

        // Set text to buttons
        Collections.shuffle(buttonList);
        for (int i = 0; i < buttonList.size() - 1; i++) {
            buttonList.get(i).setText(String.valueOf(viewModel.getFakeResults().get(i)));
        }
        buttonList.get(3).setText(String.valueOf(viewModel.getResult()));
        for (Button button : buttonList) button.setEnabled(true);
    }

    private void endGame() {
        Timber.d("Game is ended");
        Bundle args = new Bundle();
        args.putInt(Const.ARGS_ANSWERS, viewModel.getAnswers());
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_gameFragment_to_endGameFragment, args);
    }

    private void checkAnswer(String answer) {
        for (Button button : buttonList) button.setEnabled(false);

        if (Integer.parseInt(answer) == viewModel.getResult()) {
            viewModel.increaseAnswers();
        } else {
            viewModel.increaseMistakes();
        }

        binding.textAnswers.setText(getString(R.string.game_text_answers,
                viewModel.getAnswers(), viewModel.getMistakes()));
        updateExample();
    }
}
