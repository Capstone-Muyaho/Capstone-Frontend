package com.example.capstone_frontend

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by user on 2016-12-20.
 */
class Se_LocalDbConnector(context: Context) {
    private val db_name = "userdata"
    var Localdb: SharedPreferences? = null
    fun get_dataS(Fn: String?): String? {
        return Localdb!!.getString(Fn, "")
    }

    fun get_dataB(Fn: String?): Boolean {
        return Localdb!!.getBoolean(Fn, false)
    }

    fun get_dataI(Fn: String?): Int {
        return Localdb!!.getInt(Fn, -1)
    }

    fun get_dataL(Fn: String?): Long {
        return Localdb!!.getLong(Fn, -1)
    }

    fun set_dataS(Fn: String?, `val`: String?) {
        val se = Localdb!!.edit()
        se.putString(Fn, `val`)
        se.commit()
    }

    fun set_dataB(Fn: String?, `val`: Boolean) {
        val se = Localdb!!.edit()
        se.putBoolean(Fn, `val`)
        se.commit()
    }

    fun set_dataI(Fn: String?, `val`: Int) {
        val se = Localdb!!.edit()
        se.putInt(Fn, `val`)
        se.commit()
    }

    fun set_dataL(Fn: String?, `val`: Long) {
        val se = Localdb!!.edit()
        se.putLong(Fn, `val`)
        se.commit()
    }

    init {
        Localdb = context.getSharedPreferences(db_name, 0)
    }
}