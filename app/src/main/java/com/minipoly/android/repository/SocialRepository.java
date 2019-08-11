package com.minipoly.android.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Conversation;
import com.minipoly.android.entity.Interaction;
import com.minipoly.android.entity.Message;
import com.minipoly.android.entity.Review;
import com.minipoly.android.entity.User;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.livedata.LiveWriteDocument;

import java.util.List;

import static com.minipoly.android.References.db;
import static com.minipoly.android.References.interactions;
import static com.minipoly.android.References.realestates;
import static com.minipoly.android.References.users;

public class SocialRepository {


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

    public static FireLiveQuery<User> getFollowing(String userId) {
        return new FireLiveQuery<>(users.document(userId)
                .collection(C.COLLECTION_FOLLOWING).get(), User.class);
    }

    public static void getFollowing(String userId, DataListener<List<UserBrief>> listener) {
        users.document(userId).collection(C.COLLECTION_FOLLOWING).get().addOnCompleteListener(task -> {
            List<UserBrief> users = null;
            if (task.isSuccessful() && task.getResult() != null)
                users = task.getResult().toObjects(UserBrief.class);
            listener.onComplete(task.isSuccessful(), users);
        });
    }

    public static void isFollowing(String userId, DataListener<Boolean> listener) {
        users.document(userId).collection(C.COLLECTION_FOLLOWING).document(getUserId())
                .get().addOnCompleteListener(task -> {
            listener.onComplete(task.isSuccessful(), task.isSuccessful() && task.getResult().exists());
        });
    }

    public static void isFollower(String userId, DataListener<Boolean> listener) {
        users.document(getUserId()).collection(C.COLLECTION_FOLLOWERS)
                .whereEqualTo("id", userId).get().addOnCompleteListener(task -> {
            boolean b = false;
            if (task.isSuccessful())
                b = task.getResult().size() > 0;
            listener.onComplete(task.isSuccessful(), b);
        });
    }

    public static void follow(UserBrief brief, CompleteListener listener) {
        users.document(brief.getId()).collection(C.COLLECTION_FOLLOWERS).document(getUserId())
                .set(UserRepository.getBrief()).addOnCompleteListener(task -> {
            users.document(getUserId()).collection(C.COLLECTION_FOLLOWING).document(brief.getId()).set(brief);
            listener.onComplete(task.isSuccessful());
        });
    }

    public static void unFollow(String userId, CompleteListener listener) {
        users.document(userId).collection(C.COLLECTION_FOLLOWERS).document(getUserId())
                .delete().addOnCompleteListener(task -> {
            listener.onComplete(task.isSuccessful());
            users.document(getUserId()).collection(C.COLLECTION_FOLLOWING).document(userId).delete();
        });
    }

    public static void getFollowers(String userId, DataListener<List<UserBrief>> listener) {
        users.document(userId).collection(C.COLLECTION_FOLLOWERS).get().addOnCompleteListener(task -> {
            List<UserBrief> users = null;
            if (task.isSuccessful() && task.getResult() != null)
                users = task.getResult().toObjects(UserBrief.class);
            listener.onComplete(task.isSuccessful(), users);
        });
    }

    public static void liked(String id, DataListener<Boolean> listener) {
        interactions.document(getUserId()).get().addOnCompleteListener(task -> {
            boolean liked = false;
            if (task.isSuccessful()) {
                Interaction interaction = task.getResult().toObject(Interaction.class);
                if (interaction != null)
                    liked = interaction.getLike() != null && interaction.getLike().contains(id);
            }
            listener.onComplete(task.isSuccessful(), liked);
        });
    }

    public static void disliked(String id, DataListener<Boolean> listener) {
        interactions.document(getUserId()).get().addOnCompleteListener(task -> {
            boolean disliked = false;
            if (task.isSuccessful() && task.getResult() != null) {
                Interaction interaction = task.getResult().toObject(Interaction.class);
                if (interaction != null)
                    disliked = interaction.getDislike() != null && interaction.getDislike().contains(id);
            }
            listener.onComplete(task.isSuccessful(), disliked);
        });
    }


    public static void like(DocumentReference itemRef, DataListener<Boolean> listener) {
        DocumentReference reference = interactions.document(getUserId());
        String advrtId = itemRef.getId();
        db.runTransaction(transaction -> {
            Interaction interaction = transaction.get(reference).toObject(Interaction.class);
            boolean liked = interaction != null && interaction.getLike().contains(advrtId);
            boolean disliked = interaction != null && interaction.getDislike().contains(advrtId);
            // if disliked before remove from dislike list and remove from advrt dislikes
            if (disliked) {
                transaction.update(reference, "dislike", FieldValue.arrayRemove(advrtId));
                transaction.update(itemRef, "dislike", FieldValue.increment(-1));
            }
            if (liked) {
                transaction.update(reference, "like", FieldValue.arrayRemove(advrtId));
                transaction.update(itemRef, "like", FieldValue.increment(-1));
                return false;
            } else {
                transaction.update(reference, "like", FieldValue.arrayUnion(advrtId));
                transaction.update(itemRef, "like", FieldValue.increment(1));
                return true;
            }
        }).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(), task.getResult()));

    }

    public static void dislike(DocumentReference itemRef, DataListener<Boolean> listener) {
        DocumentReference reference = interactions.document(getUserId());
        String advrtId = itemRef.getId();
        db.runTransaction(transaction -> {
            Interaction interaction = transaction.get(reference).toObject(Interaction.class);
            boolean liked = interaction != null && interaction.getLike().contains(advrtId);
            boolean disliked = interaction != null && interaction.getDislike().contains(advrtId);
            // if liked remove the like
            if (liked) {
                transaction.update(reference, "like", FieldValue.arrayRemove(advrtId));
                transaction.update(itemRef, "like", FieldValue.increment(-1));
            }
            // if disliked before remove from dislike list and remove from advrt dislikes
            if (disliked) {
                transaction.update(reference, "dislike", FieldValue.arrayRemove(advrtId));
                transaction.update(itemRef, "dislike", FieldValue.increment(-1));
                return false;
            } else {
                transaction.update(reference, "dislike", FieldValue.arrayUnion(advrtId));
                transaction.update(itemRef, "dislike", FieldValue.increment(1));
                return true;
            }
        }).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(), task.getResult()));

    }

    public static LiveWriteDocument sendMessage(Conversation c, Message msg) {
        DocumentReference reference = realestates.document(c.getAdvrtId()).collection(C.CONVERSATIONS)
                .document(c.getId()).collection(C.MESSAGES).document();
        msg.setId(reference.getId());
        return new LiveWriteDocument(reference.set(msg));
    }


    public static void addReview(String userId, Review review, CompleteListener listener) {
        DocumentReference reference = users.document(userId).collection(C.COLLECTION_REVIEWS).document();
        review.setId(reference.getId());
        reference.set(review).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void getReviews(String userId, DataListener<List<Review>> listener) {
        users.document(userId).collection(C.COLLECTION_REVIEWS).get().addOnCompleteListener(task -> {
            List<Review> reviews = null;
            if (task.isSuccessful() && task.getResult() != null)
                reviews = task.getResult().toObjects(Review.class);
            listener.onComplete(task.isSuccessful(), reviews);
        });
    }

}
