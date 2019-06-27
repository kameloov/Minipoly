package com.minipoly.android.repository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Comment;
import com.minipoly.android.entity.Reply;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.utils.LocalData;

import java.util.List;

import static com.minipoly.android.References.realestates;

public class CommentRepository {
    private static String advrtId = "";
    private static DocumentSnapshot last;
    private static int PAGE_SIZE = 10;


    public static FireLiveQuery<Comment> laodComments(String id) {
        return new FireLiveQuery<Comment>(realestates.document(id).collection(C.COMMENTS_COLLECTION)
                .orderBy("date", Query.Direction.ASCENDING), Comment.class, false);
    }

    public static void loadRealestateComments(String id, DataListener<List<Comment>> listener) {

        /// reset every thing if we are getting commetns for new advrt
        if (!id.equals(advrtId)) {
            last = null;
        }
        // set advrtI  to the specified id so we can use it in next check
        advrtId = id;
        Query query = realestates.document(id).collection(C.COMMENTS_COLLECTION).orderBy("date", Query.Direction.DESCENDING)
                .limit(PAGE_SIZE);
        if (last != null)
            query = query.startAfter(last);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> docs = task.getResult().getDocuments();
                if (docs.size() > 0)
                    last = docs.get(docs.size() - 1);
            }
            listener.onComplete(task.isSuccessful(), task.getResult().toObjects(Comment.class));
        });
    }

    public static void addComment(Comment comment, CompleteListener listener) {
        DocumentReference reference = realestates.document(comment.getAdvrtId())
                .collection(C.COMMENTS_COLLECTION).document();
        comment.setId(reference.getId());
        reference.set(comment).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void addReply(String text, Comment comment, CompleteListener listener) {
        DocumentReference reference = realestates.document(comment.getAdvrtId())
                .collection(C.COMMENTS_COLLECTION).document(comment.getId()).collection(C.REPLIES).document();
        Reply reply = new Reply();
        reply.setCommentId(comment.getId());
        reply.setId(reference.getId());
        reply.setText(text);
        reply.setUserBrief(new UserBrief(LocalData.getUserInfo()));
        reference.set(reply).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void loadReplies(Comment comment, DataListener<List<Reply>> listener) {
        realestates.document(comment.getAdvrtId()).collection(C.COMMENTS_COLLECTION)
                .document(comment.getId()).collection(C.REPLIES).get().addOnCompleteListener(task -> {
            List<Reply> replies = null;
            if (task.isSuccessful())
                replies = task.getResult().toObjects(Reply.class);
            listener.onComplete(task.isSuccessful(), replies);
        });
    }


}
