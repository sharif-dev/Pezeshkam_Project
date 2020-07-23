package com.example.pezeshkam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pezeshkam.Adapters.HomepageAdapter;
import com.example.pezeshkam.Models.DoctorCard;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        ArrayList<DoctorCard> doctorCards = new ArrayList<>();
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));
        doctorCards.add(new DoctorCard("alireza", "09302377651", "sdf", "روانشناس"));

        listView = findViewById(R.id.list1);
        ArrayAdapter<DoctorCard> adapter = new HomepageAdapter(this, 0, doctorCards);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}