package com.example.pezeshkam.Threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import static com.example.pezeshkam.Activities.Homepage.PROFILE_PICTURE_NOT_RECEIVED;

public class HDatasThread extends Thread {
    private Handler handler;
    private int userID;
    public HDatasThread(int userID, Handler handler) {
        this.handler = handler;
        this.userID = userID;
    }

    @Override
    public void run() {
        super.run();
        ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", 1337);
        gameScore.put("playerName", "Sean Plott");
        gameScore.put("cheatMode", false);
        gameScore.saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        Message message = new Message();
        message.what = PROFILE_PICTURE_NOT_RECEIVED;
        message.obj = new Object();
        handler.sendMessage(message);
        query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.i("ParseObject", object.toString());
                    // object will be your game score
                } else {
                    Log.i("Parse null", object.toString());
                    // something went wrong
                }
            }
        });

    }
}
