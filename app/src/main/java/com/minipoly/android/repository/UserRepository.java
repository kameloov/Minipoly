package com.minipoly.android.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.entity.Interaction;
import com.minipoly.android.entity.User;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.utils.LocalData;

import static com.minipoly.android.References.interactions;
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

    public static String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
