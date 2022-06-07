package uz.gita.justcontact.contact.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.justcontact.contact.data.source.local.MyPreference

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MyPreference.init(this)
        //MyPreference.getInstance()
        instance = this
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var instance : Context
        private set
    }

}



