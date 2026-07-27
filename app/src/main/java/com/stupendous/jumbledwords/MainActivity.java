package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.stupendous.jumbledwords.provider.JumbleDbSQLiteOpenHelper;
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
        SharedPrefs.getInstance().initialize(getApplicationContext());
        // DB_PATH =cw.getFilesDir().getAbsolutePath()+ "/databases/"; //edited to databases
        // DB_PATH ="/data/data/com.stupendous.jumbledwords/databases/"; //edited to databases

        initBanner();
        tv_totalScore = (TextView) findViewById(R.id.tv_totalScore);
        tv_totalScore.setText(getString(R.string.best_score, SharedPrefs.getInstance().getIntPreference("score", 0)));

        findViewById(R.id.btnLevel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        findViewById(R.id.btnLevel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int score = SharedPrefs.getInstance().getIntPreference("score", 0);

                if (score >= 25) {
                    startActivity(new Intent(MainActivity.this, GameActivity2.class));
                }

                else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.dialogue_title)
                            .setMessage(R.string.dialogue_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
            }
        });

        findViewById(R.id.btnLevel3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int score = SharedPrefs.getInstance().getIntPreference("score", 0);

                if (score >= 5) {
                    startActivity(new Intent(MainActivity.this , GameActivity3.class));
                }

                else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.dialogue_title)
                            .setMessage(R.string.dialogue_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }

            }
        });

        /*findViewById(R.id.iv_quitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        JumblewordsCursor cursor = new JumblewordsSelection().query(this);

        if(!AppPreferences.getBooleanSharedPreference(this,AppPreferences.KEY_DB_COPIED,false) || cursor== null || cursor.getCount() == 0)
            Utility.copyDataBase(this,this.openOrCreateDatabase("words.db", Context.MODE_PRIVATE,null).getPath());

        JumblewordsCursor cursor2 = new JumblewordsSelection().query(this);

        if(cursor2!=null){
            Log.e(TAG,"Count:"+cursor2.getCount());
            }


    }

    @Override
    protected void onResume() {
        super.onResume();
       // tv_totalScore.setText(getString(R.string.best_score, SharedPrefs.getInstance().getIntPreference("score", 0)));

    }
}
