package com.bik.telefood.ui.favorite;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class FavoriteAdapter extends FragmentStateAdapter {

    public FavoriteAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return FavoriteProductFragment.newInstance(position);
        } else {
            return FavoriteProvidersFragment.newInstance(position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}