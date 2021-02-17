package com.bik.telefood.CommonUtils.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bik.telefood.R;
import com.bik.telefood.model.entity.general.City;

import java.util.List;

public class CityAdapter extends ArrayAdapter<City> {

    private final Context mContext;
    private final List<City> cityItems;

    public CityAdapter(@NonNull Context context, List<City> cityItems) {
        super(context, 0, cityItems);
        mContext = context;
        this.cityItems = cityItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.dropdown_menu_popup_item, parent, false);

        City items = cityItems.get(position);

        TextView name = listItem.findViewById(R.id.tv_spinner);
        name.setText(items.getTitle());

        return listItem;
    }
}
