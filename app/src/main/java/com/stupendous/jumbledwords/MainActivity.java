package com.stupendous.jumbledwords;

import static com.stupendous.jumbledwords.Utility.LEVEL_1_BEST_SCORE;
import static com.stupendous.jumbledwords.Utility.LEVEL_2_BEST_SCORE;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsCursor;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsSelection;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    private static final int UNLOCK_MEDIUM_LEVEL_SCORE = 20;
    private static final int UNLOCK_HARD_LEVEL_SCORE = 10;
    ContextWrapper cw ;
    //String DB_PATH ;
   // String DB_NAME = "words.db";
    TextView tv_Level1Score;
    TextView tv_Level2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cw =new ContextWrapper(getApplicationContext());
        SharedPrefs.getInstance().initialize(getApplicationContext());
        // DB_PATH =cw.getFilesDir().getAbsolutePath()+ "/databases/"; //edited to databases
        // DB_PATH ="/data/data/com.stupendous.jumbledwords/databases/"; //edited to databases

        initBanner();
        tv_Level1Score = (TextView) findViewById(R.id.tv_level1Score);
       // tv_Level1Score.setText(getString(R.string.level1_score, SharedPrefs.getInstance().getIntPreference(LEVEL_1_BEST_SCORE, 0)));
        tv_Level2Score = (TextView) findViewById(R.id.tv_level2Score);
      //  tv_Level2Score.setText(getString(R.string.level2_score, SharedPrefs.getInstance().getIntPreference(LEVEL_2_BEST_SCORE, 0)));

        findViewById(R.id.btnLevel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        ImageView gifImageView = findViewById(R.id.gif_image_view);
        Glide.with(this).load(R.drawable.light).into(gifImageView);

        findViewById(R.id.btnLevel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int score = SharedPrefs.getInstance().getIntPreference(LEVEL_1_BEST_SCORE, 0);

                if (score >= UNLOCK_MEDIUM_LEVEL_SCORE) {
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

//                int score = SharedPrefs.getInstance().getIntPreference(LEVEL_2_BEST_SCORE, 0);
//
//                if (score >= UNLOCK_HARD_LEVEL_SCORE) {
//                    startActivity(new Intent(MainActivity.this , GameActivity3.class));
//                }
//
//                else {
//                    new AlertDialog.Builder(MainActivity.this)
//                            .setTitle(R.string.dialogue_title)
//                            .setMessage(R.string.dialogue_message)
//                            .setPositiveButton(android.R.string.ok, null)
//                            .show();
//                }

                Toast.makeText(MainActivity.this , "Coming Soon" , Toast.LENGTH_SHORT).show();

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
        SharedPrefs.getInstance().initialize(getApplicationContext());

        if(tv_Level1Score != null && tv_Level2Score != null)
        {
            tv_Level1Score.setText(getString(R.string.level1_score, SharedPrefs.getInstance().getIntPreference(LEVEL_1_BEST_SCORE, 0)));
            tv_Level2Score.setText(getString(R.string.level2_score, SharedPrefs.getInstance().getIntPreference(LEVEL_2_BEST_SCORE, 0)));
        }

    }
}
