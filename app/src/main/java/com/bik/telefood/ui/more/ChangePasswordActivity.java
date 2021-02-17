package com.bik.telefood.ui.more;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.ConfirmDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityChangePasswordBinding;
import com.bik.telefood.model.network.ApiConstant;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity implements ConfirmDialog.OnPositiveButtonListener {

    private ActivityChangePasswordBinding binding;
    private MoreViewModel moreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        moreViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        String oldPassword = binding.etOldPassword.getText().toString();
        String newPassword = binding.etPassword.getText().toString();
        String confirmNewPassword = binding.etConfirmPassword.getText().toString();

        if (oldPassword.isEmpty()) {
            binding.ilOldPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        if (newPassword.isEmpty()) {
            binding.ilPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        if (confirmNewPassword.isEmpty()) {
            binding.ilConfirmPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstant.OLD_PASSWORD, oldPassword);
        params.put(ApiConstant.PASSWORD, newPassword);
        params.put(ApiConstant.PASSWORD_CONFIRMATION, confirmNewPassword);

        moreViewModel.changePassword(params, this, getSupportFragmentManager()).observe(this, mainResponse -> {
            new ConfirmDialog(this, getString(R.string.message_request_success), mainResponse.getMsg(), this);
        });
    }

    @Override
    public void onClick() {
        setResult(RESULT_OK);
        finish();
    }
}