package com.minipoly.android.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.entity.Conversation;
import com.minipoly.android.entity.User;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;

public class SocialRepository {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference users = db.collection("user");
    private static CollectionReference conversations = db.collection("conversation");
    public static FireLiveDocument<User> getUser(String userId){
        return new FireLiveDocument<>(users.document(userId).get(), User.class);
    }

    public static void updateUser(User user, CompleteListener listener) {
        users.document(user.getId()).set(user).addOnCompleteListener(task ->
                listener.onComplete(task.isSuccessful()));
    }

    public static String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static FireLiveQuery<User> getFriends(String userId){
        return new FireLiveQuery<>(users.document(userId)
                .collection("friends").get(), User.class);
    }

    public static FireLiveDocument startConversation(Conversation conversation){
        return new FireLiveDocument<>(conversations.add(conversation), Conversation.class);
    }

    public static User getdemoUser() {
        User user = new User();
        user.setId(getUserId());
        user.setName("kamel");
        user.setPicture(getUserId());
        user.setEmail("kameloov@gmail.com");
        user.setPhone("0201123758139");
        return user;
    }
}
