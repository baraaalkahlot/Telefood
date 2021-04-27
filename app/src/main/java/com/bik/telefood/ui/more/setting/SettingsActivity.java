package com.bik.telefood.ui.more.setting;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivitySettingsBinding;
import com.bik.telefood.ui.auth.login.LoginViewModel;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.settings.getId(), new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private SettingViewModel viewModel;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            viewModel = new ViewModelProvider(this).get(SettingViewModel.class);
            LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

            try {

                SwitchPreferenceCompat notificationSwitch = findPreference("sync");
                loginViewModel.getUserModel().observe(this, userModel -> {
                    if (userModel != null) {
                        boolean oldValue = userModel.getSendNotification();
                        notificationSwitch.setChecked(oldValue);
                        notificationSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                            viewModel.toggleNotification(getContext(), getActivity().getSupportFragmentManager()).observe(getViewLifecycleOwner(), mainResponse -> {
                                loginViewModel.updateNotification(!oldValue);
                            });
                            return true;
                        });
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}