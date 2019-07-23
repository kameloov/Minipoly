package com.minipoly.android.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by kameloov on 8/2/2018.
 */

public class SocialUtils {

    public static void dial(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        context.startActivity(intent);
    }

    public static boolean whatsappMsg(Context c, String number, String msg) {
        if (appInstalledOrNot(c, "com.whatsapp")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setPackage("com.whatsapp");
            String url = "https://wa.me/" + number + "/?text=" + msg;
            intent.setData(Uri.parse(url));
            c.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean appInstalledOrNot(Context c, String uri) {
        PackageManager pm = c.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static boolean sendEmail(Context context, String address, String title, String content) {
        boolean success = false;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT, content);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
            success = true;
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        return success;
    }

    public static void youtube(Context context, String url) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public static void facebook(Context context, String url) {
        Uri uri = Uri.parse(url);
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static void browser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(i);
    }
}
