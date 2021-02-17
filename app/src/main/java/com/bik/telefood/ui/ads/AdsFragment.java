package com.bik.telefood.ui.ads;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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

import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentAdsBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.bottomsheet.SuccessDialogFragment;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AdsFragment extends Fragment {
    private static final int ACTION_PICK_IMAGE = 105;
    private FragmentAdsBinding binding;
    private AdsViewModel adsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdsBinding.inflate(inflater, container, false);
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        adsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        binding.llImage.setOnClickListener(v -> pickImage());
        binding.btnSend.setOnClickListener(v -> checkValidation());

        return binding.getRoot();
    }


    private void checkValidation() {
        String productName = binding.etProductName.getText().toString();
        String productPrice = binding.etProductPrice.getText().toString();
        String productDesc = binding.etProductDesc.getText().toString();

        if (productName.isEmpty()) {
            binding.ilProductName.setError(getString(R.string.error_msg_missing_product_name));
            return;
        }

        if (productPrice.isEmpty()) {
            binding.ilProductPrice.setError(getString(R.string.error_msg_missing_price));
            return;
        }

        if (productDesc.isEmpty()) {
            binding.ilProductDesc.setError(getString(R.string.error_msg_missing_desc));
            return;
        }

        SuccessDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "SuccessDialogFragment");
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
//                binding.llImage.setImageURI(uri);
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