package com.bik.telefood.ui.auth.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MainActivity;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.CommonUtils.spinners.CityAdapter;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentSignUpBinding;
import com.bik.telefood.model.entity.CityItems;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.AuthViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class SignUpFragment extends Fragment {
    private static final int ACTION_PICK_IMAGE = 105;

    private FragmentSignUpBinding binding;
    private AuthViewModel authViewModel;
    private MultipartBody.Part img_profile;
    private long cityModelId;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.ivProfileImage.setOnClickListener(v -> pickImage());
        binding.tvActionLogin.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.navigation_login));
        binding.btnSignUp.setOnClickListener(v -> checkValidation());
        return binding.getRoot();
    }


    private void checkValidation() {
        String fullName = binding.etFullName.getText().toString();
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        String city = binding.spCity.getText().toString();
        String governorate = binding.spGovernorate.getText().toString();
        String password = binding.etPassword.getText().toString();
        boolean isChecked = binding.cbPrivacyPolicy.isChecked();

/*        if (img_profile == null) {
            binding.tvProfileImage.setError(getString(R.string.error_msg_missing_profile_image_required));
            binding.svSignUp.fullScroll(ScrollView.FOCUS_UP);
            return;
        }*/

        if (fullName.isEmpty()) {
            binding.etFullName.setError(getString(R.string.error_msg_missing_name));
            return;
        }

        if (phoneNumber.isEmpty()) {
            binding.etPhoneNumber.setError(getString(R.string.error_msg_missing_phone_number));
            return;
        }

        if (city.isEmpty()) {
            binding.ilCity.setError(getString(R.string.error_msg_missing_city));
            return;
        }

        if (governorate.isEmpty()) {
            binding.ilGovernorate.setError(getString(R.string.error_msg_missing_governorate));
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        if (isChecked) {
            binding.etPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        signUp();
    }


    private void signUp() {
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

/*        // Cache User Type
        SharedPreferences.Editor typeEditor = getActivity().getSharedPreferences(AppConstant.USER_TYPE, Context.MODE_PRIVATE).edit();
        typeEditor.putString(AppConstant.USER_TYPE, userTypeAfterLogin);
        typeEditor.apply();*/


        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initSpinnersValues() {
        List<CityItems> cityItems = new ArrayList<>();
        binding.spCity.setAdapter(new CityAdapter(getContext(), cityItems));
        binding.spCity.setOnItemClickListener((parent, view, position, id) -> {
            CityItems items = (CityItems) parent.getItemAtPosition(position);
    /*        binding.spCity.setText(items.getName(), false);
            cityModelId = items.getId();*/
        });
    }

    private void pickImage() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_PICK_IMAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            // When an Image is picked
            if (requestCode == ACTION_PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
                Uri uri = data.getData();
                File file = FileUtils.getFile(getContext(), uri);
                binding.ivProfileImage.setImageURI(uri);
                RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA));
//                img_profile = MultipartBody.Part.createFormData(ApiConstant.IMAGE, file.getName(), requestPhoto);
            }

        } catch (Exception e) {
            new MessageDialog(getContext(), getString(R.string.title_error_message), e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACTION_PICK_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new MessageDialog(getContext(), getString(R.string.title_request_permission), getString(R.string.msg_request_permission));
        }
    }
}