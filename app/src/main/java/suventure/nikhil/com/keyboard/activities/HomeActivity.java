package suventure.nikhil.com.keyboard.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import suventure.nikhil.com.chatlibrary.model.Chat;
import suventure.nikhil.com.keyboard.R;
import suventure.nikhil.com.keyboard.adapter.ChatAdapter;
import suventure.nikhil.com.keyboard.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    Context context;
    int duration=255;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=HomeActivity.this;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();


    }
}
