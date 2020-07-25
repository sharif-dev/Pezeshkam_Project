package com.example.pezeshkam.Threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.logging.LogRecord;

import static com.example.pezeshkam.MainActivity.EMPTY_RESULT;
import static com.example.pezeshkam.MainActivity.NON_EMPTY_RESULT;
import static com.example.pezeshkam.MainActivity.RESQUEST_FAILED;

public class SearchThread extends Thread {
    private String searchField;
    private Handler handler;
    public SearchThread(String searchField, Handler handler) {
        this.searchField = searchField;
        this.handler = handler;
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
        message.what = RESQUEST_FAILED;
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
