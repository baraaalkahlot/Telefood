package com.bik.telefood.ui.auth;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.CommonUtils.ProgressBarDialog;
import com.bik.telefood.CommonUtils.spinners.CityAdapter;
import com.bik.telefood.CommonUtils.spinners.GovernorateAdapter;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivitySignUpBinding;
import com.bik.telefood.model.entity.general.City;
import com.bik.telefood.model.entity.general.Governorate;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.login.LoginActivity;
import com.bik.telefood.ui.common.viewmodel.CitiesViewModel;
import com.bik.telefood.ui.common.viewmodel.GovernorateViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignUpActivity extends AppCompatActivity {
    private static final int ACTION_PICK_IMAGE = 105;
    private static final int ACTION_VERIFY_NUMBER = 101;

    private ActivitySignUpBinding binding;
    private GovernorateViewModel governorateViewModel;
    private CitiesViewModel citiesViewModel;
    private AuthViewModel authViewModel;
    private FirebaseAuth mAuth;
    private MultipartBody.Part img_profile;
    private int governorateModelId;
    private int cityModelId;
    private String userRole;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private HashMap<String, RequestBody> params;
    private ProgressBarDialog progressBarDialog;
    private boolean mVerificationInProgress = false;
    private String phone;
    private String phoneNumberWithCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        governorateViewModel = new ViewModelProvider(this).get(GovernorateViewModel.class);
        citiesViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        initGovernorateSpinnersValues();
        progressBarDialog = new ProgressBarDialog();

        if (getIntent() != null) {
            userRole = getIntent().getStringExtra(ApiConstant.ROLE);
        }
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
                Intent intent = new Intent(SignUpActivity.this, ConfirmCodeActivity.class);
                intent.putExtra(AppConstant.PHONE_NUMBER, phone);
                intent.putExtra(AppConstant.PHONE_VERIFICATION_ID, s);
                intent.putExtra(AppConstant.FORCE_RESENDING_TOKEN, forceResendingToken);
                startActivityForResult(intent, ACTION_VERIFY_NUMBER);
                mVerificationInProgress = false;
                if (progressBarDialog.isVisible())
                    progressBarDialog.dismiss();
            }
        };

        binding.ivProfileImage.setOnClickListener(v -> pickImage());
        binding.tvActionLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        binding.btnSignUp.setOnClickListener(v -> checkValidation());

    }


    private void checkValidation() {
        if (mVerificationInProgress) return;

        String fullName = binding.etFullName.getText().toString();
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        String city = binding.spCity.getText().toString();
        String governorate = binding.spGovernorate.getText().toString();
        String password = binding.etPassword.getText().toString();
        boolean isChecked = binding.cbPrivacyPolicy.isChecked();

        if (img_profile == null && userRole.equals(ApiConstant.ROLE_VENDOR)) {
            binding.tvProfileImage.setError(getString(R.string.error_msg_missing_profile_image_required));
            binding.svSignUp.fullScroll(ScrollView.FOCUS_UP);
            return;
        }

        if (fullName.isEmpty()) {
            binding.etFullName.setError(getString(R.string.error_msg_missing_name));
            return;
        }

        if (phoneNumber.isEmpty()) {
            binding.etPhoneNumber.setError(getString(R.string.error_msg_missing_phone_number));
            return;
        }

        if (governorate.isEmpty()) {
            binding.ilGovernorate.setError(getString(R.string.error_msg_missing_governorate));
            return;
        }

        if (city.isEmpty()) {
            binding.ilCity.setError(getString(R.string.error_msg_missing_city));
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        if (!isChecked) {
            binding.cbPrivacyPolicy.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        params = new HashMap<>();
        params.put(ApiConstant.NAME, RequestBody.create(fullName, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PHONE, RequestBody.create(phoneNumber, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PASSWORD, RequestBody.create(password, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PASSWORD_CONFIRMATION, RequestBody.create(password, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.ROLE, RequestBody.create(userRole, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.GOVERNORATE_ID, RequestBody.create(String.valueOf(governorateModelId), MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.CITY_ID, RequestBody.create(String.valueOf(cityModelId), MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));

        phone = phoneNumber;
        startPhoneNumberVerification(phoneNumber);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        phoneNumberWithCode = ApiConstant.COUNTRY_CODE + phoneNumber;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumberWithCode)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;
        progressBarDialog.show(getSupportFragmentManager(), "ProgressBarDialog");
    }

    private void signUp() {
        authViewModel.register(params, img_profile, this, getSupportFragmentManager()).observe(this, mainResponse -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void initGovernorateSpinnersValues() {
        //Set Governorate List Values
        governorateViewModel.getGovernorateList(this, getSupportFragmentManager()).observe(this, governoratesResponse -> {
            List<Governorate> governorateList = governoratesResponse.getGovernorates();
            binding.spGovernorate.setAdapter(new GovernorateAdapter(this, governorateList));
            binding.spGovernorate.setOnItemClickListener((parent, view, position, id) -> {
                Governorate items = (Governorate) parent.getItemAtPosition(position);
                binding.spGovernorate.setText(items.getTitle(), false);
                governorateModelId = items.getId();
                getCitiesSpinnersValuesById(governorateModelId);
                binding.ilCity.setHint(R.string.label_city);
                binding.spCity.setText("");
            });
        });
    }

    private void getCitiesSpinnersValuesById(int governorate) {
        //Set City List Values
        citiesViewModel.getCityList(governorate, this, getSupportFragmentManager()).observe(this, citiesResponse -> {
            List<City> cityList = citiesResponse.getCities();
            CityAdapter cityAdapter = new CityAdapter(this, cityList);
            binding.spCity.setAdapter(cityAdapter);
            binding.spCity.setOnItemClickListener((parent, view, position, id) -> {
                City items = (City) parent.getItemAtPosition(position);
                binding.spCity.setText(items.getTitle(), false);
                cityModelId = items.getId();
            });

            binding.spCity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cityAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        });

    }


    private void pickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_PICK_IMAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == ACTION_PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
                Uri uri = data.getData();
                File file = FileUtils.getFile(this, uri);
                binding.ivProfileImage.setImageURI(uri);
                RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA));
                img_profile = MultipartBody.Part.createFormData(ApiConstant.Profile_IMAGE_AVATAR, file.getName(), requestPhoto);
            } else if (requestCode == ACTION_VERIFY_NUMBER && resultCode == Activity.RESULT_OK) {
                signUp();
            }

        } catch (Exception e) {
            new MessageDialog(this, getString(R.string.title_error_message), e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACTION_PICK_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new MessageDialog(this, getString(R.string.title_request_permission), getString(R.string.msg_request_permission));
        }
    }

}