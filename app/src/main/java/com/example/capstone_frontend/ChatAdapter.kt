package com.example.capstone_frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ChatAdapter(private val mValues: List<ChatMsgVO>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_chat_msg, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val vo = mValues[position]
        if (mValues[position].userid == Se_Application.Localdb?.get_dataS("userid")) {
            holder.other_cl.visibility = View.GONE
            holder.my_cl.visibility = View.VISIBLE
            holder.date_tv2.text = vo.crt_dt
            holder.content_tv2.text = vo.content
        } else {
            holder.other_cl.visibility = View.VISIBLE
            holder.my_cl.visibility = View.GONE
            holder.userid_tv.text = vo.userid
            holder.date_tv.text = vo.crt_dt
            holder.content_tv.text = vo.content
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var my_cl: ConstraintLayout
        var other_cl: ConstraintLayout
        var userid_tv: TextView
        var date_tv: TextView
        var content_tv: TextView
        var date_tv2: TextView
        var content_tv2: TextView

        init {
            my_cl = view.findViewById(R.id.my_cl)
            other_cl = view.findViewById(R.id.other_cl)
            userid_tv = view.findViewById(R.id.userid_tv)
            date_tv = view.findViewById(R.id.date_tv)
            content_tv = view.findViewById(R.id.content_tv)
            date_tv2 = view.findViewById(R.id.date_tv2)
            content_tv2 = view.findViewById(R.id.content_tv2)
        }
    }

}