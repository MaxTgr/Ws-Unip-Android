package com.tgr.wservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void Get(View view) {
        Intent intentGet = new Intent(this, GetActivity.class);
        startActivity(intentGet);
    }

    public void Send(View view) {
        Intent intentPut = new Intent(this, PostActivity.class);
        startActivity(intentPut);
    }
}
