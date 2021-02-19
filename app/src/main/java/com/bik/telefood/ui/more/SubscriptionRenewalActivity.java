package com.bik.telefood.ui.more;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.FileUtils;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivitySubscriptionRenewalBinding;
import com.bik.telefood.model.entity.general.BankInformationModel;
import com.bik.telefood.model.network.ApiConstant;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SubscriptionRenewalActivity extends AppCompatActivity implements SubscriptionsPackagesAdapter.OnSingleSelectionListener {

    private static final int ACTION_PICK_IMAGE = 105;
    private ActivitySubscriptionRenewalBinding binding;
    private MoreViewModel moreViewModel;
    private SubscriptionsPackagesAdapter subscriptionsPackagesAdapter;
    private int packageId = 0;
    private MultipartBody.Part img_transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionRenewalBinding.inflate(getLayoutInflater());
        moreViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        setContentView(binding.getRoot());


        MoreViewModel moreViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        moreViewModel.getBankInfo(this, getSupportFragmentManager()).observe(this, bankInfoResponse -> {
            BankInformationModel bankInformationModel = bankInfoResponse.getInformation();
            appendedText(R.string.label_bank_name, bankInformationModel.getBank(), binding.include.tvBankName);
            appendedText(R.string.label_account_number, bankInformationModel.getAccountNumber(), binding.include.tvAccountNumber);
            appendedText(R.string.label_the_full_name, bankInformationModel.getFullName(), binding.include.tvFullName);
            appendedText(R.string.label_iban, bankInformationModel.getIBAN(), binding.include.tvIban);
        });

        moreViewModel.getSubscriptionPackages(this, getSupportFragmentManager()).observe(this, packageResponse -> {
            subscriptionsPackagesAdapter = new SubscriptionsPackagesAdapter(packageResponse.getPackages(), this);
            binding.rvSubscription.setAdapter(subscriptionsPackagesAdapter);
        });
        binding.llImage.setOnClickListener(v -> pickImage());

        binding.btnSend.setOnClickListener(v -> {
            if (packageId == 0) {
                new MessageDialog(this, getString(R.string.title_error_message), getString(R.string.error_msg_missing_package));
                return;
            }

            if (img_transfer == null) {
                binding.scrollView.fullScroll(ScrollView.FOCUS_UP);
                binding.tvAttachImage.setError(getString(R.string.title_note_add_transfer_picture));
                return;
            }

            moreViewModel.newSubscription(img_transfer, packageId, this, getSupportFragmentManager()).observe(this, mainResponse -> {
                setResult(RESULT_OK);
                finish();
            });
        });
    }

    private void appendedText(int label, String value, MaterialTextView textView) {
        SpannableStringBuilder str = new SpannableStringBuilder(getString(label));
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lochinvar)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(str);

        Spannable wordTwo = new SpannableString(value);
        wordTwo.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tundora)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(" " + wordTwo);
    }

    @Override
    public void onSelect(int packageId) {
        this.packageId = packageId;
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
                binding.ivTransferImage.setImageURI(uri);
                binding.tvAttachImage.setVisibility(View.GONE);
                binding.btnRemove.setVisibility(View.VISIBLE);
                binding.btnRemove.setOnClickListener(v -> {
                    img_transfer = null;
                    binding.tvAttachImage.setVisibility(View.VISIBLE);
                    binding.ivTransferImage.setImageResource(R.drawable.ic_add_photo);
                    v.setVisibility(View.GONE);
                });
                RequestBody requestPhoto = RequestBody.create(file, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA));
                img_transfer = MultipartBody.Part.createFormData(ApiConstant.IMAGE, file.getName(), requestPhoto);
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