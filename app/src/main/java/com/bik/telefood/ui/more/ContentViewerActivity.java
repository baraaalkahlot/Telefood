package com.bik.telefood.ui.more;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.databinding.ActivityContentViewerBinding;

public class ContentViewerActivity extends AppCompatActivity {
    private ActivityContentViewerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContentViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MoreViewModel moreViewModel = new ViewModelProvider(this).get(MoreViewModel.class);

        Intent intent = getIntent();
        boolean b = intent.getBooleanExtra(AppConstant.IS_ABOUT_APP, false);

        if (b)
            moreViewModel.aboutApp(this, getSupportFragmentManager()).observe(this, aboutAppResponse -> displayContent(aboutAppResponse.getAboutApp()));
        else
            moreViewModel.privacyPolicy(this, getSupportFragmentManager()).observe(this, privacyPolicyResponse -> displayContent(privacyPolicyResponse.getPrivacyPolicy()));

    }

    private void displayContent(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            binding.tvContent.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        else
            binding.tvContent.setText(Html.fromHtml(content));
    }
}
