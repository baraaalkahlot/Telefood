package com.bik.telefood.CommonUtils.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bik.telefood.R;
import com.bik.telefood.model.entity.general.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private final List<City> cityItemsList;
    private final ItemFilter mFilter = new ItemFilter();
    private List<City> filterCityList;
    private LayoutInflater mInflater;

    public CityAdapter(Context context, List<City> cityItemsList) {
        mContext = context;
        this.cityItemsList = cityItemsList;
        this.filterCityList = cityItemsList;
        mInflater = LayoutInflater.from(context);

    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        View listItem = convertView;
        if (listItem == null) {
            listItem = mInflater.inflate(R.layout.dropdown_menu_popup_item, null);

            holder = new ViewHolder();
            holder.text = listItem.findViewById(R.id.tv_spinner);
            listItem.setTag(holder);
        } else {
            holder = (ViewHolder) listItem.getTag();
        }
        holder.text.setText(filterCityList.get(position).getTitle());

        return listItem;
    }


    @Override
    public int getCount() {
        return filterCityList == null ? 0 : filterCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return mFilter;
    }

    static class ViewHolder {
        TextView text;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
/*            if (filterString.isEmpty()) {
                filterCityList = cityItemsList;
            } else {
                final List<City> list = new ArrayList<>();
                for (City item : cityItemsList) {
                    if (item.getTitle().toLowerCase().contains(filterString)) {
                        list.add(item);
                    }
                }
                filterCityList = list;
            }
            results.values = filterCityList;
            results.count = filterCityList.size();*/

            final List<City> list = cityItemsList;

            int count = list.size();
            final ArrayList<City> nlist = new ArrayList<City>(count);

            City filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getTitle().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterCityList = (ArrayList<City>) results.values;
            notifyDataSetChanged();
        }
    }
}
