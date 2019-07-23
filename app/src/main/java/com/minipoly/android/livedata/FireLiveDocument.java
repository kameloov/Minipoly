package com.minipoly.android.livedata;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class FireLiveDocument<X> extends LiveData<X> {

    public FireLiveDocument(DocumentReference reference, Class<X> x) {
        reference.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null)
                return;
            if (documentSnapshot != null && documentSnapshot.exists())
                setValue(documentSnapshot.toObject(x));
        });
    }

    public FireLiveDocument(Task t,Class<X> x) {
        t.addOnCompleteListener(task -> {
            Object result = task.getResult();
            if ( task.isSuccessful() && result!=null ){
                if (result instanceof DocumentSnapshot)
                    setValue(((DocumentSnapshot)task.getResult()).toObject(x));
            }
        });
    }

}
