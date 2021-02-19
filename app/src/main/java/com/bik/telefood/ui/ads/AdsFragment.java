package com.bik.telefood.ui.ads;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.CommonUtils.spinners.CategoriesAdapter;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentAdsBinding;
import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.bottomsheet.SuccessDialogFragment;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AdsFragment extends Fragment implements AdsImagesAdapter.OnCancelImageListener {
    private static final int ACTION_PICK_IMAGE = 105;
    private FragmentAdsBinding binding;
    private AdsViewModel adsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private ArrayList<Uri> mArrayUri;
    private MultipartBody.Part[] images;
    private int categoryModelId = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdsBinding.inflate(inflater, container, false);
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        mArrayUri = new ArrayList<>();

        binding.llImage.setOnClickListener(v -> pickImage());
        binding.btnSend.setOnClickListener(v -> checkValidation());

        initCategorySpinnersValues();
        return binding.getRoot();
    }


    private void checkValidation() {
        String productName = binding.etProductName.getText().toString();
        String productPrice = binding.etProductPrice.getText().toString();
        String productDesc = binding.etProductDesc.getText().toString();

        if ((images == null || images.length == 0)) {
            new MessageDialog(getContext(), getString(R.string.title_error_message), getString(R.string.error_msg_missing_ads_images));
            binding.tvAttachImage.setError(getString(R.string.title_note_add_image));
            binding.scrollView.fullScroll(ScrollView.FOCUS_UP);
            return;
        }

        if (productName.isEmpty()) {
            binding.ilProductName.setError(getString(R.string.error_msg_missing_product_name));
            return;
        }

        if (categoryModelId == 0) {
            binding.ilCategory.setError(getString(R.string.error_msg_missing_category));
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

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put(ApiConstant.PRODUCT_NAME, RequestBody.create(productName, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PRODUCT_CATEGORY, RequestBody.create(String.valueOf(categoryModelId), MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PRODUCT_PRICE, RequestBody.create(productPrice, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        params.put(ApiConstant.PRODUCT_DETAILS, RequestBody.create(productDesc, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));

        adsViewModel.addService(params, images, getContext(), getActivity().getSupportFragmentManager()).observe(getViewLifecycleOwner(), mainResponse -> {
            resetView();
            SuccessDialogFragment.newInstance(mainResponse.getMsg()).show(getActivity().getSupportFragmentManager(), "SuccessDialogFragment");
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            images = null;
            mArrayUri.clear();
            // When an Image is picked
            if (requestCode == ACTION_PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data) {

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                    }
                    images = new MultipartBody.Part[mArrayUri.size()];
                    for (int i = 0; i < mArrayUri.size(); i++) {
                        Uri uri = mArrayUri.get(i);
                        File file = FileUtils.getFile(getContext(), uri);
                        RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContext().getContentResolver().getType(uri)));
                        images[i] = MultipartBody.Part.createFormData(ApiConstant.ADD_ADS_IMAGES, file.getName(), requestPhoto);
                    }
                    showAdsImagesList(mArrayUri);
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    File file = FileUtils.getFile(getContext(), uri);
                    RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContext().getContentResolver().getType(uri)));
                    images = new MultipartBody.Part[1];
                    images[0] = MultipartBody.Part.createFormData(ApiConstant.ADD_ADS_IMAGES, file.getName(), requestPhoto);
                    mArrayUri.add(uri);
                    showAdsImagesList(mArrayUri);
                }

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

    private void showAdsImagesList(List<Uri> uriList) {
        binding.tvAttachImage.setVisibility(View.GONE);
        binding.ivAddImage.setVisibility(View.GONE);
        binding.rvAdsImage.setVisibility(View.VISIBLE);
        AdsImagesAdapter adsImagesAdapter = new AdsImagesAdapter(uriList, this);
        binding.rvAdsImage.setAdapter(adsImagesAdapter);
    }

    @Override
    public void onClick() {
        binding.tvAttachImage.setVisibility(View.VISIBLE);
        binding.ivAddImage.setVisibility(View.VISIBLE);
        binding.rvAdsImage.setVisibility(View.GONE);
    }

    private void resetView() {
        binding.tvAttachImage.setVisibility(View.VISIBLE);
        binding.ivAddImage.setVisibility(View.VISIBLE);
        binding.rvAdsImage.setVisibility(View.GONE);
        binding.etProductName.setText("");
        binding.ilCategory.setHint(R.string.label_category);
        binding.spCategory.setText("");
        binding.etProductPrice.setText("");
        binding.etProductDesc.setText("");

        mArrayUri.clear();
        images = null;
        categoryModelId = 0;
    }

    private void initCategorySpinnersValues() {
        //Set Governorate List Values
        categoriesViewModel.getCategoriesListLiveData().observe(getViewLifecycleOwner(), categoryModelList -> {
            binding.spCategory.setAdapter(new CategoriesAdapter(getContext(), categoryModelList));
            binding.spCategory.setOnItemClickListener((parent, view, position, id) -> {
                CategoryModel items = (CategoryModel) parent.getItemAtPosition(position);
                binding.spCategory.setText(items.getTitle(), false);
                categoryModelId = items.getId();
            });
        });
    }
}