package com.minipoly.android;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.minipoly.android.livedata.FireLiveUpload;

import java.io.InputStream;

public class StorageManager {
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static StorageReference root = storage.getReference().getRoot().child("minipoly");

    public static FireLiveUpload uploadStream(String path, InputStream stream) {
        FireLiveUpload live = new FireLiveUpload();
        live.setUploadTask(root.child(path).putStream(stream));
        return live;
    }

    public static StorageReference getRoot() {
        return root;
    }

    public static StorageReference getUserRoot() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return root.child(uid);
    }
}
