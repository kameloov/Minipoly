package com.minipoly.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class UserManager {
    private static FirebaseAuth mauth;
    private static GoogleSignInClient signInClient;
    public static int GOOGLE_LOGIN = 1214;

    public static void init(Context context) {
        mauth = FirebaseAuth.getInstance();
        //  initGoogleSignIn(context);
    }
 /*   private static void initGoogleSignIn(Context context){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(context,gso);
    }*/

    public static String getUserID() {
        return mauth.getCurrentUser().getUid();
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
