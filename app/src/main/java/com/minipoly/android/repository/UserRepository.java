package com.minipoly.android.repository;

import com.google.firebase.firestore.DocumentReference;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.UserManager;
import com.minipoly.android.entity.Interaction;
import com.minipoly.android.entity.Notification;
import com.minipoly.android.entity.Report;
import com.minipoly.android.entity.User;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.utils.LocalData;

import java.util.List;

import static com.minipoly.android.References.interactions;
import static com.minipoly.android.References.reports;
import static com.minipoly.android.References.users;

public class UserRepository {
    public static UserBrief getBrief() {
        UserBrief brief = new UserBrief();
        User u = LocalData.getUserInfo();
        brief.setId(u.getId());
        brief.setName(u.getName());
        brief.setDeals(u.getDealCount());
        brief.setStars(u.getStars());
        brief.setPicture(u.getPicture());
        return brief;
    }

    public static User getCachedUser() {
        User user = new User();
        user.setId(getUserId());
        user.setName("kamel");
        user.setPicture(getUserId());
        user.setEmail("kameloov@gmail.com");
        user.setPhone("0201123758139");
        return user;
    }

    public static void getUerNotification(String userId, int limit, DataListener<List<Notification>> listener) {
        users.document(userId).collection(C.COLLECTION_NOTIFICATION).limit(limit).get()
                .addOnCompleteListener(task -> {
                    List<Notification> list = null;
                    if (task.getResult() != null)
                        list = task.getResult().toObjects(Notification.class);
                    listener.onComplete(task.isSuccessful(), list);
                });
    }


    public static FireLiveQuery<Notification> getUserNotifications(String userId, int limit) {
        return new FireLiveQuery<Notification>(users.document(userId)
                .collection(C.COLLECTION_NOTIFICATION).limit(limit).get(), Notification.class);
    }

    public static void updateUser(User user, CompleteListener listener) {
        users.document(user.getId()).set(user).addOnCompleteListener(task -> {
            if (listener != null)
                listener.onComplete(task.isSuccessful());
        });
    }

    public static void addUser(User user, CompleteListener listener) {
        users.document(user.getId()).set(user).addOnCompleteListener(task -> {
            if (listener != null)
                listener.onComplete(task.isSuccessful());
            interactions.document(user.getId()).set(new Interaction());
        });

    }

    public static FireLiveDocument<User> getUser(String userId) {
        return new FireLiveDocument<User>(users.document(userId).get(), User.class);
    }

    public static void getUser(String userId, DataListener<User> listener) {
        users.document(userId).get().addOnCompleteListener(task -> {
            User user = null;
            if (task.isSuccessful() && task.getResult() != null)
                user = task.getResult().toObject(User.class);
            listener.onComplete(task.isSuccessful(), user);
        });
    }

    public static String getUserId() {
        return UserManager.getUserID();
    }

    public static void refreshUser() {
        if (getUserId() == null)
            return;
        users.document(getUserId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                User user = task.getResult().toObject(User.class);
                LocalData.saveUserInfo(user);
            }
        });
    }

    public static void reportUser(Report report, CompleteListener listener) {
        DocumentReference reference = reports.document();
        report.setId(reference.getId());
        reference.set(report).addOnCompleteListener(task -> {
            if (listener != null)
                listener.onComplete(task.isSuccessful());

        });
    }
}
