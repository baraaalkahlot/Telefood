package com.bik.telefood.ui.more;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.CommonUtils.spinners.CityAdapter;
import com.bik.telefood.CommonUtils.spinners.GovernorateAdapter;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityEditUserInfoBinding;
import com.bik.telefood.model.entity.general.City;
import com.bik.telefood.model.entity.general.Governorate;
import com.bik.telefood.model.entity.general.UserModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.login.LoginViewModel;
import com.bik.telefood.ui.common.viewmodel.CitiesViewModel;
import com.bik.telefood.ui.common.viewmodel.GovernorateViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditUserInfoActivity extends AppCompatActivity {
    private static final int ACTION_PICK_IMAGE = 105;

    private ActivityEditUserInfoBinding binding;
    private MoreViewModel moreViewModel;
    private LoginViewModel loginViewModel;
    private GovernorateViewModel governorateViewModel;
    private CitiesViewModel citiesViewModel;

    private MultipartBody.Part img_profile;
    private int governorateModelId;
    private int cityModelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        moreViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        governorateViewModel = new ViewModelProvider(this).get(GovernorateViewModel.class);
        citiesViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);

        setUserInfo();
        binding.ivProfileImage.setOnClickListener(v -> pickImage());

        binding.btnSave.setOnClickListener(v -> updateProfile());
    }

    private void setUserInfo() {
        loginViewModel.getUserModel().observe(this, userModel -> {

            Picasso.get()
                    .load(Uri.parse(userModel.getAvatar()))
                    .error(R.drawable.ic_take_picture)
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(binding.ivProfileImage);

            binding.etFullName.setText(userModel.getName());
            binding.etPhoneNumber.setText(userModel.getPhone());

            governorateModelId = Integer.parseInt(userModel.getGovernorateId());
            cityModelId = Integer.parseInt(userModel.getCityId());
            initGovernorateSpinnersValues();
        });
    }

    private void updateProfile() {
        String fullName = binding.etFullName.getText().toString();
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        String city = binding.spCity.getText().toString();
        String governorate = binding.spGovernorate.getText().toString();

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


        HashMap<String, RequestBody> params = new HashMap<>();
        params.put(ApiConstant.NAME, RequestBody.create(fullName, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PHONE, RequestBody.create(phoneNumber, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.GOVERNORATE_ID, RequestBody.create(String.valueOf(governorateModelId), MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.CITY_ID, RequestBody.create(String.valueOf(cityModelId), MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));

        moreViewModel.updateProfile(params, img_profile, this, getSupportFragmentManager()).observe(this, updateProfileResponse -> {
            UserModel userModel = updateProfileResponse.getUser();
            loginViewModel.updateUserSection(userModel.getAvatar(), userModel.getName(), userModel.getPhone(), userModel.getGovernorateId(), userModel.getCityId());
            setResult(RESULT_OK);
            finish();
        });
    }


    private void initGovernorateSpinnersValues() {
        //Set Governorate List Values
        governorateViewModel.getGovernorateList(this, getSupportFragmentManager()).observe(this, governoratesResponse -> {
            List<Governorate> governorateList = governoratesResponse.getGovernorates();
            binding.spGovernorate.setAdapter(new GovernorateAdapter(this, governorateList));

            for (Governorate governorate : governorateList) {
                if (governorate.getId() == governorateModelId) {
                    binding.spGovernorate.setText(governorate.getTitle(), false);
                    break;
                }
            }
            getCitiesSpinnersValuesById(governorateModelId, cityModelId);

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
            binding.spCity.setAdapter(new CityAdapter(this, cityList));

            binding.spCity.setOnItemClickListener((parent, view, position, id) -> {
                City items = (City) parent.getItemAtPosition(position);
                binding.spCity.setText(items.getTitle(), false);
                cityModelId = items.getId();
            });
        });
    }

    private void getCitiesSpinnersValuesById(int governorate, int cityId) {
        //Set City List Values
        citiesViewModel.getCityList(governorate, this, getSupportFragmentManager()).observe(this, citiesResponse -> {
            List<City> cityList = citiesResponse.getCities();
            binding.spCity.setAdapter(new CityAdapter(this, cityList));

            for (City city : cityList) {
                if (city.getId() == cityId) {
                    binding.spCity.setText(city.getTitle(), false);
                    break;
                }
            }
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