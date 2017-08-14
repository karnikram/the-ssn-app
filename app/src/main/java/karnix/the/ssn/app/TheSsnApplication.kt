package karnix.the.ssn.app

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.google.firebase.database.FirebaseDatabase
import karnix.the.ssn.ssnmachan.BuildConfig

/**
 * Created by adithya321 on 4/20/17.
 */

class TheSsnApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()

            Stetho.initializeWithDefaults(this)
        }

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
