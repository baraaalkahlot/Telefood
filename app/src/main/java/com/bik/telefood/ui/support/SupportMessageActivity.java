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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMessageBinding;
import com.bik.telefood.model.entity.support.TicketModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.bottomsheet.ActionTypeDialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SupportMessageActivity extends AppCompatActivity implements ActionTypeDialogFragment.OnActionSelectListener {
    private static final int ACTION_PICK_FILE = 2;
    private static final int ACTION_PICK_IMAGE = 105;
    private ActivityMessageBinding binding;
    private SupportViewModel supportViewModel;
    private ArrayList<Uri> mArrayUri;
    private MultipartBody.Part[] attachments;
    private String id;
    private SupportMessageAdapter supportMessageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mArrayUri = new ArrayList<>();

        id = getIntent().getStringExtra(AppConstant.TICKET_ID);
        binding.ivLocation.setImageResource(R.drawable.ic_send);
        binding.ivAttach.setVisibility(View.VISIBLE);
        binding.ivAttach.setOnClickListener(v -> chooseAttachType());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        binding.rvMessages.setHasFixedSize(true);
        binding.rvMessages.setLayoutManager(linearLayoutManager);
        loadChat(id);
        binding.ivLocation.setOnClickListener(v -> sendMessage(id, binding.etMessage.getText().toString().trim(), false));
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

    private void loadChat(String id) {
        supportViewModel.showTicket(id, this, getSupportFragmentManager()).observe(this, showDetailsResponse -> {
            TicketModel ticketModel = showDetailsResponse.getTicket();
            binding.includeChatContact.tvTitle.setText(ticketModel.getSubject());
            binding.includeChatContact.tvDesc.setText(ticketModel.getDescription());
            supportMessageAdapter = new SupportMessageAdapter(showDetailsResponse.getMessages(), this);
            binding.rvMessages.setAdapter(supportMessageAdapter);
        });
    }

    private void sendMessage(String id, String msg, boolean isAttach) {
        if (msg.isEmpty() && !isAttach) return;
        binding.etMessage.setText("");
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put(ApiConstant.ID, RequestBody.create(id, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        hashMap.put(ApiConstant.MESSAGE, RequestBody.create(msg, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));

        supportViewModel.sendMessage(hashMap, attachments, this, getSupportFragmentManager(), true).observe(this, mainResponse -> loadChat(id));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == ACTION_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {

                attachments = null;
                mArrayUri.clear();

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
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    File file = FileUtils.getFile(this, uri);
                    RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContentResolver().getType(uri)));
                    attachments = new MultipartBody.Part[1];
                    attachments[0] = MultipartBody.Part.createFormData(ApiConstant.ATTACHMENT, file.getName(), requestPhoto);
                    mArrayUri.add(uri);
                }
            } else if (requestCode == ACTION_PICK_FILE && resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                File file = FileUtils.getFile(this, uri);
                RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(getContentResolver().getType(uri)));
                attachments = new MultipartBody.Part[1];
                attachments[0] = MultipartBody.Part.createFormData(ApiConstant.ATTACHMENT, file.getName(), requestPhoto);
                mArrayUri.add(uri);
            }
            sendMessage(id, "attachment", true);
        } catch (Exception e) {
            new MessageDialog(this, getString(R.string.title_error_message), e.getMessage());
        }
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