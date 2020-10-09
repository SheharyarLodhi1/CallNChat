package com.shery.hblcaller.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.shery.hblcaller.R
import kotlinx.android.synthetic.main.activity_entrance.*
import org.jetbrains.anko.startActivity

class EntranceActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        button.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
            when(p0?.id){
                R.id.button -> enterChatroom()
            }
    }

    private fun enterChatroom(){
        val userName = userName.text.toString()
        val roomName = roomname.text.toString()

        /*if(!roomName.isNullOrBlank()&&!userName.isNullOrBlank()) {
            startActivity<ChatRoomActivity>(
                "userName" to userName,
                "roomName" to roomName
            )
        }else{
            Toast.makeText(this,"Nickname and Roomname should be filled!",Toast.LENGTH_SHORT)
        }*/

        if(!userName.isNullOrBlank()) {
            startActivity<ChatRoomActivity>(
                "userName" to userName
              /*  "roomName" to roomName*/
            )
        }else{
            Toast.makeText(this,"Nickname and Roomname should be filled!",Toast.LENGTH_SHORT)
        }
    }
}
