package com.meteorhead.leave.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.internal.IOException;

/**
 * Created by wierzchanowskig on 20.10.2016.
 */

public class RealmExporter {
    public static void exportDatabase(Context context) {

        // init realm
        Realm realm = Realm.getDefaultInstance();

        File exportRealmFile = null;
        try {
            // get or create an "export.realm" file
            exportRealmFile = new File(context.getExternalCacheDir(), "export.realm");

            // if "export.realm" already exists, delete
            exportRealmFile.delete();

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "cocomide@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "LEAVE - Realm database file " + Calendar.getInstance(Locale.getDefault()));
        intent.putExtra(Intent.EXTRA_TEXT, "Realm Database");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        context.startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }
}
