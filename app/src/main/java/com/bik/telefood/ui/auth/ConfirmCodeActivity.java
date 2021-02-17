package com.bik.telefood.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.ProgressBarDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityConfirmCodeBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.bottomsheet.ConfirmDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ConfirmCodeActivity extends AppCompatActivity {

    private static final String TAG = ConfirmDialogFragment.class.getName();
    private ActivityConfirmCodeBinding binding;
    private FirebaseAuth mAuth;
    private String phoneNumber;
    private boolean isResetPassword;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ProgressBarDialog progressBarDialog;
    private boolean mVerificationInProgress = false;
    private boolean mCheckingCode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        progressBarDialog = new ProgressBarDialog();

        binding.btnCheckCode.setOnClickListener(v -> {
            if (!mCheckingCode && !mVerificationInProgress) checkCode();
        });

        if (getIntent() != null) {
            Intent intent = getIntent();
            phoneNumber = intent.getStringExtra(AppConstant.PHONE_NUMBER);
            isResetPassword = intent.getBooleanExtra(AppConstant.RESET_PASSWORD, false);
            mVerificationId = intent.getStringExtra(AppConstant.PHONE_VERIFICATION_ID);
            mResendToken = intent.getParcelableExtra(AppConstant.FORCE_RESENDING_TOKEN);
        }

        binding.tvResendCode.setOnClickListener(v -> {
            if (!mVerificationInProgress)
                resendCode(phoneNumber, mResendToken);
        });

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
                    Toast.makeText(ConfirmCodeActivity.this, R.string.invalid_phone_number, Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationInProgress = false;
                if (progressBarDialog.isVisible())
                    progressBarDialog.dismiss();
                Toast.makeText(ConfirmCodeActivity.this, R.string.msg_resend_code_successful, Toast.LENGTH_LONG).show();
            }
        };

    }

    private void resendCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        String phoneNumberWithCode = ApiConstant.COUNTRY_CODE + phoneNumber;

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumberWithCode)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        progressBarDialog.show(getSupportFragmentManager(), "ProgressBarDialog");
    }

    private void checkCode() {
        String code = binding.pinEntryCode.getText().toString();

        if (code.isEmpty()) {
            binding.pinEntryCode.setError(getString(R.string.error_msg_missing_verification_code));
            return;
        }

        if (!mVerificationId.isEmpty())
            verifyPhoneNumberWithCode(mVerificationId, code);
    }


    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        mCheckingCode = true;
        progressBarDialog.show(getSupportFragmentManager(), "ProgressBarDialog");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                    mCheckingCode = false;
                    if (progressBarDialog.isVisible())
                        progressBarDialog.dismiss();
                    if (task.isSuccessful()) {
                        if (isResetPassword) {
                            Intent intent = new Intent(ConfirmCodeActivity.this, ResetPasswordActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(AppConstant.PHONE_NUMBER, phoneNumber);
                            startActivity(intent);
                        } else {
                            setResult(RESULT_OK);
                            finish();
                        }
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            binding.pinEntryCode.setError(getString(R.string.error_msg_mismatched_verification));
                        }
                    }
                });
    }
}