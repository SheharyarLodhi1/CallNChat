package com.shery.hblcaller.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.shery.hblcaller.HomeActivity
import com.shery.hblcaller.R
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chatroom.*


class ChatRoomActivity : AppCompatActivity(), View.OnClickListener {


    val TAG = ChatRoomActivity::class.java.simpleName


    var mSocket: Socket? = null
    var userName: String? = null
    //lateinit var roomName: String;


    val gson: Gson = Gson()

    //For setting the recyclerView.
    val chatList: ArrayList<Message> = arrayListOf();
    lateinit var chatRoomAdapter: ChatRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)


        send.setOnClickListener(this)
        leave.setOnClickListener(this)

        //Get the nickname and roomname from entrance activity.
        try {
            userName = intent.getStringExtra(HomeActivity.CHATUSERNAME)!!

            //roomName = intent.getStringExtra("roomName")!!
        } catch (e: Exception) {
            e.printStackTrace()
        }


        //Set Chatroom adapter

        chatRoomAdapter = ChatRoomAdapter(this, chatList);
        recyclerView.adapter = chatRoomAdapter;

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //Let's connect to our Chat room! :D
        try {
            //mSocket = IO.socket("http://10.0.2.2:3000")
            mSocket = IO.socket("http://192.168.0.110:8000/")
            Log.d("success", mSocket?.id()!!)

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        mSocket?.connect()
        mSocket?.on(Socket.EVENT_CONNECT, onConnect)
        mSocket?.on("newUserToChatRoom", onNewUser)
        mSocket?.on("updateChat", onUpdateChat)
        mSocket?.on("userLeftChatRoom", onUserLeft)
    }

    var onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat: Message = Message(leftUserName, "",  MessageType.USER_LEAVE.index)
        addItemToRecyclerView(chat)
    }

    var onUpdateChat = Emitter.Listener {
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        addItemToRecyclerView(chat)
    }

    var onConnect = Emitter.Listener {
        //val data = initialData(userName, roomName)
        try {
            val data = initialData(userName!!)
            val jsonData = gson.toJson(data)
            mSocket?.emit("subscribe", jsonData)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        //val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
        val chat = Message(name, "", MessageType.USER_JOIN.index)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
    }


    private fun sendMessage() {
        try {
            val content = editText.text.toString()
            //val sendData = SendMessage(userName, content, roomName)
            val sendData = SendMessage(userName!!, content)
            val jsonData = gson.toJson(sendData)
            mSocket?.emit("newMessage", jsonData)

            //val message = Message(userName, content, roomName, MessageType.CHAT_MINE.index)
            val message = Message(userName!!, content, MessageType.CHAT_MINE.index)
            addItemToRecyclerView(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addItemToRecyclerView(message: Message) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        runOnUiThread {
            chatList.add(message)
            chatRoomAdapter.notifyItemInserted(chatList.size)
            editText.setText("")
            recyclerView.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.send -> sendMessage()
            R.id.leave -> onDestroy()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //val data = initialData(userName, roomName)
        val data = initialData(userName!!)
        val jsonData = gson.toJson(data)
        mSocket?.emit("unsubscribe", jsonData)
        mSocket?.disconnect()
    }

}