package com.supercasual.braintrainer.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.supercasual.braintrainer.R;
import com.supercasual.braintrainer.databinding.FragmentGameBinding;
import com.supercasual.braintrainer.utils.Const;

class GameFragment extends Fragment {

    private FragmentGameBinding binding;

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
        initButtonsListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initButtonsListeners() {
        binding.btnAnswer1.setOnClickListener(view -> {
            Button button = (Button) view;
            String answer = button.getText().toString();
            checkAnswer(answer);
        });
        binding.btnAnswer2.setOnClickListener(view -> {
            Button button = (Button) view;
            String answer = button.getText().toString();
            checkAnswer(answer);
        });
        binding.btnAnswer3.setOnClickListener(view -> {
            Button button = (Button) view;
            String answer = button.getText().toString();
            checkAnswer(answer);
        });
        binding.btnAnswer4.setOnClickListener(view -> {
            Button button = (Button) view;
            String answer = button.getText().toString();
            checkAnswer(answer);
        });
    }

    private void checkAnswer(String answer) {
    }

    private void endGame() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_gameFragment_to_endGameFragment);
    }
}
