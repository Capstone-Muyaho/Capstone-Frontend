package com.example.capstone_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_parent_main.*

class ParentMainActivity : AppCompatActivity() {

    lateinit var frgChatRoom : ChatRoomFragment
    lateinit var frgChatMsg : ChatMsgFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_main)

        val type = intent.getStringExtra("type")
        val nickName = intent.getStringExtra("nickName")

        btnMyProfile.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }

        btnChat.setOnClickListener {
            val intent = Intent(this,MyChatActivity::class.java)
            //intent.putExtra("nickName", nickName)
            startActivity(intent)

        }
    }
    /*
    fun initFragment() {
        frgChatRoom = ChatRoomFragment()
        //frgChatMsg = ChatMsgFragment()

        commitFragment(frgChatRoom)
        //commitFragment(frgChatMsg)
    }

    fun commitFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fi_frag_container, fragment).commit()
    }

     */
}