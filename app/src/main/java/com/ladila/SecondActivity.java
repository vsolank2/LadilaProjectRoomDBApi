package com.ladila;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ladila.project.Pojo.DBPojo.DBResult;
import com.ladila.project.R;

public class SecondActivity extends AppCompatActivity {
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Second Screen");
        tvData = findViewById(R.id.tvData);
        Intent intent = getIntent();
        DBResult dbResult = intent.getParcelableExtra("myObj");

        if (dbResult != null) {
            tvData.setText(new Gson().toJson(dbResult));
        } else {
            Toast.makeText(this, "Something Wrong.", Toast.LENGTH_SHORT).show();
        }
    }
}