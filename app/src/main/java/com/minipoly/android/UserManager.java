package com.minipoly.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.minipoly.android.entity.Interaction;

import static com.minipoly.android.References.interactions;

public class UserManager {
    private static FirebaseAuth mauth;
    private static GoogleSignInClient signInClient;
    public static int GOOGLE_LOGIN = 1214;
    public static Interaction interaction;
    private static MutableLiveData<Boolean> logged = new MutableLiveData(false);

    public static void init(Context context) {
        mauth = FirebaseAuth.getInstance();
        if (getUserID() != null) {
            logged.setValue(true);
            getInteractions(null);

        } else {
            logged.setValue(false);
        }

    }

    public static LiveData<Boolean> getLoginState() {
        return logged;
    }

    private static void getInteractions(CompleteListener listener) {
        interactions.document(getUserID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null)
                interaction = task.getResult().toObject(Interaction.class);
            if (listener != null)
                listener.onComplete(task.isSuccessful());
        });
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
                .addOnCompleteListener(task -> {
                    listener.onComplete(task.isComplete());
                    logged.setValue(true);
                });
    }

    public static void linkWithFacebook(AccessToken token, CompleteListener listener) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mauth.signInWithCredential(credential).addOnCompleteListener(task -> {
            listener.onComplete(task.isComplete());
        });
    }

    public static void loginAnonymously(final CompleteListener listener) {
        mauth.signInAnonymously().addOnCompleteListener(task -> {
            logged.setValue(true);
            listener.onComplete(task.isComplete());
        });
    }
}
