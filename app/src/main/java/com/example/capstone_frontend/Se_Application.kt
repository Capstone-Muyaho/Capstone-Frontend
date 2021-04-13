package com.example.capstone_frontend

import android.app.Application
import android.view.animation.AlphaAnimation
import java.util.regex.Matcher
import java.util.regex.Pattern

class Se_Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Init_Value()
    }

    fun Init_Value() {
        Localdb = Se_LocalDbConnector(this.applicationContext)
        Server_URL = "13.209.236.238"
    }

    companion object {
        private const val TAG = "Se_Application"
        var Server_URL: String? = null
        var Localdb: Se_LocalDbConnector? = null
        var ClickedAnimation = AlphaAnimation(1f, 0.2f)
        fun emailValidator(email: String?): Boolean {
            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}