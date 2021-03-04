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
import com.bik.telefood.model.entity.AddServicesRequestBody;
import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.ads.AdsImagesAdapter;
import com.bik.telefood.ui.ads.AdsViewModel;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;

import java.io.File;
import java.util.ArrayList;
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
    private List<Integer> imagesId;
    private int categoryModelId = 0;
    private int productId;
    private AdsImagesAdapter adsImagesAdapter;
    private int UPLOADED_IMAGES_COUNT = 0;
    private int imageCounter = 0;
    private String product_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        binding = FragmentAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mArrayUri = new ArrayList<>();
        imagesId = new ArrayList<>();
        adsImagesAdapter = new AdsImagesAdapter(mArrayUri, this);
        productId = getIntent().getIntExtra(AppConstant.PRODUCT_ID, 0);
        setInitValue();

        binding.llImage.setOnClickListener(v -> pickImage());
        binding.btnSend.setOnClickListener(v -> checkValidation());
    }


    private void setInitValue() {
        servicesViewModel.getSingleServicesList(productId, this, getSupportFragmentManager(), true).observe(this, singleServiceResponse -> {
            SingleServiceModel singleServiceModel = singleServiceResponse.getService();
            product_id = String.valueOf(singleServiceModel.getId());
            String product_name = singleServiceModel.getName();
            String product_category_id = singleServiceModel.getCategory();
            String product_price = singleServiceModel.getPrice();
            String product_desc = singleServiceModel.getDescription();
            categoryModelId = Integer.parseInt(singleServiceModel.getCategory());

            binding.etProductName.setText(product_name);
            binding.etProductPrice.setText(product_price);
            binding.etProductDesc.setText(product_desc);

            LocalConstant localConstant = new LocalConstant(this, this, this);
            localConstant.getCategoryNameById(Integer.parseInt(product_category_id), getSupportFragmentManager()).observe(this, s -> binding.spCategory.setText(s));

            initCategorySpinnersValues();

            for (String s : singleServiceModel.getImages()) {
                mArrayUri.add(Uri.parse(s));
            }
            if (!mArrayUri.isEmpty()) {
                binding.ivResetView.setVisibility(View.VISIBLE);
                binding.ivResetView.setOnClickListener(v -> resetRecyclerView());
            }
            showAdsImagesList();
        });
    }

    private void checkValidation() {
        String productName = binding.etProductName.getText().toString();
        String productPrice = binding.etProductPrice.getText().toString();
        String productDesc = binding.etProductDesc.getText().toString();

        if (mArrayUri.isEmpty()) {
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

        AddServicesRequestBody body = new AddServicesRequestBody(product_id, productName, categoryModelId, productPrice, productDesc, imagesId);

        adsViewModel.updateService(body, this, getSupportFragmentManager()).observe(this, mainResponse -> {
            setResult(RESULT_OK);
            finish();
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == ACTION_PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data) {

                binding.btnSend.setEnabled(false);
                showUploadAnim();
                binding.ivResetView.setVisibility(View.VISIBLE);
                binding.ivResetView.setOnClickListener(v -> resetRecyclerView());

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    UPLOADED_IMAGES_COUNT += mClipData.getItemCount();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        uploadImage(uri);
                    }
                } else if (data.getData() != null) {
                    UPLOADED_IMAGES_COUNT++;
                    Uri uri = data.getData();
                    mArrayUri.add(uri);
                    uploadImage(uri);
                }
            }

        } catch (Exception e) {
            new MessageDialog(this, getString(R.string.title_error_message), e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == ACTION_PICK_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new MessageDialog(this, getString(R.string.title_request_permission), getString(R.string.msg_request_permission));
        }
    }

    private void resetRecyclerView() {
        binding.btnSend.setEnabled(false);
        hideUploadAnim();
        mArrayUri.clear();
        imagesId.clear();
        UPLOADED_IMAGES_COUNT = 0;
        imageCounter = 0;

        adsImagesAdapter.notifyDataSetChanged();
        binding.ivResetView.setVisibility(View.GONE);
        binding.tvAttachImage.setVisibility(View.VISIBLE);
        binding.ivAddImage.setVisibility(View.VISIBLE);
        binding.rvAdsImage.setVisibility(View.GONE);
    }


    private void showAdsImagesList() {
        binding.tvAttachImage.setVisibility(View.GONE);
        binding.ivAddImage.setVisibility(View.GONE);
        binding.rvAdsImage.setVisibility(View.VISIBLE);
        binding.rvAdsImage.setAdapter(adsImagesAdapter);
        adsImagesAdapter.notifyDataSetChanged();
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

    private void uploadImage(Uri uri) {
        File file = FileUtils.getFile(this, uri);
        RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(this.getContentResolver().getType(uri)));
        MultipartBody.Part body = MultipartBody.Part.createFormData(ApiConstant.UPLOAD_ADS_IMAGE, file.getName(), requestPhoto);
        adsViewModel.uploadImage(body, this, getSupportFragmentManager()).observe(this, uploadImagesResponse -> {
            imagesId.add(uploadImagesResponse.getImageId());
            imageCounter++;
            if (imageCounter == UPLOADED_IMAGES_COUNT) {
                binding.btnSend.setEnabled(true);
                hideUploadAnim();
                showAdsImagesList();
            }
        });
    }

    private void showUploadAnim() {
        binding.lottieUploadImage.setVisibility(View.VISIBLE);
        binding.lottieUploadImage.playAnimation();
        binding.rvAdsImage.setVisibility(View.GONE);
    }

    private void hideUploadAnim() {
        binding.lottieUploadImage.cancelAnimation();
        binding.lottieUploadImage.setVisibility(View.GONE);
        binding.rvAdsImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddImageClick() {
        pickImage();
    }
}