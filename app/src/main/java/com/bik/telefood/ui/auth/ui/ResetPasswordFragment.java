package com.bik.telefood.ui.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentResetPasswordBinding;

import org.jetbrains.annotations.NotNull;

public class ResetPasswordFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentResetPasswordBinding binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        binding.btnSave.setOnClickListener(v -> {
            String password = binding.etPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();

            if (password.isEmpty()) {
                binding.ilPassword.setError(getString(R.string.error_msg_missing_password));
                return;
            }
            if (confirmPassword.isEmpty()) {
                binding.ilConfirmPassword.setError(getString(R.string.error_msg_missing_password));
                return;
            }

            //TODO
            NavHostFragment.findNavController(this).navigate(R.id.navigation_login);
        });
        return binding.getRoot();
    }
}