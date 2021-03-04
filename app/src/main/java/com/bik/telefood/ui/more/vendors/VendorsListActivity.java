package com.bik.telefood.ui.more.vendors;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityProvidersListBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsListModel;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsModel;
import com.bik.telefood.ui.common.ui.ProvidersDetailsActivity;
import com.bik.telefood.ui.common.viewmodel.VendorsViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendorsListActivity extends AppCompatActivity implements VendorsAdapter.OnProvidersClickListener {

    private ActivityProvidersListBinding binding;
    private VendorsViewModel vendorsViewModel;
    private VendorsAdapter vendorsAdapter;
    private SkeletonScreen providersSkeleton;
    private HashMap<String, String> params;
    private List<VendorsModel> vendorsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorsViewModel = new ViewModelProvider(this).get(VendorsViewModel.class);
        binding = ActivityProvidersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        params = new HashMap<>();
        vendorsModels = new ArrayList<>();
        vendorsAdapter = new VendorsAdapter(vendorsModels, this);
        binding.rvProviders.setAdapter(vendorsAdapter);

        providersSkeleton = Skeleton.bind(binding.rvProviders)
                .adapter(vendorsAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_providers_skeleton)
                .show();

        vendorsAdapter.setOnLoadingRequestListener(page -> loadVendors(page, params));
        loadVendors(1, params);
    }

    private void loadVendors(int page, HashMap<String, String> params) {
        providersSkeleton.show();
        vendorsViewModel.getVendors(page, params, this, getSupportFragmentManager()).observe(this, vendorsResponse -> {
            VendorsListModel vendorsListModel = vendorsResponse.getVendors();
            if (vendorsListModel.getLastPage() == vendorsListModel.getCurrentPage()) {
                vendorsAdapter.setLastPage(true);
            }
            vendorsModels.addAll(vendorsListModel.getData());
            vendorsAdapter.notifyDataSetChanged();
            vendorsAdapter.setLoading(false);
            providersSkeleton.hide();
        });
    }

    @Override
    public void OnProvidersClick(int id) {
        Intent intent = new Intent(this, ProvidersDetailsActivity.class);
        intent.putExtra(AppConstant.USER_ID, id);
        startActivity(intent);
    }
}