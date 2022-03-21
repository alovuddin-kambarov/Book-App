package uz.coder.mvpexample.room.db

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstants(this)

        // disable night mode settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // init blurLayout
       // BlurKit.init(this)
    }
}