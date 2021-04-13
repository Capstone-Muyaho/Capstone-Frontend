package com.example.capstone_frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MyChatActivity : AppCompatActivity() {
    lateinit var frgChatRoom : ChatRoomFragment
    lateinit var frgChatMsg : ChatMsgFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_chat)

        val nickName = intent.getStringExtra("nickName")
        val bundle = Bundle();
        bundle.putString("nickName", nickName)

        initFragment()
        /*
        frgChatRoom.setArguments(bundle)
        frgChatRoom = ChatRoomFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fi_frag_container, frgChatRoom).commit()
         */
    }

    fun initFragment() {
        frgChatRoom = ChatRoomFragment()
       // frgChatMsg = ChatMsgFragment()

        commitFragment(frgChatRoom)
        /*frgChatMsg.enter_btn.setOnClickListener(){
            commitFragment(frgChatMsg)
        } */
    }

    fun commitFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fi_frag_container, fragment).commit()
        //supportFragmentManager.beginTransaction().replace(R.id.fi_frag_container1, fragment).commit()
    }

}