package com.example.capstone_frontend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class ChatMsgFragment : Fragment(),
    View.OnClickListener {
    // 로그용 TAG
    private val TAG = javaClass.simpleName

    // 채팅을 입력할 입력창과 전송 버튼
    var content_et: EditText? = null
    var send_iv: ImageView? = null
    // var send_iv = findViewById(R.id.send_iv) as ImageView

    // 채팅 내용을 뿌려줄 RecyclerView 와 Adapter
    var rv: RecyclerView? = null

    var mAdapter: ChatAdapter? = null

    // 채팅 방 이름
    var chatroom: String? = ""

    // 채팅 내용을 담을 배열
    var msgList: MutableList<ChatMsgVO> = ArrayList()

    // FirebaseDatabase 연결용 객체들
    var database = FirebaseDatabase.getInstance()
    var myRef: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_msg, container, false)
        content_et = view.findViewById(R.id.content_et)
        send_iv = view.findViewById(R.id.send_iv)
        rv = view.findViewById(R.id.rv)
        send_iv?.setOnClickListener(this)

// ChatRoomFragment 에서 받는 채팅방 이름
        chatroom = arguments!!.getString("chatroom")
        mAdapter = ChatAdapter(msgList)
        rv?.setLayoutManager(LinearLayoutManager(activity))
        rv?.setAdapter(mAdapter)

// Firebase Database 초기
        myRef = database.getReference(chatroom!!)

// Firebase Database Listener 붙이기
        myRef!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
// Firebase 의 해당 DB 에 값이 추가될 경우 호출, 생성 후 최초 1번은 실행됨
                Log.d(TAG, "onChild added")
                Log.d(
                    TAG,
                    "onChild = " + dataSnapshot.getValue(ChatMsgVO::class.java)
                        .toString()
                )

// Database 의 정보를 ChatMsgVO 객체에 담음
                val chatMsgVO = dataSnapshot.getValue(ChatMsgVO::class.java)!!
                msgList.add(chatMsgVO)

// 채팅 메시지 배열에 담고 RecyclerView 다시 그리기
                mAdapter = ChatAdapter(msgList)
                rv?.setAdapter(mAdapter)
                rv?.scrollToPosition(msgList.size - 1)
                Log.d(TAG, msgList.size.toString() + "")
            }

            override fun onChildChanged(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        Log.d(TAG, "chatroom = $chatroom")
        return view
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.send_iv -> if (content_et!!.text.toString().trim { it <= ' ' }.length >= 1) {
                Log.d(TAG, "입력처리")
                val df =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

// Database 에 저장할 객체 만들기
                val msgVO = ChatMsgVO(
                    Se_Application.Localdb?.get_dataS("nickname"), /////userid
                    df.format(Date()).toString(),
                    content_et!!.text.toString().trim { it <= ' ' }
                )

// 해당 DB 에 값 저장시키기
                myRef!!.push().setValue(msgVO)

// 입력 필드 초기화
                content_et!!.setText("")
            } else {
                Toast.makeText(activity, "메시지를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        // TODO: Customize parameter initialization
        fun newInstance(): ChatMsgFragment {
            return ChatMsgFragment()
        }
    }

    /*
    companion object {
        private const val TAG = "ChatMsgFragment"
        fun newInstance(param1: String?, param2: String?): ChatMsgFragment {
            val fragment = ChatMsgFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }*/
}