package com.bik.telefood.ui.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
    private int governorateModelId = 0;
    private int cityModelId = 0;
    private int fromPriceModelId = 0;
    private int toPriceModelId = 0;

    public static FilterDialogFragment newInstance(OnFilterChangeListener onFilterChangeListener) {
        final FilterDialogFragment fragment = new FilterDialogFragment();
        FilterDialogFragment.onFilterChangeListener = onFilterChangeListener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFilterDialogBinding.inflate(inflater, container, false);
        binding.tvClearFilter.setOnClickListener(v -> {
            onFilterChangeListener.onClearFilterClick();
            dismiss();
        });
        setPriceSpinnersValues();
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
                onFilterChangeListener.onChanged(governorateModelId, cityModelId, fromPriceModelId, toPriceModelId);
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
                onFilterChangeListener.onChanged(governorateModelId, cityModelId, fromPriceModelId, toPriceModelId);
            });
        });
    }


    private void setPriceSpinnersValues() {
        //Set Price List Values
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.price_entries));
        binding.spPrice.setAdapter(spinnerArrayAdapter);
        binding.spPrice.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    fromPriceModelId = 1;
                    toPriceModelId = 25;
                    break;
                case 1:
                    fromPriceModelId = 25;
                    toPriceModelId = 50;
                    break;
                case 2:
                    fromPriceModelId = 50;
                    toPriceModelId = 100;
                    break;
                case 3:
                    fromPriceModelId = 100;
                    toPriceModelId = 500;
                    break;
                case 4:
                    fromPriceModelId = 500;
                    toPriceModelId = 1000;
                    break;
            }
            onFilterChangeListener.onChanged(governorateModelId, cityModelId, fromPriceModelId, toPriceModelId);
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
        void onChanged(int governorateModelId, int cityModelId, int fromPrice, int toPrice);

        void onClearFilterClick();
    }
}