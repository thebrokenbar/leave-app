package com.meteorhead.leave.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

import java.io.File
import java.util.Calendar
import java.util.Locale

import io.realm.Realm
import io.realm.internal.IOException

/**
 * Created by wierzchanowskig on 20.10.2016.
 */

object RealmExporter {
    fun exportDatabase(context: Context) {

        // init realm
        val realm = Realm.getDefaultInstance()

        var exportRealmFile: File? = null
        try {
            // get or create an "export.realm" file
            exportRealmFile = File(context.externalCacheDir, "export.realm")

            // if "export.realm" already exists, delete
            exportRealmFile.delete()

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        realm.close()

        // init email intent and add export.realm as attachment
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, "cocomide@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT,
                        "LEAVE - Realm database file " + Calendar.getInstance(Locale.getDefault()))
        intent.putExtra(Intent.EXTRA_TEXT, "Realm Database")
        val u = Uri.fromFile(exportRealmFile)
        intent.putExtra(Intent.EXTRA_STREAM, u)

        // start email intent
        context.startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"))
    }
}
