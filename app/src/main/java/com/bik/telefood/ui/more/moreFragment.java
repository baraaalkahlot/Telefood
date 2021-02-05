package com.bik.telefood.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentMoreBinding;
import com.bik.telefood.ui.common.ProvidersDetailsActivity;
import com.bik.telefood.ui.more.ads.MyAdsActivity;
import com.bik.telefood.ui.support.TechnicalSupportActivity;

public class moreFragment extends Fragment {

    private static final int ACTION_MOVE_TO_EDIT_USER = 100;
    private MoreViewModel mViewModel;
    private static final int ACTION_MOVE_TO_SUBSCRIPTION = 101;
    private static final int ACTION_MOVE_TO_PROVIDERS_DETAILS = 103;
    private static final int ACTION_MOVE_TO_SETTING_ACTIVITY = 105;
    private FragmentMoreBinding binding;

    public static moreFragment newInstance() {
        return new moreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);

        binding.cardMyAds.ivItemMore.setImageResource(R.drawable.ic_ads);
        binding.cardProviders.ivItemMore.setImageResource(R.drawable.ic_providers);
        binding.cardSupport.ivItemMore.setImageResource(R.drawable.ic_technical_support);

        binding.cardMyAds.tvItemMore.setText(R.string.label_my_ads);
        binding.cardProviders.tvItemMore.setText(R.string.label_providers);
        binding.cardSupport.tvItemMore.setText(R.string.label_support);


        binding.cardSetting.ivItemMore.setImageResource(R.drawable.ic_setting);
        binding.cardPrivacyPolicy.ivItemMore.setImageResource(R.drawable.ic_privacy_policy);
        binding.cardAboutApp.ivItemMore.setImageResource(R.drawable.ic_about_app);

        binding.cardSetting.tvItemMore.setText(R.string.title_setting);
        binding.cardPrivacyPolicy.tvItemMore.setText(R.string.label_privacy_policy);
        binding.cardAboutApp.tvItemMore.setText(R.string.label_about_app);


        binding.cardEditPassword.ivItemMore.setImageResource(R.drawable.ic_lock);
        binding.cardSignOut.ivItemMore.setImageResource(R.drawable.ic_sign_out);

        binding.cardEditPassword.tvItemMore.setText(R.string.label_change_password);
        binding.cardSignOut.tvItemMore.setText(R.string.label_sign_out);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoreViewModel.class);

        binding.includeCardUserInfo.btnEditUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
            startActivityForResult(intent, ACTION_MOVE_TO_EDIT_USER);
        });

        binding.includeCardUserInfo.btnRenew.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SubscriptionRenewalActivity.class);
            startActivityForResult(intent, ACTION_MOVE_TO_SUBSCRIPTION);
        });

        binding.cardMyAds.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyAdsActivity.class);
            startActivity(intent);
        });

        binding.cardProviders.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProvidersDetailsActivity.class);
            startActivityForResult(intent, ACTION_MOVE_TO_PROVIDERS_DETAILS);
        });

        binding.cardSupport.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TechnicalSupportActivity.class);
            startActivity(intent);
        });

        binding.cardEditPassword.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.cardSetting.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivityForResult(intent, ACTION_MOVE_TO_SETTING_ACTIVITY);
        });

    }

}