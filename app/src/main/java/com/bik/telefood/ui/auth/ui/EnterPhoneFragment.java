package com.bik.telefood.ui.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentEnterPhoneBinding;
import com.bik.telefood.ui.auth.AuthViewModel;

public class EnterPhoneFragment extends Fragment {
    private FragmentEnterPhoneBinding binding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEnterPhoneBinding.inflate(inflater, container, false);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnSendCode.setOnClickListener(v -> sendSmsCode());
        return binding.getRoot();
    }

    private void sendSmsCode() {
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        if (phoneNumber.isEmpty()) {
            binding.ilPhoneNumber.setError(getString(R.string.error_msg_missing_phone_number));
            return;
        }

        //TODO
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.PHONE_NUMBER, phoneNumber);
        NavHostFragment.findNavController(this).navigate(R.id.navigation_enter_confirm_code, bundle);
    }
}