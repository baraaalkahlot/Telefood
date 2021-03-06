package com.bik.telefood.ui.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityProvidersDetailsBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsModel;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.model.entity.general.services.ServicesListModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.common.adapter.ProductAdapter;
import com.bik.telefood.ui.common.viewmodel.ToggleFavoriteViewModel;
import com.bik.telefood.ui.common.viewmodel.VendorsViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProvidersDetailsActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private static final int ACTION_GO_TO_PRODUCT_DETAILS = 103;
    private ActivityProvidersDetailsBinding binding;
    private ProductAdapter productAdapter;
    private VendorsViewModel vendorsViewModel;
    private List<ServicesItemModel> servicesItemModels;
    private SkeletonScreen productSkeleton;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProvidersDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getIntExtra(AppConstant.USER_ID, 0);
        ToggleFavoriteViewModel toggleFavoriteViewModel = new ViewModelProvider(this).get(ToggleFavoriteViewModel.class);
        vendorsViewModel = new ViewModelProvider(this).get(VendorsViewModel.class);

        servicesItemModels = new ArrayList<>();
        productAdapter = new ProductAdapter(servicesItemModels, this, this);
        binding.rvProduct.setAdapter(productAdapter);
        productAdapter.setOnLoadingRequestListener(this::loadData);
        productSkeleton = Skeleton.bind(binding.rvProduct)
                .adapter(productAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_product)
                .show();
        loadData(1);
        binding.btnFavorite.setOnClickListener(v -> {
            toggleFavoriteViewModel.favoriteToggle(ApiConstant.FAVORITE_TYPE_VENDOR, id, this, getSupportFragmentManager()).observe(this, mainResponse -> setResult(RESULT_OK));
        });
    }

    private void loadData(int page) {
        productSkeleton.show();
        hideEmptyStatus();
        vendorsViewModel.getSingleVendors(page, id, this, getSupportFragmentManager()).observe(this, servicesResponse -> {
            VendorsModel vendorsModel = servicesResponse.getUser();
            ServicesListModel servicesListModel = servicesResponse.getServices();
            binding.tvFullName.setText(vendorsModel.getName());
            binding.rbSupplierRating.setRating(vendorsModel.getRating());
            Picasso.get().load(vendorsModel.getAvatar()).error(R.drawable.ic_baseline_person).placeholder(R.drawable.ic_baseline_person).fit().into(binding.ivAvatar);
            binding.btnFavorite.setChecked(vendorsModel.getFavorite());

            if (servicesListModel.getData() == null || servicesListModel.getData().isEmpty()) {
                showEmptyStatus();
            }
            if (servicesListModel.getLastPage() == servicesListModel.getCurrentPage()) {
                productAdapter.setLastPage(true);
            }
            servicesItemModels.addAll(servicesListModel.getData());
            productAdapter.notifyDataSetChanged();
            productAdapter.setLoading(false);
            productSkeleton.hide();
        });
    }

    private void showEmptyStatus() {
        binding.rvProduct.setVisibility(View.GONE);
        binding.includeEmptyStatusProduct.constraintLayoutEmptyStatusProduct.setVisibility(View.VISIBLE);
    }

    private void hideEmptyStatus() {
        binding.rvProduct.setVisibility(View.VISIBLE);
        binding.includeEmptyStatusProduct.constraintLayoutEmptyStatusProduct.setVisibility(View.GONE);
    }

    @Override
    public void OnProductClick(int id, Boolean favorite) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        intent.putExtra(AppConstant.PRODUCT_IS_FAVORITE, favorite);
        startActivityForResult(intent, ACTION_GO_TO_PRODUCT_DETAILS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_GO_TO_PRODUCT_DETAILS && resultCode == Activity.RESULT_OK) {
            servicesItemModels.clear();
            productAdapter.resetPager();
            loadData(1);
        }
    }
}