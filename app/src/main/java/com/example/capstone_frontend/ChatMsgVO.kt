package com.example.capstone_frontend

class ChatMsgVO {
    var userid: String? = null
    var crt_dt: String? = null
    var content: String? = null

    constructor() {}
    constructor(userid: String?, crt_dt: String?, content: String?) {
        this.userid = userid
        this.crt_dt = crt_dt
        this.content = content
    }


    override fun toString(): String {
        return "ChatMsgVO{" +
                "userid='" + userid + '\'' +
                ", crt_dt='" + crt_dt + '\'' +
                ", content='" + content + '\'' +
                '}'
    }
}