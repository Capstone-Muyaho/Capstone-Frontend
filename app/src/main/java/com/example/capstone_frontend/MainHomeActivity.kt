package com.example.capstone_frontend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_main_home.*
import java.io.IOException

class MainHomeActivity : AppCompatActivity() {
    lateinit var mSocket: Socket // for socket
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)

        setPermission()

        val db: DatabaseReference = Firebase.database.getReference("users")
        val id = intent.getStringExtra("id").toString()
        getToken(db, id)

        val type = intent.getStringExtra("type")
        val nickName = intent.getStringExtra("nickName")

        btn_profile.setOnClickListener {
            val profileIntent = Intent(this, MyProfileActivity::class.java)
            profileIntent.putExtra("type", type)
            profileIntent.putExtra("nickName", nickName)
            startActivity(profileIntent)
        }

        btn_friendlist.setOnClickListener {
            val friendListIntent = Intent(this, FriendListActivity::class.java)
            startActivity(friendListIntent)
        }

        btn_snaptalk.setOnClickListener {
            val snapTalkIntent = Intent(this, SnaptalkActivity::class.java)

            db.child(id).child("nickname").get().addOnSuccessListener {
                val nickName = it.value.toString()
                db.child(id).child("chatroom").get().addOnSuccessListener {
                    val chatRoom = it.value.toString()
                    snapTalkIntent.putExtra("username", nickName)
                    snapTalkIntent.putExtra("roomNumber", chatRoom)
                    startActivity(snapTalkIntent)
                }
            }

        }

        btn_emergency_call.setOnClickListener {
            try {
                var callIntent = Intent(Intent.ACTION_CALL)
                callIntent.setData(Uri.parse("tel:119"))
                startActivity(callIntent)
            } catch (e: Exception) {
                Log.d("call", "전화 실패")   
            }
        }
    }

    private fun setPermission() {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                // Toast.makeText(this@MainHomeActivity, "권한이 허용 되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { // 설정해놓은 위험 권한들 중 거부한 경우 수행
                Toast.makeText(this@MainHomeActivity, "권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("긴급 전화를 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] -> [권한] 항목에서 허용해주세요.")
            .setPermissions(
                android.Manifest.permission.CALL_PHONE
            )
            .check()
    }

    public fun getToken(db: DatabaseReference, id: String) {
        Thread(Runnable {
            try {
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.i("로그 : ", "getInstanceId failed", task.exception)
                            return@OnCompleteListener
                        }
                        val token = task.result?.token
                        db.child(id).child("nickname").get().addOnSuccessListener {
                            val username = it.value.toString()
                            try {
                                mSocket = IO.socket("http://49.50.162.112:80")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.d("fail", "Failed to connect")
                            }
                            mSocket.connect()
                            var test = "for testing"
                            mSocket.emit("token", gson.toJson(TokenItem(token.toString(),id.toString())))
                            Log.d(
                                "TOKEN", " added to server"
                            )
                        }
                    })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }
}
