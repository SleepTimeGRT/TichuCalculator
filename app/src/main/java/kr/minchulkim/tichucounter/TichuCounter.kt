package kr.minchulkim.tichucounter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TichuCounter : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}