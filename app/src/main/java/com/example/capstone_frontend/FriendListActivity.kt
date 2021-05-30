package com.example.capstone_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_friend_list.*

class FriendListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userReference: DatabaseReference = database.getReference("users")
        val db: DatabaseReference = Firebase.database.getReference("users")
        var userSearchList = ArrayList<User>()
        var userSearch: User? = null
        var index = 0
        var friendNick: String? = ""
        var friendType: String?  = ""

        UserApiClient.instance.me { user, error ->
            if(error != null) {
                Log.e("TAG", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                val id = user.id.toString()

                db.child(id).child("nickname").get().addOnSuccessListener {
                    val myNick = it.value.toString()
                    db.child(id).child("friend").get().addOnSuccessListener {
                        val myFriend = it.value.toString()

                        userReference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                userSearchList.clear()
                                index = 0

                                for (i: DataSnapshot in snapshot.children) {
                                    userSearch = i.getValue(User::class.java)
                                    userSearchList.add(userSearch!!)

                                    if (myFriend != "" && myFriend == userSearchList[index].nickname.toString()) {
                                        if(userSearchList[index].friend.toString() == myNick) {
                                            friendNick = myFriend
                                            if(userSearchList[index].type.toString() == "P") {
                                                friendType = "부모"
                                            } else if(userSearchList[index].type.toString() =="C") {
                                                friendType = "자녀"
                                            }
                                        } else if(userSearchList[index].friend.toString() == "") {
                                            Toast.makeText(this@FriendListActivity, "상대방 요청이 필요합니다.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    index++
                                }
                                val friendList = arrayListOf(
                                        friendNick?.let { it1 -> friendType?.let { it2 -> FriendInformation(it1, it2) } }
                                )
                                rv_friend.layoutManager = LinearLayoutManager(this@FriendListActivity, LinearLayoutManager.VERTICAL, false)
                                rv_friend.setHasFixedSize((true))
                                rv_friend.adapter = FriendListAdapter(friendList)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@FriendListActivity, "읽어오기 실패.", Toast.LENGTH_SHORT).show()
                            }

                        })

                    }
                }

            }
        }

        btn_intent_add_friend.setOnClickListener() {
            val friendIntent = Intent(this, AddFriendActivity::class.java)
            startActivity(friendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        btn_friend_request.setOnClickListener {
             UserApiClient.instance.me { user, error ->
                val requestIntent = Intent(this, FriendRequestActivity::class.java)
                startActivity(requestIntent)
            }
        }
    }
}


