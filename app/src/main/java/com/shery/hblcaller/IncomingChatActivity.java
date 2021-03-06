package com.shery.hblcaller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class IncomingChatActivity extends ChatActivity implements Observer {

    private String fromUsername;
    private boolean answered = false;
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000};
        v.vibrate(pattern, 0);

        fromUsername = intent.getStringExtra(HomeActivity.FROM_USERNAME);
        signaller = Signaller.incomingChatSignaller;
        signaller.setObserver(this);
        setContentView(R.layout.activity_incoming_call);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.from_username_calling_text), fromUsername);
        TextView fromUsernameText = (TextView) findViewById(R.id.fromUsernameCallingText);
        fromUsernameText.setText(text);

        findViewById(R.id.acceptButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.acceptButton).setEnabled(false);
                findViewById(R.id.declineButton).setEnabled(false);
                v.cancel();
                openChatDisplayActivity(fromUsername);
                answered = true;
                finish();
            }
        });

        findViewById(R.id.declineButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.acceptButton).setEnabled(false);
                findViewById(R.id.declineButton).setEnabled(false);
                v.cancel();
                signaller.sendInitialChatAnswer(ChatStatus.DECLINED);
                answered = true;
                finish();
            }
        });

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!answered) {
                    findViewById(R.id.acceptButton).setEnabled(false);
                    findViewById(R.id.declineButton).setEnabled(false);
                    v.cancel();
                    signaller.sendInitialChatAnswer(ChatStatus.DECLINED);
                    finish();
                }
            }
        }, 17500);

    }

    @Override
    public void onResume() {
        super.onResume();
        signaller.setObserver(this);
    }

    void displayErrorDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void update(Observable observable, Object data) {
        try {
            JSONObject json = new JSONObject((String) data);
            if (json.has("chatStatus") && json.getString("chatStatus").equals(ChatStatus.CANCELLED.name())) {
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}