package com.bik.telefood.ui.favorite;

import android.app.Activity;
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
import com.bik.telefood.databinding.FragmentFavoriteProductBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.model.entity.general.services.ServicesListModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.common.ui.ProductDetailsActivity;
import com.bik.telefood.ui.common.viewmodel.ToggleFavoriteViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProductFragment extends Fragment implements FavoriteProvidersAdapter.OnCardClickListener, FavoriteProductAdapter.OnCardClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int ACTION_GO_TO_PRODUCT_DETAILS = 106;
    private FragmentFavoriteProductBinding binding;
    private FavoriteProductViewModel mViewModel;
    private FavoriteProductAdapter favoriteProductAdapter;
    private List<ServicesItemModel> data;
    private SkeletonScreen productsSkeleton;

    public static FavoriteProductFragment newInstance(int index) {
        FavoriteProductFragment fragment = new FavoriteProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteProductBinding.inflate(inflater, container, false);
        data = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteProductViewModel.class);

        favoriteProductAdapter = new FavoriteProductAdapter(data, this);
        binding.rvProduct.setAdapter(favoriteProductAdapter);
        productsSkeleton = Skeleton.bind(binding.rvProduct)
                .adapter(favoriteProductAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_my_product)
                .show();

        getFavoriteProduct();
        mViewModel.setFavoritesProduct(ApiConstant.FAVORITE_TYPE_SERVICE, getContext(), getActivity().getSupportFragmentManager());
    }

    private void getFavoriteProduct() {
        mViewModel.getServicesResponseLiveData().observe(getViewLifecycleOwner(), servicesResponse -> {
            if (servicesResponse.getServices().getData() == null || servicesResponse.getServices().getData().isEmpty()) {
                showEmptyStatus();
            } else {
                ServicesListModel servicesListModel = servicesResponse.getServices();
                data.addAll(servicesListModel.getData());
            }
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_animation_from_bottom);
            LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
            binding.rvProduct.setLayoutAnimation(layoutAnimationController);
            favoriteProductAdapter.notifyDataSetChanged();
            productsSkeleton.hide();
        });
    }


    @Override
    public void onClick(int id) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        intent.putExtra(AppConstant.PRODUCT_IS_FAVORITE, true);
        startActivityForResult(intent, ACTION_GO_TO_PRODUCT_DETAILS);
    }

    @Override
    public void onFavClick(int id, int position) {
        ToggleFavoriteViewModel toggleFavoriteViewModel = new ViewModelProvider(this).get(ToggleFavoriteViewModel.class);
        toggleFavoriteViewModel.favoriteToggle(ApiConstant.FAVORITE_TYPE_SERVICE, id, getContext(), getActivity().getSupportFragmentManager()).observe(this, mainResponse -> {
            data.remove(position);
            favoriteProductAdapter.notifyItemRemoved(position);
            favoriteProductAdapter.notifyItemRangeChanged(position, data.size());
            if ((data.isEmpty())) showEmptyStatus();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if (requestCode == ACTION_GO_TO_PRODUCT_DETAILS && resultCode == Activity.RESULT_OK) {
            data.clear();
            mViewModel.setFavoritesProduct(ApiConstant.FAVORITE_TYPE_SERVICE, getContext(), getActivity().getSupportFragmentManager());
        }
    }

    private void showEmptyStatus() {
        binding.includeEmptyStatusProduct.getRoot().setVisibility(View.VISIBLE);
        binding.rvProduct.setVisibility(View.GONE);
        binding.includeEmptyStatusProduct.tvEmptyStatusTitle.setText(R.string.title_empty_status_favorite_product);
    }
}