package com.minipoly.android.utils;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.minipoly.android.entity.ValueFilter;

import java.util.List;

public class FireStoreUtils {

    public static Query buildQuery(CollectionReference reference, List<ValueFilter> filters) {
        Query query = processFilter(reference,filters.get(0));
        filters.remove(0);
        for (ValueFilter filter : filters) {
          processFilter(query,filter);
        }
        return query;
    }

    private static Query processFilter(Query query, ValueFilter filter) {
        switch (filter.getType()) {
            case LESS:
                query = query.whereLessThan(filter.getField(), filter.getValue1());
                break;
            case GREATER:
                query = query.whereGreaterThan(filter.getField(), filter.getValue1());
                break;
            case EQUAL:
                query = query.whereEqualTo(filter.getField(), filter.getValue1());
                break;
            case EQUAL_GREATER:
                query = query.whereGreaterThanOrEqualTo(filter.getField(), filter.getValue1());
                break;
            case EQUAL_LESS:
                query = query.whereLessThanOrEqualTo(filter.getField(), filter.getValue1());
                break;
            case RANGE:
                query = query.whereGreaterThan(filter.getField(), filter.getValue1())
                        .whereLessThan(filter.getField(), filter.getValue2());
                break;
        }
        return query;
    }

    public static Query processFilter(CollectionReference reference, ValueFilter filter) {
        Query query = null;
        switch (filter.getType()) {
            case LESS:
                query = reference.whereLessThan(filter.getField(), filter.getValue1());
                break;
            case GREATER:
                query = reference.whereGreaterThan(filter.getField(), filter.getValue1());
                break;
            case EQUAL:
                query = reference.whereEqualTo(filter.getField(), filter.getValue1());
                break;
            case EQUAL_GREATER:
                query = reference.whereGreaterThanOrEqualTo(filter.getField(), filter.getValue1());
                break;
            case EQUAL_LESS:
                query = reference.whereLessThanOrEqualTo(filter.getField(), filter.getValue1());
                break;
            case RANGE:
                query = reference.whereGreaterThan(filter.getField(), filter.getValue1())
                        .whereLessThan(filter.getField(), filter.getValue2());
                break;
        }
        return query;
    }
}
