package com.christian.lurienwallet.demo.ui.qrscanner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QRScannerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QRScannerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}