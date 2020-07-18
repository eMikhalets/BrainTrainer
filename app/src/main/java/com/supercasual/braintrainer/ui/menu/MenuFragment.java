package com.supercasual.braintrainer.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.supercasual.braintrainer.R;
import com.supercasual.braintrainer.databinding.FragmentMenuBinding;
import com.supercasual.braintrainer.utils.Const;

import timber.log.Timber;

public class MenuFragment extends Fragment {

    private MenuViewModel viewModel;
    private FragmentMenuBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        initButtonsListeners();
        Timber.d("Menu fragment created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initButtonsListeners() {
        binding.btnLevel1.setOnClickListener(view -> {
            Timber.d("Selected Level 1");
            Bundle args = new Bundle();
            args.putString(Const.ARGS_GAME_MODE, Const.LEVEL_1);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
        binding.btnLevel2.setOnClickListener(view -> {
            Timber.d("Selected Level 2");
            Bundle args = new Bundle();
            args.putString(Const.ARGS_GAME_MODE, Const.LEVEL_2);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
        binding.btnLevel3.setOnClickListener(view -> {
            Timber.d("Selected Level 3");
            Bundle args = new Bundle();
            args.putString(Const.ARGS_GAME_MODE, Const.LEVEL_3);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
        binding.btnBegin.setOnClickListener(view -> {
            Bundle args = new Bundle();
            String operation = viewModel.getGameMode(
                    binding.spinnerOperation.getSelectedItem().toString());
            Timber.d("Selected operation %s", operation);
            args.putString(Const.ARGS_GAME_MODE, operation);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });

        Timber.d("Menu button's listeners init");
    }
}