package com.supercasual.braintrainer.ui.endgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.supercasual.braintrainer.R;
import com.supercasual.braintrainer.databinding.FragmentEndGameBinding;
import com.supercasual.braintrainer.utils.Const;

public class EndGameFragment extends Fragment {

    private FragmentEndGameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEndGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initButtonsListeners();
        setGameResults();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setGameResults() {
        Bundle args = getArguments();
        if (args != null) {
            binding.textRightAnswers.setText(getString(R.string.end_game_text_right_answers,
                    args.getInt(Const.ARGS_ANSWERS)));
        }
    }

    private void initButtonsListeners() {
        binding.btnReturnToMenu.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });
        binding.btnExitGame.setOnClickListener(view -> {
            requireActivity().finish();
        });
    }
}
