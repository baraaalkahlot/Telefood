package com.bik.telefood.CommonUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMainBinding;
import com.bik.telefood.model.MyFirebaseMessagingService;
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

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);

        String roomId = SharedPreferencesHelper.getRoomId(getApplication());
        toggleChatBadge(menu, roomId.isEmpty());


        Boolean notification = SharedPreferencesHelper.isThereNotification(getApplication());
        toggleNotificationBadge(menu, notification);

        if (roomId.isEmpty())
            MyFirebaseMessagingService.Notification.getInstance().getNewMessage().observe(this, s -> {
                toggleChatBadge(menu, s.isEmpty());
            });

        MyFirebaseMessagingService.Notification.getInstance().getNewNotification().observe(this, s -> {
            toggleNotificationBadge(menu, s);
        });

        return true;
    }


    private void toggleChatBadge(Menu menu, Boolean value) {
        if (value)
            menu.findItem(R.id.action_chat).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chat));
        else
            menu.findItem(R.id.action_chat).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chat_badge));
    }


    private void toggleNotificationBadge(Menu menu, Boolean value) {
        if (value)
            menu.findItem(R.id.action_notification).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_notification_badge));
        else
            menu.findItem(R.id.action_notification).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_notification));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_notification) {
            // Cache Notification Received
            SharedPreferences.Editor preferences = getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE).edit();
            preferences.putBoolean(AppConstant.IS_THERE_NOTIFICATION, false);
            preferences.apply();
            MyFirebaseMessagingService.Notification.getInstance().addNewNotification(false);
            startActivity(new Intent(this, NotificationActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_chat) {
            SharedPreferences.Editor preferences = getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE).edit();
            MyFirebaseMessagingService.Notification.getInstance().addNewMessage("");
            Intent intent = new Intent(this, ChatListActivity.class);
            intent.putExtra(AppConstant.ROOM_ID, SharedPreferencesHelper.getRoomId(getApplication()));
            startActivity(intent);
            preferences.putBoolean(AppConstant.IS_THERE_MESSAGE, false);
            preferences.putString(AppConstant.ROOM_ID, "");
            preferences.apply();
            return true;
        }
        return false;
    }
}