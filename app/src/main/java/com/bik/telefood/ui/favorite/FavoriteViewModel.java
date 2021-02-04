package com.bik.telefood.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<Integer> mIndex;
    private LiveData<String> mText;

    public FavoriteViewModel() {
        mIndex = new MutableLiveData<>();
        mText = Transformations.map(mIndex, input -> "Hello world from section: " + input);
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}