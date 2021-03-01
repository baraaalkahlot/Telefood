package com.bik.telefood.ui.more.ads;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.LocalConstant;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.CommonUtils.spinners.CategoriesAdapter;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentAdsBinding;
import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.ads.AdsImagesAdapter;
import com.bik.telefood.ui.ads.AdsViewModel;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditAdsActivity extends AppCompatActivity implements AdsImagesAdapter.OnCancelImageListener {

    private static final int ACTION_PICK_IMAGE = 105;
    private FragmentAdsBinding binding;
    private AdsViewModel adsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private ServicesViewModel servicesViewModel;
    private ArrayList<Uri> mArrayUri;
    private MultipartBody.Part[] images;
    private int categoryModelId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        binding = FragmentAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = getIntent().getIntExtra(AppConstant.PRODUCT_ID, 0);
        setInitValue(id);
        mArrayUri = new ArrayList<>();

        binding.llImage.setOnClickListener(v -> pickImage());
        binding.btnSend.setOnClickListener(v -> checkValidation());

        initCategorySpinnersValues();
    }


    private void setInitValue(int id) {
        servicesViewModel.getSingleServicesList(id, this, getSupportFragmentManager(), true).observe(this, singleServiceResponse -> {
            SingleServiceModel singleServiceModel = singleServiceResponse.getService();
            String product_name = singleServiceModel.getName();
            String product_category_id = singleServiceModel.getCategory();
            String product_price = singleServiceModel.getPrice();
            String product_desc = singleServiceModel.getDescription();

            binding.etProductName.setText(product_name);
            binding.etProductPrice.setText(product_price);
            binding.etProductDesc.setText(product_desc);

            LocalConstant localConstant = new LocalConstant(this, this, this);
            localConstant.getCategoryNameById(Integer.parseInt(product_category_id), getSupportFragmentManager()).observe(this, s -> binding.spCategory.setText(s));

            List<Uri> uriList = new ArrayList<>();

            for (String s : singleServiceModel.getImages()) {
                uriList.add(Uri.parse(s));
            }

            if (!uriList.isEmpty()) {
                showAdsImagesList(uriList);
            }
        });
    }

    private void checkValidation() {
        String productName = binding.etProductName.getText().toString();
        String productPrice = binding.etProductPrice.getText().toString();
        String productDesc = binding.etProductDesc.getText().toString();

        if ((images == null || images.length == 0)) {
            new MessageDialog(this, getString(R.string.title_error_message), getString(R.string.error_msg_missing_ads_images));
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

        adsViewModel.updateService(params, images, this, getSupportFragmentManager()).observe(this, mainResponse -> setResult(RESULT_OK));
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                        File file = FileUtils.getFile(this, uri);
                        RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(this.getContentResolver().getType(uri)));
                        images[i] = MultipartBody.Part.createFormData(ApiConstant.ADD_ADS_IMAGES, file.getName(), requestPhoto);
                    }
                    showAdsImagesList(mArrayUri);
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    File file = FileUtils.getFile(this, uri);
                    RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(this.getContentResolver().getType(uri)));
                    images = new MultipartBody.Part[1];
                    images[0] = MultipartBody.Part.createFormData(ApiConstant.ADD_ADS_IMAGES, file.getName(), requestPhoto);
                    mArrayUri.add(uri);
                    showAdsImagesList(mArrayUri);
                }

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

    private void showAdsImagesList(List<Uri> uriList) {
        binding.tvAttachImage.setVisibility(View.GONE);
        binding.ivAddImage.setVisibility(View.GONE);
        binding.rvAdsImage.setVisibility(View.VISIBLE);
        AdsImagesAdapter adsImagesAdapter = new AdsImagesAdapter(uriList, this);
        binding.rvAdsImage.setAdapter(adsImagesAdapter);
    }

    private void initCategorySpinnersValues() {
        //Set Governorate List Values
        categoriesViewModel.getCategoriesListLiveData().observe(this, categoryModelList -> {
            binding.spCategory.setAdapter(new CategoriesAdapter(this, categoryModelList));
            binding.spCategory.setOnItemClickListener((parent, view, position, id) -> {
                CategoryModel items = (CategoryModel) parent.getItemAtPosition(position);
                binding.spCategory.setText(items.getTitle(), false);
                categoryModelId = items.getId();
            });
        });
    }

    @Override
    public void onClick() {
        binding.tvAttachImage.setVisibility(View.VISIBLE);
        binding.ivAddImage.setVisibility(View.VISIBLE);
        binding.rvAdsImage.setVisibility(View.GONE);
    }
}