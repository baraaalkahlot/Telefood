package com.bik.telefood.ui.common.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.bik.telefood.databinding.ImageSliderLayoutItemBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {

    private List<String> mSliderItems = new ArrayList<>();
    private OnImageSliderListener onImageSliderListener;

    public void setOnImageSliderListener(OnImageSliderListener onImageSliderListener) {
        this.onImageSliderListener = onImageSliderListener;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    public void placeHolder(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    public void setmSliderItems(List<String> mSliderItems) {
        this.mSliderItems = mSliderItems;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        return new SliderAdapterVH(ImageSliderLayoutItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    public interface OnImageSliderListener {
        void onImageClick(String path);
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        private AppCompatImageView imageView;
        private FrameLayout frameLayout;

        public SliderAdapterVH(ImageSliderLayoutItemBinding binding) {
            super(binding.getRoot());
            imageView = binding.ivAutoImageSlider;
            frameLayout = binding.frameLayoutImageSlider;
        }

        private void bind(int position) {
            String sliderItem = mSliderItems.get(position);
            Picasso.get().load(Uri.parse(sliderItem)).into(imageView);
            frameLayout.setOnClickListener(v -> onImageSliderListener.onImageClick(mSliderItems.get(position)));
        }
    }
}