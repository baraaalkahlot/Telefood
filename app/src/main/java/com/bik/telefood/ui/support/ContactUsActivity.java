package com.bik.telefood.ui.support;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityContactUsBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.ads.AdsImagesAdapter;
import com.bik.telefood.ui.bottomsheet.ActionTypeDialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ContactUsActivity extends AppCompatActivity implements AdsImagesAdapter.OnCancelImageListener, ActionTypeDialogFragment.OnActionSelectListener {

    private static final int ACTION_PICK_FILE = 2;
    private static final int ACTION_PICK_IMAGE = 105;
    private ActivityContactUsBinding binding;
    private ArrayList<Uri> mArrayUri;
    private MultipartBody.Part[] attachments;
    private AdsImagesAdapter adsImagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        SupportViewModel supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        setContentView(binding.getRoot());

        mArrayUri = new ArrayList<>();
        adsImagesAdapter = new AdsImagesAdapter(mArrayUri, this, this);

        binding.llAttachFile.setOnClickListener(v -> chooseAttachType());
        binding.btnSend.setOnClickListener(v -> {
            String subject = binding.etSubject.getText().toString();
            String details = binding.etDetails.getText().toString();
            if (subject.isEmpty()) {
                binding.ilSubject.setError(getString(R.string.error_msg_missing_subject));
                return;
            }

            if (details.isEmpty()) {
                binding.ilDetails.setError(getString(R.string.error_msg_missing_desc));
                return;
            }

            HashMap<String, RequestBody> params = new HashMap<>();
            params.put(ApiConstant.TICKET_SUBJECT, RequestBody.create(subject, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
            params.put(ApiConstant.TICKET_DETAILS, RequestBody.create(details, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
            supportViewModel.addTicket(params, attachments, this, getSupportFragmentManager()).observe(this, mainResponse -> {
                resetView();
                setResult(RESULT_OK);
                finish();
            });
        });
    }

    private void chooseAttachType() {
        ActionTypeDialogFragment.newInstance(this).show(getSupportFragmentManager(), "ActionTypeDialogFragment");
    }

    private void pickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_PICK_IMAGE);
        }
    }

    private void pickFile() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openDirectory();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_PICK_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACTION_PICK_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else if ((requestCode == ACTION_PICK_FILE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            openDirectory();
        } else {
            new MessageDialog(this, getString(R.string.title_request_permission), getString(R.string.msg_request_permission));
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_PICK_IMAGE);
    }

    public void openDirectory() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, ACTION_PICK_FILE);
    }

    private void showAdsImagesList() {
        binding.tvAttachImage.setVisibility(View.GONE);
        binding.rvAttachImage.setVisibility(View.VISIBLE);
        binding.rvAttachImage.setAdapter(adsImagesAdapter);
        adsImagesAdapter.notifyDataSetChanged();
    }


    private void resetView() {
        mArrayUri.clear();
        attachments = null;
        binding.ivResetView.setVisibility(View.GONE);
        binding.tvAttachImage.setVisibility(View.VISIBLE);
        binding.rvAttachImage.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == ACTION_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {

                binding.ivResetView.setVisibility(View.VISIBLE);
                binding.ivResetView.setOnClickListener(v -> resetView());
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                    }
                    attachments = new MultipartBody.Part[mArrayUri.size()];
                    for (int i = 0; i < mArrayUri.size(); i++) {
                        Uri uri = mArrayUri.get(i);
                        File file = FileUtils.getFile(this, uri);
                        RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContentResolver().getType(uri)));
                        attachments[i] = MultipartBody.Part.createFormData(ApiConstant.ATTACHMENT, file.getName(), requestPhoto);

                    }
                    showAdsImagesList();
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    File file = FileUtils.getFile(this, uri);
                    RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContentResolver().getType(uri)));
                    attachments = new MultipartBody.Part[1];
                    attachments[0] = MultipartBody.Part.createFormData(ApiConstant.ATTACHMENT, file.getName(), requestPhoto);
                    mArrayUri.add(uri);
                    showAdsImagesList();
                }
            } else if (requestCode == ACTION_PICK_FILE && resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                File file = FileUtils.getFile(this, uri);
                RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContentResolver().getType(uri)));
                attachments = new MultipartBody.Part[1];
                attachments[0] = MultipartBody.Part.createFormData(ApiConstant.ATTACHMENT, file.getName(), requestPhoto);
                mArrayUri.add(uri);
                showAdsImagesList();
            }

        } catch (Exception e) {
            new MessageDialog(this, getString(R.string.title_error_message), e.getMessage());
        }
    }

    @Override
    public void onAddImageClick() {
        chooseAttachType();
    }

    @Override
    public void onAttachFile() {
        pickFile();
    }

    @Override
    public void onAttachPhotos() {
        pickImage();
    }
}