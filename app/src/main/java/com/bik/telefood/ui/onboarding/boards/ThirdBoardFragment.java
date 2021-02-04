package com.bik.telefood.ui.onboarding.boards;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentThirdBoardBinding;
import com.bik.telefood.ui.onboarding.PageViewModel;
import com.bik.telefood.ui.onboarding.UserTypeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdBoardFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "3";

    private PageViewModel pageViewModel;

    public static ThirdBoardFragment newInstance(int index) {
        ThirdBoardFragment fragment = new ThirdBoardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentThirdBoardBinding binding = FragmentThirdBoardBinding.inflate(inflater, container, false);
        binding.btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserTypeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        });
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return binding.getRoot();
    }
}