package com.minipoly.android.livedata;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.minipoly.android.ProgressInfo;


public class LiveWriteDocument extends LiveData<ProgressInfo> {

    public LiveWriteDocument(Task t) {
        startTask(t);
    }

    public LiveWriteDocument() {
    }

    public void startTask(Task t) {
        setValue(ProgressInfo.PROGRESS);
        t.addOnCompleteListener(task -> setValue(task.isSuccessful() ? ProgressInfo.SUCCESS : ProgressInfo.FAIL));
    }
}
