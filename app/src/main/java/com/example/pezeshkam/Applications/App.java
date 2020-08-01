package com.example.pezeshkam.Applications;
import com.parse.Parse;
import android.app.Application;
import android.util.Log;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("com.example.pezeshkam")
                // if defined
//                .clientKey("AIzaSyDmVyXq_yAc1Rf2k9Yn2xfjpQQX6Za7Fgo")
                .server("http://localhost:1337/parse/")
                .build()
        );
    }
}
