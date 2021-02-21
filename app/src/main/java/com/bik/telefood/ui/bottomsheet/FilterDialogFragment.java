package com.bik.telefood.ui.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.spinners.CityAdapter;
import com.bik.telefood.CommonUtils.spinners.GovernorateAdapter;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentFilterDialogBinding;
import com.bik.telefood.model.entity.general.City;
import com.bik.telefood.model.entity.general.Governorate;
import com.bik.telefood.ui.common.viewmodel.CitiesViewModel;
import com.bik.telefood.ui.common.viewmodel.GovernorateViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilterDialogFragment extends BottomSheetDialogFragment {

    private FragmentFilterDialogBinding binding;
    private static OnFilterChangeListener onFilterChangeListener;
    private int governorateModelId;
    private int cityModelId;

    public static FilterDialogFragment newInstance(OnFilterChangeListener onFilterChangeListener) {
        final FilterDialogFragment fragment = new FilterDialogFragment();
        FilterDialogFragment.onFilterChangeListener = onFilterChangeListener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFilterDialogBinding.inflate(inflater, container, false);
        binding.tvClearFilter.setOnClickListener(v -> onFilterChangeListener.onClearFilterClick());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GovernorateViewModel governorateViewModel = new ViewModelProvider(this).get(GovernorateViewModel.class);
        governorateViewModel.getGovernorateList(getContext(), getActivity().getSupportFragmentManager()).observe(this, governoratesResponse -> {
            List<Governorate> governorateList = governoratesResponse.getGovernorates();
            binding.spGovernorate.setAdapter(new GovernorateAdapter(getContext(), governorateList));
            binding.spGovernorate.setOnItemClickListener((parent, view1, position, id) -> {
                Governorate items = (Governorate) parent.getItemAtPosition(position);
                binding.spGovernorate.setText(items.getTitle(), false);
                governorateModelId = items.getId();
                onFilterChangeListener.onChanged(governorateModelId, cityModelId);
                getCitiesSpinnersValuesById(governorateModelId);
                binding.ilCity.setHint(R.string.label_city);
                binding.spCity.setText("");
            });
        });

    }


    private void getCitiesSpinnersValuesById(int governorate) {
        //Set City List Values
        CitiesViewModel citiesViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);
        citiesViewModel.getCityList(governorate, getContext(), getActivity().getSupportFragmentManager()).observe(this, citiesResponse -> {
            List<City> cityList = citiesResponse.getCities();
            binding.spCity.setAdapter(new CityAdapter(getContext(), cityList));
            binding.spCity.setOnItemClickListener((parent, view, position, id) -> {
                City items = (City) parent.getItemAtPosition(position);
                binding.spCity.setText(items.getTitle(), false);
                cityModelId = items.getId();
                onFilterChangeListener.onChanged(governorateModelId, cityModelId);
            });
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            if (onFilterChangeListener instanceof FilterDialogFragment) {
                onFilterChangeListener = (OnFilterChangeListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public interface OnFilterChangeListener {
        void onChanged(int governorateModelId, int cityModelId);

        void onClearFilterClick();
    }
}