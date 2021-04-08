package com.example.capstone_frontend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var SignInIntent = Intent(this, SignInActivity::class.java)
        Handler().postDelayed({
            startActivity(SignInIntent)
        }, 2000)
        /* key 구하기
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
        */
        /*Push messages*/
        val test: MyFirebaseInstanceIDService = MyFirebaseInstanceIDService()
        test.onTokenRefresh()
        Log.d(
            "FCM Message TEST",
            "REFRESHED TOKEN IS RIGHT ABOVE !!!"
        )


    }
}