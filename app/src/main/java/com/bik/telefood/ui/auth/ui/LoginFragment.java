package com.bik.telefood.ui.auth.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.MainActivity;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentLoginBinding;
import com.bik.telefood.ui.auth.AuthViewModel;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AuthViewModel authViewModel;
    private String userTypeAfterLogin;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        binding.tvActionSignUp.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.navigation_sign_up));
        binding.tvActionResetPassword.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.navigation_enter_phone));
        binding.btnLogin.setOnClickListener(v -> checkValidation());
        return binding.getRoot();
    }

    private void checkValidation() {
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        String password = binding.etPassword.getText().toString();

        if (phoneNumber.isEmpty()) {
            binding.etPhoneNumber.setError(getString(R.string.error_msg_missing_phone_number));
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        login();
    }

    private void login() {

        // Cache Token
/*
        SharedPreferences.Editor preferencesToken = getActivity().getSharedPreferences(ApiConstant.AUTHORIZATION, Context.MODE_PRIVATE).edit();
        preferencesToken.putString(ApiConstant.AUTHORIZATION, signup.getItems().getAccessToken());
        preferencesToken.apply();
*/

        // Cache Login Status
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(AppConstant.USER_STATUS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(AppConstant.USER_STATUS, true);
        editor.apply();

        // Cache User Type
        SharedPreferences.Editor typeEditor = getActivity().getSharedPreferences(AppConstant.USER_TYPE, Context.MODE_PRIVATE).edit();
        typeEditor.putString(AppConstant.USER_TYPE, userTypeAfterLogin);
        typeEditor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}