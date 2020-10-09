package com.shery.hblcaller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ChatActivity extends AppCompatActivity {
    Intent intent;
    String username;
    String chatType;
    Signaller signaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        chatType = intent.getStringExtra(HomeActivity.CHAT_TYPE);
        username = intent.getStringExtra(HomeActivity.USERNAME);

    }

    void openChatDisplayActivity(String toUsername) {
        Intent intent = null;
        if(chatType.equals(ChatType.VOICE_CHAT.name())) {
            intent = new Intent(this, VoiceChatDisplayActivity.class);
        } else if(chatType.equals(ChatType.VIDEO_CHAT.name())) {
            intent = new Intent(this, VideoChatDisplayActivity.class);
        }
        intent.putExtra(HomeActivity.USERNAME, username);
        intent.putExtra(StartChatActivity.TO_USERNAME, toUsername);
        Log.v(username, "STARTING CHAT DISPLAY ACTIVITY");
        startActivityForResult(intent, 1);
    }
}
