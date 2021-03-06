package com.bik.telefood.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.ProgressBarDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityEnterPhoneBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterPhoneActivity extends AppCompatActivity {
    private ActivityEnterPhoneBinding binding;
    private AuthViewModel authViewModel;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber;
    private boolean mVerificationInProgress = false;
    private ProgressBarDialog progressBarDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        progressBarDialog = new ProgressBarDialog();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                mVerificationInProgress = false;
                if (progressBarDialog.isVisible())
                    progressBarDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                mVerificationInProgress = false;
                if (progressBarDialog.isVisible())
                    progressBarDialog.dismiss();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    binding.ilPhoneNumber.setError(getString(R.string.invalid_phone_number));
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Intent intent = new Intent(EnterPhoneActivity.this, ConfirmCodeActivity.class);
                intent.putExtra(AppConstant.PHONE_NUMBER, phoneNumber);
                intent.putExtra(AppConstant.RESET_PASSWORD, true);
                intent.putExtra(AppConstant.PHONE_VERIFICATION_ID, s);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                mVerificationInProgress = false;
                if (progressBarDialog.isVisible())
                    progressBarDialog.dismiss();
            }
        };

        binding.btnSendCode.setOnClickListener(v -> {
            if (!mVerificationInProgress)
                sendSmsCode();
        });
    }

    private void sendSmsCode() {
        phoneNumber = binding.etPhoneNumber.getText().toString();
        if (phoneNumber.isEmpty()) {
            binding.ilPhoneNumber.setError(getString(R.string.error_msg_missing_phone_number));
            return;
        }
        startPhoneNumberVerification(phoneNumber);
    }


    private void startPhoneNumberVerification(String phoneNumbers) {
        phoneNumbers = ApiConstant.COUNTRY_CODE + phoneNumbers;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumbers)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;
        progressBarDialog.show(getSupportFragmentManager(), "ProgressBarDialog");
    }

}