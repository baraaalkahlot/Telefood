package com.bik.telefood.ui.onboarding.boards;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentFirstBoardingBinding;
import com.bik.telefood.ui.onboarding.OnMovePagerClickListener;
import com.bik.telefood.ui.onboarding.PageViewModel;


public class FirstBoardFragment extends Fragment {

    private static final int ARG_SEC_PAGER_NUMBER = 1;
    private static final String ARG_SECTION_NUMBER = "1";
    private static final int ARG_THIRD_PAGER_NUMBER = 2;
    private FragmentFirstBoardingBinding binding;
    private OnMovePagerClickListener mListener;

    private PageViewModel pageViewModel;

    public static FirstBoardFragment newInstance(int index) {
        FirstBoardFragment fragment = new FirstBoardFragment();
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
        binding = FragmentFirstBoardingBinding.inflate(inflater, container, false);
        pageViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        binding.ivForward.setOnClickListener(v -> mListener.onClick(ARG_SEC_PAGER_NUMBER));
        binding.tvSkip.setOnClickListener(v -> mListener.onClick(ARG_THIRD_PAGER_NUMBER));

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMovePagerClickListener) {
            mListener = (OnMovePagerClickListener) context;
        }
    }

}