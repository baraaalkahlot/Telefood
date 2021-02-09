package com.bik.telefood.ui.onboarding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bik.telefood.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity implements OnMovePagerClickListener {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardingBinding binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
    }


    @Override
    public void onClick(int position) {
        viewPager.setCurrentItem(position);
    }
}