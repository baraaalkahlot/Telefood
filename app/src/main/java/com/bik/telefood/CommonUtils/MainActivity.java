package com.bik.telefood.CommonUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMainBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.chat.ChatListActivity;
import com.bik.telefood.ui.notifications.NotificationActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_meals, R.id.navigation_add_ads, R.id.navigation_favorite, R.id.navigation_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (!SharedPreferencesHelper.getUserType(getApplication()).equals(ApiConstant.ROLE_VENDOR))
            binding.navView.getMenu().removeItem(R.id.navigation_add_ads);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (SharedPreferencesHelper.getUserType(getApplication()).equals(AppConstant.EMPTY))
            return false;
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_notification) {
            startActivity(new Intent(this, NotificationActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_chat) {
            startActivity(new Intent(this, ChatListActivity.class));
            return true;
        }
        return false;
    }
}