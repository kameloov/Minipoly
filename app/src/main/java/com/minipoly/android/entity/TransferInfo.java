package com.minipoly.android.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import android.net.Uri;

import com.minipoly.android.BR;
import com.minipoly.android.ProgressInfo;

public class TransferInfo  extends BaseObservable {

    public TransferInfo() {
        status = ProgressInfo.IDLE;
        progress = 0;
    }

    private ProgressInfo status;
    private int progress;
    private Uri uri;
    private String text;

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Bindable
    public ProgressInfo getStatus() {
        return status;
    }


    @Bindable
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        notifyPropertyChanged(BR.progress);
    }

    public void setStatus(ProgressInfo status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
}
