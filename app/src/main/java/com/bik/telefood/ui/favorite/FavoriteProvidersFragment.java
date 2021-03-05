package com.bik.telefood.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentFavoriteProvidersBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsListModel;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.common.ui.ProvidersDetailsActivity;
import com.bik.telefood.ui.common.viewmodel.ToggleFavoriteViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProvidersFragment extends Fragment implements FavoriteProvidersAdapter.OnCardClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentFavoriteProvidersBinding binding;
    private FavoriteProvidersViewModel mViewModel;
    private FavoriteProvidersAdapter favoriteProvidersAdapter;
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
        data = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteProvidersViewModel.class);

        favoriteProvidersAdapter = new FavoriteProvidersAdapter(data, this);
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
            if (vendorsResponse.getVendors().getData() == null || vendorsResponse.getVendors().getData().isEmpty()) {
                showEmptyStatus();
            } else {
                VendorsListModel vendors = vendorsResponse.getVendors();
                data.addAll(vendors.getData());
            }
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_animation_from_bottom);
            LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
            binding.rvProviders.setLayoutAnimation(layoutAnimationController);
            favoriteProvidersAdapter.notifyDataSetChanged();
            providersSkeleton.hide();
        });
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(getActivity(), ProvidersDetailsActivity.class);
        intent.putExtra(AppConstant.USER_ID, (int) id);
        startActivity(intent);
    }

    @Override
    public void onFavClick(int id, int position) {
        ToggleFavoriteViewModel toggleFavoriteViewModel = new ViewModelProvider(this).get(ToggleFavoriteViewModel.class);
        toggleFavoriteViewModel.favoriteToggle(ApiConstant.FAVORITE_TYPE_VENDOR, id, getContext(), getActivity().getSupportFragmentManager()).observe(this, mainResponse -> {
            data.remove(position);
            favoriteProvidersAdapter.notifyItemRemoved(position);
            favoriteProvidersAdapter.notifyItemRangeChanged(position, data.size());
            if (data.isEmpty()) showEmptyStatus();
        });
    }

    private void showEmptyStatus() {
        binding.includeEmptyStatusProduct.getRoot().setVisibility(View.VISIBLE);
        binding.rvProviders.setVisibility(View.GONE);
        binding.includeEmptyStatusProduct.tvEmptyStatusTitle.setText(R.string.title_empty_status_favorite_providers);
    }
}