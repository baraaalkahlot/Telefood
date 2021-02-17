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
import com.bik.telefood.model.entity.general.Governorate;

import java.util.List;

public class GovernorateAdapter extends ArrayAdapter<Governorate> {

    private final Context mContext;
    private final List<Governorate> governorates;

    public GovernorateAdapter(@NonNull Context context, List<Governorate> governorates) {
        super(context, 0, governorates);
        mContext = context;
        this.governorates = governorates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.dropdown_menu_popup_item, parent, false);

        Governorate governorate = governorates.get(position);

        TextView name = listItem.findViewById(R.id.tv_spinner);
        name.setText(governorate.getTitle());

        return listItem;
    }
}
