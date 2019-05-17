package com.minipoly.android.livedata;

import androidx.lifecycle.LiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;


public class FireLiveQuery<T> extends LiveData<List<T>> {

    private EventListener<QuerySnapshot> listener;
    private Query query;
    private ListenerRegistration registration;

    public FireLiveQuery(Task t, Class<T> x ) {
        t.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Object result = task.getResult();
                if (result != null) {
                    if (result instanceof QuerySnapshot)
                        setValue(((QuerySnapshot) task.getResult()).toObjects(x));
                }
            }
        });
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (listener!=null && query!=null)
            registration = query.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration !=null)
            registration.remove();
    }

    public FireLiveQuery(Query query, Class<T> x ) {
        this.query = query;
        listener = (queryDocumentSnapshots, e) -> {
        if (e!=null)
            return;
        setValue(queryDocumentSnapshots.toObjects(x));
        };
    }
}
