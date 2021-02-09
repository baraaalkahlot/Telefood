package com.bik.telefood.ui.onboarding.boards;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentSecBoardBinding;
import com.bik.telefood.ui.onboarding.OnMovePagerClickListener;
import com.bik.telefood.ui.onboarding.PageViewModel;

public class SecBoardFragment extends Fragment {

    private static final int ARG_THIRD_PAGER_NUMBER = 2;
    private static final String ARG_SECTION_NUMBER = "2";
    private FragmentSecBoardBinding binding;
    private OnMovePagerClickListener mListener;

    private PageViewModel pageViewModel;

    public static SecBoardFragment newInstance(int index) {
        SecBoardFragment fragment = new SecBoardFragment();
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
        binding = FragmentSecBoardBinding.inflate(inflater, container, false);

        binding.ivForward.setOnClickListener(v -> mListener.onClick(ARG_THIRD_PAGER_NUMBER));

        binding.tvSkip.setOnClickListener(v -> mListener.onClick(ARG_THIRD_PAGER_NUMBER));

        pageViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
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