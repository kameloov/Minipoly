package com.minipoly.android.filters;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.utils.FireStoreUtils;

import java.util.List;

import static com.minipoly.android.References.realestates;

public class FilterManager {

    public MutableLiveData<List<Realestate>> data = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private Query nextQuery;
    private Query current;
    private List<ValueFilter> filters;
    private boolean allLoaded = false;

    public void filter(List<ValueFilter> filters) {
        if (filters.equals(this.filters))
            return;
        this.filters = filters;
        allLoaded = false;
        current = FireStoreUtils.buildQuery(realestates, filters);
        if (current == null)
            current = realestates;
        current = current.orderBy("publishDate", Query.Direction.DESCENDING).limit(20);
        load(current, false);
    }


    public void loadMore() {
        if (nextQuery == null || allLoaded) return;
        load(nextQuery, true);
    }

    private void load(Query query, boolean more) {
        loading.setValue(true);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                loading.setValue(false);
                List<Realestate> realestates = task.getResult().toObjects(Realestate.class);
                allLoaded = more && realestates.size() == 0;
                if (more && data.getValue() != null)
                    realestates.addAll(0, data.getValue());
                data.setValue(realestates);
                int index = task.getResult().getDocuments().size() - 1;
                DocumentSnapshot last = task.getResult().getDocuments().get(index);
                nextQuery = current.startAfter(last);
            }
        });
    }

}
