package com.minipoly.android.repository;

import android.location.Geocoder;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.UserManager;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.utils.LocalData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.minipoly.android.References.db;
import static com.minipoly.android.References.realestates;
import static com.minipoly.android.References.users;

public class RealestateRepository {


    public static void addRealestate(Realestate realestate) {
        String id = realestates.document().getId();
        realestate.setId(id);
        realestates.document(id).set(realestate);
    }

    public static void updateViews(String id) {
        realestates.document(id).update("views", FieldValue.increment(1));
    }

    public static void addRealestate(Realestate realestate, CompleteListener listener) {
        String id = realestates.document().getId();
        realestate.setId(id);
        realestates.document(id).set(realestate).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void setRealestate(Realestate realestate) {
        realestates.document(realestate.getId()).set(realestate);
    }


    public static void search(List<String> tags, DataListener<List<Realestate>> listener) {
        if (tags == null || tags.size() == 0)
            listener.onComplete(false, null);
        Query q = realestates;
        for (String tag : tags)
            q = q.whereEqualTo("tags." + tag, true);

        q.get().addOnCompleteListener(task -> {
            List<Realestate> realestates = new ArrayList<>();
            if (task.isSuccessful() && task.getResult() != null)
                realestates = task.getResult().toObjects(Realestate.class);
            listener.onComplete(task.isSuccessful(), realestates);
        });
    }

    public static void getFollowingAds(DataListener<List<Realestate>> listener) {
        String userId = UserManager.getUserID();
        SocialRepository.getFollowing(userId, (success, data) -> {
            if (success && data != null) {
                List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                for (UserBrief user : data) {
                    Task t = realestates.whereEqualTo("userBrief.id", user.getId()).get();
                    tasks.add(t);
                }
                Tasks.whenAllComplete(tasks).addOnCompleteListener(task -> {
                    List<Realestate> realestates = new ArrayList<>();
                    if (task.isSuccessful() && task.getResult() != null)
                        for (Task t : task.getResult())
                            if (t.isSuccessful() && t.getResult() != null)
                                realestates.addAll(((QuerySnapshot) t.getResult()).toObjects(Realestate.class));
                    listener.onComplete(task.isSuccessful(), realestates);
                });

            } else
                listener.onComplete(true, null);

        });
    }

    public static void follow(String id, CompleteListener listener) {
        UserBrief brief = new UserBrief(LocalData.getUserInfo());
        realestates.document(id).collection(C.COLLECTION_FOLLOWERS).document(brief.getId())
                .set(brief).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    public static void unfollow(String id, CompleteListener listener) {
        UserBrief brief = new UserBrief(LocalData.getUserInfo());
        realestates.document(id).collection(C.COLLECTION_FOLLOWERS).document(brief.getId())
                .delete().addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    public static void isFollowing(String id, DataListener<Boolean> listener) {
        UserBrief brief = new UserBrief(LocalData.getUserInfo());
        realestates.document(id).collection(C.COLLECTION_FOLLOWERS).document(brief.getId()).get()
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(), task.getResult().exists()));
    }

    public static void setDiscount(String id, float percent, Date offerEnd, CompleteListener listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("offerPercent", percent);
        map.put("offerEnd", offerEnd);
        realestates.document(id).update(map).addOnCompleteListener(task -> {
            if (listener != null)
                listener.onComplete(task.isSuccessful());
        });
    }

    public static void setRented(String id, boolean value, CompleteListener listener) {
        realestates.document(id).update("rented", value).addOnCompleteListener(task -> {
            listener.onComplete(task.isSuccessful());
        });
    }

    public static void promote(String id, int days) {
        WriteBatch batch = db.batch();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date end = calendar.getTime();
        batch.update(realestates.document(id), "promoteEnd", end);
        batch.update(users.document(UserRepository.getUserId()), "balance", FieldValue.increment(-1 * days * C.PROMOTE_PRICE));
        batch.commit();
    }

    public static void getRealestates(DataListener<List<Realestate>> listener) {
        realestates.get().addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(),
                task.isSuccessful() ? task.getResult().toObjects(Realestate.class) : null));
    }

    public static void getUserRealestates(String userId, DataListener<List<Realestate>> listener) {
        realestates.whereEqualTo("userBrief.id", userId).get().addOnCompleteListener(task -> {
            List<Realestate> realestates = null;
            if (task.isSuccessful() && task.getResult() != null)
                realestates = task.getResult().toObjects(Realestate.class);
            listener.onComplete(task.isSuccessful(), realestates);
        });
    }

    public static void getRealestate(String id, DataListener<Realestate> listener) {
        realestates.document(id).get().addOnCompleteListener(task -> {
            Realestate realestate = null;
            if (task.getResult() != null)
                realestate = task.getResult().toObject(Realestate.class);
            listener.onComplete(task.isSuccessful(), realestate);
        });
    }

    public static void like(String id, DataListener<Boolean> listener) {
        DocumentReference reference = realestates.document(id);
        SocialRepository.like(reference, listener);
    }

    public static void dislike(String id, DataListener<Boolean> listener) {
        DocumentReference reference = realestates.document(id);
        SocialRepository.dislike(reference, listener);
    }

    public static Realestate generateRealestate(double lat, double lng, Geocoder geocoder) {
        Realestate realestate = new Realestate();
        realestate.setLang(lng);
        realestate.setLat(lat);
        realestate.setCityName("Cairo");
        realestate.setCityNameAR("القاهرة");
        return realestate;
    }

}
