package com.minipoly.android.repository;

import com.google.firebase.firestore.CollectionReference;
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

import static com.minipoly.android.References.auctions;
import static com.minipoly.android.References.realestates;

public class CommentRepository {
    private static String advrtId = "";
    private static DocumentSnapshot last;
    private static int PAGE_SIZE = 10;


    public static FireLiveQuery<Comment> loadComments(String id) {
        return new FireLiveQuery<Comment>(realestates.document(id).collection(C.COMMENTS_COLLECTION)
                .orderBy("date", Query.Direction.ASCENDING), Comment.class, false);
    }

    public static FireLiveQuery<Comment> loadAuctionComments(String id) {
        return new FireLiveQuery<Comment>(auctions.document(id).collection(C.COMMENTS_COLLECTION)
                .orderBy("date", Query.Direction.ASCENDING), Comment.class, false);
    }


    public static void likeComment(String itemId, String commentId, boolean auction, DataListener<Boolean> listener) {
        CollectionReference collection = auction ? auctions : realestates;
        DocumentReference reference = collection.document(itemId).collection(C.COMMENTS_COLLECTION).document(commentId);
        SocialRepository.like(reference, listener);
    }

    public static void dislikeComment(String itemId, String commentId, boolean auction, DataListener<Boolean> listener) {
        CollectionReference collection = auction ? auctions : realestates;
        DocumentReference reference = collection.document(itemId).collection(C.COMMENTS_COLLECTION).document(commentId);
        SocialRepository.dislike(reference, listener);
    }

    public static void addAuctionComment(Comment comment, CompleteListener listener) {
        DocumentReference reference = auctions.document(comment.getAdvrtId())
                .collection(C.COMMENTS_COLLECTION).document();
        comment.setId(reference.getId());
        reference.set(comment).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
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
        reference.set(reply).addOnCompleteListener(task -> {
            listener.onComplete(task.isSuccessful());
        });
    }

    public static void loadReplies(Comment comment, DataListener<List<Reply>> listener) {
        realestates.document(comment.getAdvrtId()).collection(C.COMMENTS_COLLECTION)
                .document(comment.getId()).collection(C.REPLIES).orderBy("date").get().addOnCompleteListener(task -> {
            List<Reply> replies = null;
            if (task.isSuccessful())
                replies = task.getResult().toObjects(Reply.class);
            listener.onComplete(task.isSuccessful(), replies);
        });
    }


}
