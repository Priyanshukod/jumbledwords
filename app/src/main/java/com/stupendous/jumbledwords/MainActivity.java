package com.stupendous.jumbledwords;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_totalScore = (TextView) findViewById(R.id.tv_totalScore);
        tv_totalScore.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0) +"\n Best Score");
        findViewById(R.id.iv_startGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
    }
}
