package com.minipoly.android.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Conversation;
import com.minipoly.android.entity.Message;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;

import java.util.ArrayList;
import java.util.List;

import static com.minipoly.android.References.conversations;
import static com.minipoly.android.References.db;

public class ChatRepository {

    public static void openConversation(Realestate realestate, DataListener<String> listener) {
        conversations.whereEqualTo("advrtId", realestate.getId()).
                whereArrayContains("userIds", UserRepository.getUserId()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Conversation> conversations = task.getResult().toObjects(Conversation.class);
                        if (conversations != null && conversations.size() > 0)
                            listener.onComplete(true, conversations.get(0).getId());
                        else
                            addConveration(realestate, listener);
                    } else
                        addConveration(realestate, listener);
                });

    }

    public static FireLiveDocument<Conversation> getConversation(String id) {
        return new FireLiveDocument<>(conversations.document(id).get(), Conversation.class);
    }

    private static void addConveration(Realestate realestate, DataListener<String> listener) {
        DocumentReference reference = conversations.document();
        Conversation c = preprareConversation(realestate);
        c.setId(reference.getId());
        reference.set(c).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(), c.getId()));
    }

    private static Conversation preprareConversation(Realestate realestate) {
        Conversation c = new Conversation();
        c.setAdvrtId(realestate.getId());
        UserBrief brief1 = realestate.getUserBrief();
        UserBrief brief2 = UserRepository.getBrief();
        c.setAdvrtTitle(realestate.getTitle());
        c.setUser1(brief1);
        c.setUser2(brief2);
        ArrayList<String> ids = new ArrayList<>();
        ids.add(brief1.getId());
        ids.add(brief2.getId());
        c.setUserIds(ids);
        return c;
    }

    public static FireLiveQuery<Message> watchConversation(String id) {
        CollectionReference reference = conversations.document(id).collection(C.COLLECTION_MESSAGES);
        Query query = reference.orderBy("timestamp", Query.Direction.ASCENDING);
        return new FireLiveQuery<>(query, Message.class, false);
    }

    public static void sendMessage(String msg, String conversationId, CompleteListener listener) {
        DocumentReference convRef = conversations.document(conversationId);
        DocumentReference msgRef = convRef.collection(C.COLLECTION_MESSAGES).document();
        Message message = new Message();
        message.setConversationId(conversationId);
        message.setText(msg);
        message.setUserId(UserRepository.getUserId());
        message.setId(msgRef.getId());
        db.runTransaction(transaction -> {
            // add message
            message.setId(msgRef.getId());
            // set as last msg in conversation
            transaction.set(msgRef, message);
            // set as last m
            transaction.update(convRef, "lastMsg", message);
            return true;
        }).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful() && task.getResult() != null));
    }
}
