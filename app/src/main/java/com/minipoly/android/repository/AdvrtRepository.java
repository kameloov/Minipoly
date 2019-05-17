package com.minipoly.android.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minipoly.android.entity.Advrt;
import com.minipoly.android.livedata.FireLiveDocument;

public class AdvrtRepository {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference ads = db.collection("ads");

    public static FireLiveDocument addAdvrt(Advrt advrt){
        String id = ads.document().getId();
        advrt.setId(id);
        return new FireLiveDocument(ads.add(advrt),Advrt.class);
    }

}
