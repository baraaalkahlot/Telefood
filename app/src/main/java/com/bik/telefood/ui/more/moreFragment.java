package com.bik.telefood.ui.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.LoginDialog;
import com.bik.telefood.CommonUtils.SharedPreferencesHelper;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentMoreBinding;
import com.bik.telefood.model.entity.general.UserModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.AuthViewModel;
import com.bik.telefood.ui.auth.login.LoginActivity;
import com.bik.telefood.ui.auth.login.LoginViewModel;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.bik.telefood.ui.more.ads.MyAdsActivity;
import com.bik.telefood.ui.more.vendors.VendorsListActivity;
import com.bik.telefood.ui.support.TechnicalSupportActivity;
import com.squareup.picasso.Picasso;

public class moreFragment extends Fragment {

    private static final int ACTION_MOVE_TO_EDIT_USER = 100;
    private static final int ACTION_MOVE_TO_SUBSCRIPTION = 101;
    private static final int ACTION_MOVE_TO_PROVIDERS_DETAILS = 103;
    private static final int ACTION_MOVE_TO_SETTING_ACTIVITY = 105;
    private FragmentMoreBinding binding;
    private MoreViewModel mViewModel;
    private LoginViewModel loginViewModel;
    private AuthViewModel authViewModel;
    private CategoriesViewModel categoriesViewModel;


    public static moreFragment newInstance() {
        return new moreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);

        if (!SharedPreferencesHelper.getUserType(getActivity().getApplication()).equals(ApiConstant.ROLE_VENDOR))
            binding.cardMyAds.cardViewItemMore.setVisibility(View.GONE);

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

        binding.swipeToRefresh.setOnRefreshListener(() -> mViewModel.getProfile(getContext(), getActivity().getSupportFragmentManager()).observe(getViewLifecycleOwner(), updateProfileResponse -> {
            UserModel userModel = updateProfileResponse.getUser();
            loginViewModel.updateUserSection(
                    userModel.getAvatar()
                    , userModel.getName()
                    , userModel.getPhone()
                    , userModel.getGovernorateId()
                    , userModel.getCityId()
                    , userModel.getGovernorate()
                    , userModel.getCity()
                    , userModel.getChoosedPlanName()
                    , userModel.getRemainingDaysInPlan());

            binding.swipeToRefresh.setRefreshing(false);
            Picasso.get()
                    .load(Uri.parse(userModel.getAvatar()))
                    .error(R.drawable.ic_baseline_person)
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(binding.includeCardUserInfo.ivUserAvatar);

            binding.includeCardUserInfo.tvTitle.setText(userModel.getName());
            binding.includeCardUserInfo.rbSupplierRating.setRating(Float.parseFloat(userModel.getRating()));

            if (userModel.getChoosedPlanName() != null)
                binding.includeCardUserInfo.tvPlanName.setText(userModel.getChoosedPlanName());

            if (userModel.getRemainingDaysInPlan() != null)
                binding.includeCardUserInfo.tvRemainingDaysInPlan.setText(getString(R.string.days_left, userModel.getRemainingDaysInPlan()));
        }));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        if (!SharedPreferencesHelper.isLoggedIn(getActivity().getApplication())) {
            new LoginDialog().show(getActivity().getSupportFragmentManager(), "LoginDialog");
            binding.swipeToRefresh.setEnabled(false);
            return;
        }
        setUserInfo();

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
            Intent intent = new Intent(getActivity(), VendorsListActivity.class);
            startActivityForResult(intent, ACTION_MOVE_TO_PROVIDERS_DETAILS);
        });

        binding.cardSupport.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TechnicalSupportActivity.class);
            startActivity(intent);
        });


        binding.cardSetting.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivityForResult(intent, ACTION_MOVE_TO_SETTING_ACTIVITY);
        });

        binding.cardPrivacyPolicy.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContentViewerActivity.class);
            intent.putExtra(AppConstant.IS_ABOUT_APP, false);
            startActivity(intent);
        });

        binding.cardAboutApp.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContentViewerActivity.class);
            intent.putExtra(AppConstant.IS_ABOUT_APP, true);
            startActivity(intent);
        });

        binding.cardEditPassword.cardViewItemMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.cardSignOut.cardViewItemMore.setOnClickListener(v -> {
            signOut();
        });

    }

    private void setUserInfo() {
        loginViewModel.getUserModel().observe(getViewLifecycleOwner(), userModel -> {
            if (userModel != null) {

                Picasso.get()
                        .load(Uri.parse(userModel.getAvatar()))
                        .error(R.drawable.ic_baseline_person)
                        .placeholder(R.drawable.ic_baseline_person)
                        .into(binding.includeCardUserInfo.ivUserAvatar);

                binding.includeCardUserInfo.tvTitle.setText(userModel.getName());
                binding.includeCardUserInfo.rbSupplierRating.setRating(Float.parseFloat(userModel.getRating()));

                if (userModel.getChoosedPlanName() != null)
                    binding.includeCardUserInfo.tvPlanName.setText(userModel.getChoosedPlanName());

                if (userModel.getRemainingDaysInPlan() != null)
                    binding.includeCardUserInfo.tvRemainingDaysInPlan.setText(getString(R.string.days_left, userModel.getRemainingDaysInPlan()));
            }
        });
    }

    private void signOut() {
        authViewModel.logout(getContext(), getActivity().getSupportFragmentManager()).observe(getViewLifecycleOwner(), mainResponse -> {

        });
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        //Delete User Table
        loginViewModel.deleteUserTable();

        //Delete Category Table
        categoriesViewModel.deleteCategoriesTable();

        // Cache new status >> SIGN OUT
        SharedPreferencesHelper.clearAllData(getContext());
/*
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.setAutoInitEnabled(false);
        firebaseMessaging.deleteToken();*/
    }
}