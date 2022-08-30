package com.mqtt.workactiv.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    //    public SharedViewModel() {
//        super();
//        statusEmitterInALoop();
//    }

    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();

    public void selectItem(String item) {
        selectedItem.setValue(item);
    }

    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }
}
