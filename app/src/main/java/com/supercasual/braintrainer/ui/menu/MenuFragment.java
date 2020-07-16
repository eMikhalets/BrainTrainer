package com.supercasual.braintrainer.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.supercasual.braintrainer.R;
import com.supercasual.braintrainer.databinding.FragmentMenuBinding;
import com.supercasual.braintrainer.utils.Const;

public class MenuFragment extends Fragment {

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
        initButtonsListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initButtonsListeners() {
        binding.btnLevel1.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString(Const.ARGS_LEVEL, Const.LEVEL_1);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
        binding.btnLevel2.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString(Const.ARGS_LEVEL, Const.LEVEL_2);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
        binding.btnLevel3.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString(Const.ARGS_LEVEL, Const.LEVEL_3);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
        binding.btnBegin.setOnClickListener(view -> {
            Bundle args = new Bundle();
            String operation = binding.spinnerOperation.getSelectedItem().toString();
            args.putString(Const.ARGS_LEVEL, operation);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_menuFragment_to_gameFragment, args);
        });
    }
}
