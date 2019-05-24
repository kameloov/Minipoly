package com.minipoly.android.repository;

import android.net.Uri;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.minipoly.android.C;
import com.minipoly.android.entity.Image;
import com.minipoly.android.livedata.FireLiveUpload;

public class MediaRepository {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference users = db.collection("user");
    private static StorageReference root = FirebaseStorage.getInstance().getReference().getRoot().child("minipoly");

    public static String getNextId(String userId) {
        return users.document(userId).collection(C.IMAGES_COLLECTION).document().getId();
    }

    public static void addImage(Image image) {
        users.document(image.getUserId()).collection(C.IMAGES_COLLECTION).document(image.getId()).set(image);
    }

    public static void updateAvatar(String userId, Uri uri, FireLiveUpload uploader) {
        uploader.setUploadTask(root.child(C.AVATARS_FOLDER).child(userId).putFile(uri));
    }

}
