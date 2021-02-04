package com.bik.telefood.ui.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentConfirmCodeBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmCodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfirmCodeFragment() {
        // Required empty public constructor
    }

    public static ConfirmCodeFragment newInstance(String param1, String param2) {
        ConfirmCodeFragment fragment = new ConfirmCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentConfirmCodeBinding binding = FragmentConfirmCodeBinding.inflate(inflater, container, false);
        binding.btnCheckCode.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.navigation_reset_password));
        return binding.getRoot();
    }
}