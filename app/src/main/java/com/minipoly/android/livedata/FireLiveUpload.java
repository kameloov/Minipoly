package com.minipoly.android.livedata;

import androidx.lifecycle.LiveData;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.minipoly.android.ProgressInfo;
import com.minipoly.android.entity.TransferInfo;

public class UploadFileLive extends LiveData<TransferInfo> {
    private TransferInfo info;
    private UploadTask uploadTask;
    private OnSuccessListener successListener;
    private OnFailureListener failureListener;
    private OnProgressListener progressListener;
    private static final String TAG = "UploadFileLive";

    public UploadFileLive() {
        info = new TransferInfo();
        info.setText("start");
        info.setProgress(0);
        info.setStatus(ProgressInfo.IDLE);
        setValue(info);
    }

    public void setUploadTask(UploadTask uploadTask) {
        this.uploadTask = uploadTask;
        addProgressListener();
        addFailureListener();
        addSuccessListeners();
    }

    private void addSuccessListeners() {
        if (successListener == null) {
            successListener = taskSnapshot -> {
                info.setStatus(ProgressInfo.SUCCESS);
                info.setProgress(100);
                info.setText("success");
                setValue(info);

                Log.e(TAG, "addSuccessListeners: success");
            };
        }
        uploadTask.addOnSuccessListener(successListener);
    }

    private void addFailureListener() {
        if (failureListener == null) {
            failureListener = e -> {
                info.setStatus(ProgressInfo.FAIL);
                info.setProgress(0);
                info.setText("success");
                setValue(info);
                Log.e(TAG, "addFailureListener: fail");
            };
        }
        uploadTask.addOnFailureListener(failureListener);
    }

    private void addProgressListener(){
        if (progressListener == null) {
            progressListener = (taskSnapshot) -> {
                info.setStatus(ProgressInfo.PROGRESS);
                UploadTask.TaskSnapshot shot = (UploadTask.TaskSnapshot) taskSnapshot;
                Log.e(TAG, "addProgressListener: "+shot.getBytesTransferred()+" of "+shot.getTotalByteCount());
                int progress =Math.round(100.0f * shot.getBytesTransferred() / shot.getTotalByteCount());
                info.setProgress(progress);
                info.setText("progress");
                Log.e(TAG, "addProgressListener: "+progress );
                setValue(info);
            };
        }
        uploadTask.addOnProgressListener(progressListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (progressListener!=null)
        uploadTask.removeOnProgressListener(progressListener);
        if (failureListener!=null)
        uploadTask.removeOnFailureListener(failureListener);
        if (successListener!=null)
        uploadTask.removeOnSuccessListener(successListener);

    }
}
