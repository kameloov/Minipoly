package com.minipoly.android;

import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.minipoly.android.livedata.UploadFileLive;

import java.io.InputStream;


public class StorageManager {
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static StorageReference root = storage.getReference().getRoot().child("minipoly");

    public static UploadFileLive uploadStream(String path, InputStream stream){
        UploadFileLive live = new UploadFileLive();
        live.setUploadTask(root.child(path).putStream(stream));
        return live;
    }

    public static StorageReference getRoot() {
        return root;
    }
}
