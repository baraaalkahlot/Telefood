package com.bik.telefood.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentFavoriteProvidersBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsListModel;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProvidersFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentFavoriteProvidersBinding binding;
    private FavoriteProvidersViewModel mViewModel;
    private FavoriteProvidersAdapter favoriteProvidersAdapter;
    private ServicesViewModel servicesViewModel;
    private List<VendorsModel> data;
    private SkeletonScreen providersSkeleton;

    public static FavoriteProvidersFragment newInstance(int index) {
        FavoriteProvidersFragment fragment = new FavoriteProvidersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteProvidersBinding.inflate(inflater, container, false);
        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        data = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteProvidersViewModel.class);

        favoriteProvidersAdapter = new FavoriteProvidersAdapter(data);
        binding.rvProviders.setAdapter(favoriteProvidersAdapter);
        providersSkeleton = Skeleton.bind(binding.rvProviders)
                .adapter(favoriteProvidersAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_providers_skeleton)
                .show();


        getFavoriteProviders();
        mViewModel.setNameQuery(ApiConstant.FAVORITE_TYPE_VENDOR, getContext(), getActivity().getSupportFragmentManager());
    }

    private void getFavoriteProviders() {
        mViewModel.getVendorsResponseLiveData().observe(getViewLifecycleOwner(), vendorsResponse -> {
            Log.d("wasd", "getFavoriteProviders: vendors" + vendorsResponse);
            Log.d("wasd", "getFavoriteProviders: get" + vendorsResponse.getVendors());
            providersSkeleton.hide();
            VendorsListModel vendors = vendorsResponse.getVendors();
            data.addAll(vendors.getData());
            favoriteProvidersAdapter.notifyDataSetChanged();
        });
    }
}