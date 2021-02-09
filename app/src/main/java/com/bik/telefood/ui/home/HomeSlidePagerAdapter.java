package com.bik.telefood.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomeSlidePagerAdapter extends FragmentStateAdapter {

    public HomeSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ProvidersItemFragment();
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
