package com.example.notesapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
// import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory // Keep this for dev if needed, remove for prod

class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            // THIS IS THE KEY: Use Play Integrity for Android production apps
            PlayIntegrityAppCheckProviderFactory.getInstance()

            // DO NOT use DebugAppCheckProviderFactory for your production build
            // If you were using RecaptchaEnterpriseAppCheckProviderFactory, remove it
            // If you want to use debug during development, uncomment the next line,
            // but make sure to remove it before releasing to production:
            // DebugAppCheckProviderFactory.getInstance()
        )
    }
}
