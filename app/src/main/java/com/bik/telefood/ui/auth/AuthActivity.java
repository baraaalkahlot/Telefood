package com.bik.telefood.ui.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.bik.telefood.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        NavHostFragment finalHost = NavHostFragment.create(R.navigation.auth_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_auth_fragment, finalHost)
                .setPrimaryNavigationFragment(finalHost) // equivalent to app:defaultNavHost="true"
                .commit();

    }
}