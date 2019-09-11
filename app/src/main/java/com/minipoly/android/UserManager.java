package com.minipoly.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.minipoly.android.entity.Interaction;

import static com.minipoly.android.References.interactions;

public class UserManager {
    private static FirebaseAuth mauth;
    private static GoogleSignInClient signInClient;
    public static int GOOGLE_LOGIN = 1214;
    public static Interaction interaction;

    public static void init(Context context) {
        mauth = FirebaseAuth.getInstance();
        if (getUserID() != null)
            getInteractions(null);

    }

    private static void getInteractions(CompleteListener listener) {
        interactions.document(getUserID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null)
                interaction = task.getResult().toObject(Interaction.class);
            if (listener != null)
                listener.onComplete(task.isSuccessful());
        });
    }

/*    public static void dislike( String itemId){
        if (interaction==null)
            getInteractions(success -> {
                if (success) {
                    dislikeItem(itemId);
                }
            });
        else
            dislikeItem(itemId);
    }

    public static void like( String itemId){
        if (interaction==null)
            getInteractions(success -> {
                if (success) {
                    likeItem(itemId);
                }
            });
        else
            likeItem(itemId);
    }

    private static void dislikeItem(String id){
        DocumentReference reference = interactions.document(getUserID());
        if (interaction.getDislike().contains(id)){
            reference.update("dislike",FieldValue.arrayRemove(id));
            interaction.getDislike().remove(id);
        } else {
            if (interaction.getLike().contains(id)){
                reference.update("like",FieldValue.arrayRemove(id));
                reference.update("dislike",FieldValue.arrayUnion(id));
                interaction.getLike().remove(id);
                interaction.getDislike().add(id);
            }else {
                reference.update("dislike",FieldValue.arrayUnion(id));
                interaction.getDislike().add(id);
            }
        }
    }*/


    private static void likeItem(String id) {
        DocumentReference reference = interactions.document(getUserID());
        if (interaction.getLike().contains(id)) {
            reference.update("like", FieldValue.arrayRemove(id));
            interaction.getLike().remove(id);
        } else {
            if (interaction.getDislike().contains(id)) {
                reference.update("dislike", FieldValue.arrayRemove(id));
                reference.update("like", FieldValue.arrayUnion(id));
                interaction.getDislike().remove(id);
                interaction.getLike().add(id);
            } else {
                reference.update("like", FieldValue.arrayUnion(id));
                interaction.getLike().add(id);
            }
        }
    }

    public static void initGoogleSignIn(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(context,gso);
    }

    public static String getUserID() {

        return mauth.getCurrentUser() == null ? null : mauth.getCurrentUser().getUid();
    }

    public static void loginGoogle(Activity ac) {
        Intent signInIntent = signInClient.getSignInIntent();
        ac.startActivityForResult(signInIntent, GOOGLE_LOGIN);
    }

    public static boolean isLogged() {
        return (mauth.getUid() != null && !mauth.getUid().isEmpty());
    }

    public static void linkToGoogle(GoogleSignInAccount acct, CompleteListener listener) {
        Log.d("signIn", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(task -> listener.onComplete(task.isComplete()));
    }

    public static void linkWithFacebook(AccessToken token, CompleteListener listener) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mauth.signInWithCredential(credential).addOnCompleteListener(task -> {
            listener.onComplete(task.isComplete());
        });
    }

    public static void loginAnonymously(final CompleteListener listener) {
        mauth.signInAnonymously().addOnCompleteListener(task -> listener.onComplete(task.isComplete())
        );
    }
}
