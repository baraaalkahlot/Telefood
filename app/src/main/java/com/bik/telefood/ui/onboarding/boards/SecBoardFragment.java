package com.bik.telefood.ui.onboarding.boards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.ui.onboarding.PageViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecBoardFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "2";

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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sec_board, container, false);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}