package com.example.capstone_frontend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [ChatRoomFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatRoomFragment : Fragment(),
    View.OnClickListener {
    var chatroom_et: EditText? = null
    var enter_btn: Button? = null
    //var enter_btn = findViewById<Button>(R.id.enter_btn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        val rootView =
            inflater.inflate(R.layout.fragment_chat_room, container, false)
        chatroom_et = rootView.findViewById(R.id.chatroom_et)
        enter_btn = rootView.findViewById(R.id.enter_btn)
        enter_btn?.setOnClickListener(this)
        return rootView
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.enter_btn -> if (chatroom_et!!.text.toString()
                    .trim { it <= ' ' }.length >= 0
            ) {
                Log.d(TAG, "입장처리")

// 원하는 데이터를 담을 객체
                val argu = Bundle()
                argu.putString("chatroom", chatroom_et!!.text.toString())

// 이동할 Fragment 선언
                val chatMsgFragment = ChatMsgFragment()

// 이동할 Fragment 에 데이터 객체 담기
                chatMsgFragment.arguments = argu
                activity!!.supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.fi_frag_container, chatMsgFragment, "CHATMSG")
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(activity, "채팅방 이름을 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "ChatRoomFragment"
        fun newInstance(param1: String?, param2: String?): ChatRoomFragment {
            val fragment = ChatRoomFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}