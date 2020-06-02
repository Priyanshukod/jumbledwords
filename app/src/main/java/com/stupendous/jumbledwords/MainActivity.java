package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsCursor;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsSelection;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    ContextWrapper cw ;
    //String DB_PATH ;
   // String DB_NAME = "words.db";
    TextView tv_totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cw =new ContextWrapper(getApplicationContext());
        // DB_PATH =cw.getFilesDir().getAbsolutePath()+ "/databases/"; //edited to databases
        // DB_PATH ="/data/data/com.invincible.jumbledwords/databases/"; //edited to databases

        tv_totalScore = (TextView) findViewById(R.id.tv_totalScore);
        tv_totalScore.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0) +"\n Best Score");
        findViewById(R.id.iv_startGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        findViewById(R.id.iv_quitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!AppPreferences.getBooleanSharedPreference(this,AppPreferences.KEY_DB_COPIED,false))
            Utility.copyDataBase(this,this.openOrCreateDatabase("words.db", Context.MODE_PRIVATE,null).getPath());

        JumblewordsCursor cursor = new JumblewordsSelection().query(this);

        if(cursor!=null){
            Log.e(TAG,"Count:"+cursor.getCount());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_totalScore.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0) +"\n Best Score");

    }
}
