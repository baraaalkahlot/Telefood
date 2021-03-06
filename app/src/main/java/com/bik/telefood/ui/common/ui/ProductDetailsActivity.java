package com.bik.telefood.ui.common.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.LocalConstant;
import com.bik.telefood.CommonUtils.SharedPreferencesHelper;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityProductDetailsBinding;
import com.bik.telefood.model.entity.general.ShortUserInfoModel;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.chat.MessageActivity;
import com.bik.telefood.ui.common.adapter.ImageSliderAdapter;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;
import com.bik.telefood.ui.common.viewmodel.ToggleFavoriteViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity implements ImageSliderAdapter.OnImageSliderListener {

    private ActivityProductDetailsBinding binding;
    private int product_id;
    private boolean favoriteUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (SharedPreferencesHelper.getUserType(getApplication()).equals(ApiConstant.ROLE_USER)) {
            binding.ivContactWithProvider.setVisibility(View.VISIBLE);
            binding.ivContactWithProvider.setOnClickListener(v -> startActivity(new Intent(this, MessageActivity.class)));
        }
        Intent intent = getIntent();
        product_id = intent.getIntExtra(AppConstant.PRODUCT_ID, 0);
        boolean isFavorite = intent.getBooleanExtra(AppConstant.PRODUCT_IS_FAVORITE, false);
        boolean isMyFavorite = intent.getBooleanExtra(AppConstant.PRODUCT_NO_FAVORITE, false);
        if (isMyFavorite)
            binding.btnFavorite.setVisibility(View.GONE);
        else
            binding.btnFavorite.setChecked(isFavorite);

        ServicesViewModel servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        servicesViewModel.getSingleServicesList(product_id, this, getSupportFragmentManager(), true).observe(this, servicesResponse -> {
            SingleServiceModel singleServiceModel = servicesResponse.getService();
            ShortUserInfoModel shortUserInfoModel = singleServiceModel.getUser();
            String product_name = singleServiceModel.getName();
            String product_category_id = singleServiceModel.getCategory();
            String product_price = singleServiceModel.getPrice();
            String product_desc = singleServiceModel.getDescription();
            String rate = singleServiceModel.getUser().getRating();

            try {
                float starNumber = Float.parseFloat(rate);
                binding.rbSupplierRating.setRating(starNumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            LocalConstant localConstant = new LocalConstant(this, this, this);
            localConstant.getCategoryNameById(Integer.parseInt(product_category_id), getSupportFragmentManager()).observe(this, s -> binding.tvCategory.setText(s));

            binding.tvProductName.setText(product_name);
            binding.tvProductPrice.setText(getString(R.string.bind_price, product_price));
            binding.tvProductDesc.setText(product_desc);

            binding.tvFullName.setText(shortUserInfoModel.getName());
            Picasso.get()
                    .load(shortUserInfoModel.getAvatar())
                    .error(R.drawable.ic_baseline_person)
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(binding.ivUserProfileImage);

            // Set Images to Image Slider
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(singleServiceModel.getImages());
            binding.imageSlider.setSliderAdapter(imageSliderAdapter);
            imageSliderAdapter.setOnImageSliderListener(this);
            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            binding.imageSlider.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
            binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            binding.imageSlider.setIndicatorSelectedColor(getResources().getColor(R.color.lochinvar));
            binding.imageSlider.setIndicatorUnselectedColor(Color.WHITE);
            binding.imageSlider.setScrollTimeInSec(3);
            binding.imageSlider.startAutoCycle();

            binding.btnFavorite.setOnClickListener(v -> toggleFavorite());

        });
    }

    private void toggleFavorite() {
        ToggleFavoriteViewModel toggleFavoriteViewModel = new ViewModelProvider(this).get(ToggleFavoriteViewModel.class);
        toggleFavoriteViewModel.favoriteToggle(ApiConstant.FAVORITE_TYPE_SERVICE, product_id, this, getSupportFragmentManager()).observe(this, mainResponse -> favoriteUpdated = true);
        setResult(RESULT_OK);
    }

    @Override
    public void onImageClick(String path) {
    }
}