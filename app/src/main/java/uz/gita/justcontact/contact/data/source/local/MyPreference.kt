package uz.gita.justcontact.contact.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.justcontact.contact.data.model.common.StartFragmentEnum
import uz.gita.justcontact.contact.data.model.common.getStartFragment
import javax.inject.Inject

class MyPreference @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: MyPreference? = null

        fun init(context: Context) {
            instance = MyPreference(context)
        }

        fun getInstance(): MyPreference = instance!!
    }

    private val pref = context.getSharedPreferences("MyContacts", Context.MODE_PRIVATE)

    var startFragment: StartFragmentEnum
        set(value) = pref.edit().putInt("LOGIN_FRAGMENT", value.position).apply()
        get() = pref.getInt("LOGIN_FRAGMENT", StartFragmentEnum.LOGIN.position).getStartFragment()

    var token: String
        set(value) = pref.edit().putString("TOKEN", value).apply()
        get() = pref.getString("TOKEN", "")!!

    var firstName: String
        set(value) = pref.edit().putString("FIRST_NAME", value).apply()
        get() = pref.getString("FIRST_NAME", "")!!

    var lastName: String
        set(value) = pref.edit().putString("LAST_NAME", value).apply()
        get() = pref.getString("LAST_NAME", "")!!

    var phone: String
        set(value) = pref.edit().putString("PHONE", value).apply()
        get() = pref.getString("PHONE", "")!!

    var password: String
        set(value) = pref.edit().putString("PASSWORD", value).apply()
        get() = pref.getString("PASSWORD", "")!!

//    var startScreen: StartFragmentEnum
//    set(value) = pref.edit().putInt("START_FRAGMENT", value.position).apply()
//    get() = pref.getInt("START_FRAGMENT", StartFragmentEnum.LOGIN.position).getStartFragment()
//

}