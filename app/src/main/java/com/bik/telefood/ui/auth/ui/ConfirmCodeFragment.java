package com.bik.telefood.ui.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentConfirmCodeBinding;

import org.jetbrains.annotations.NotNull;

public class ConfirmCodeFragment extends Fragment {

    private FragmentConfirmCodeBinding binding;
    private String phoneNumber = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = savedInstanceState.getString(AppConstant.PHONE_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfirmCodeBinding.inflate(inflater, container, false);
        binding.btnCheckCode.setOnClickListener(v -> checkCode());
        return binding.getRoot();
    }

    private void checkCode() {
        String code = binding.pinEntryCode.getText().toString();

        if (code.isEmpty()) {
            binding.pinEntryCode.setError(getString(R.string.error_msg_missing_verification_code));
            return;
        }
        if (phoneNumber.isEmpty()) {
            return;
        }

        //TODO Check Code
        NavHostFragment.findNavController(this).navigate(R.id.navigation_reset_password);
    }
}