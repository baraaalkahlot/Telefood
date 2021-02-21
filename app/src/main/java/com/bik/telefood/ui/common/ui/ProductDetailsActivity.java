package com.bik.telefood.ui.common.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.LocalConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityProductDetailsBinding;
import com.bik.telefood.model.entity.general.ShortUserInfoModel;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceModel;
import com.bik.telefood.ui.common.adapter.ImageSliderAdapter;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity implements ImageSliderAdapter.OnImageSliderListener {

    private ActivityProductDetailsBinding binding;
    private ServicesViewModel servicesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int product_id = intent.getIntExtra(AppConstant.PRODUCT_ID, 0);

        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        servicesViewModel.getSingleServicesList(product_id, this, getSupportFragmentManager(), true).observe(this, servicesResponse -> {
            SingleServiceModel singleServiceModel = servicesResponse.getService();
            ShortUserInfoModel shortUserInfoModel = singleServiceModel.getUser();
            String product_name = singleServiceModel.getName();
            String product_category_id = singleServiceModel.getCategory();
            String product_price = singleServiceModel.getPrice();
            String product_desc = singleServiceModel.getDescription();

            LocalConstant localConstant = new LocalConstant(this, this, this);
            localConstant.getCategoryNameById(Integer.parseInt(product_category_id)).observe(this, s -> binding.tvCategory.setText(s));

            binding.tvProductName.setText(product_name);
            binding.tvProductPrice.setText(product_price);
            binding.tvProductDesc.setText(product_desc);

            binding.tvFullName.setText(shortUserInfoModel.getName());
            Picasso.get()
                    .load(shortUserInfoModel.getAvatar())
                    .error(R.drawable.ic_baseline_person)
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(binding.ivUserProfileImage);

            // Set Images to Image Slider
            List<String> imageAllItemsList = singleServiceModel.getImages();
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter();
            binding.imageSlider.setSliderAdapter(imageSliderAdapter);
            binding.imageSlider.setInfiniteAdapterEnabled(false);
            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
            binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            imageSliderAdapter.setOnImageSliderListener(this);
            imageSliderAdapter.setmSliderItems(imageAllItemsList);
        });
    }


    @Override
    public void onImageClick(String path) {

    }
}