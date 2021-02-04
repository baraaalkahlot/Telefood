package com.bik.telefood.ui.onboarding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bik.telefood.ui.onboarding.boards.FirstBoardFragment;
import com.bik.telefood.ui.onboarding.boards.SecBoardFragment;
import com.bik.telefood.ui.onboarding.boards.ThirdBoardFragment;

import org.jetbrains.annotations.NotNull;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {


    public SectionsPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        position += 1;
        switch (position) {
            case 1:
                return FirstBoardFragment.newInstance(position);
            case 2:
                return SecBoardFragment.newInstance(position);
            case 3:
                return ThirdBoardFragment.newInstance(position);
            default:
                return FirstBoardFragment.newInstance(position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}