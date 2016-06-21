package com.stupendous.jumbledwords;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    ContextWrapper cw ;
    String DB_PATH ;
    String DB_NAME = "words.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cw =new ContextWrapper(getApplicationContext());
        // DB_PATH =cw.getFilesDir().getAbsolutePath()+ "/databases/"; //edited to databases
         DB_PATH ="/data/data/com.stupendous.jumbledwords/databases/"; //edited to databases

        TextView tv_totalScore = (TextView) findViewById(R.id.tv_totalScore);
        tv_totalScore.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0) +"\n Best Score");
        findViewById(R.id.iv_startGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
       // copyDataBase();
    }

    private void copyDataBase()
    {
        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try
        {
            myInput =getApplicationContext().getAssets().open(DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput =new FileOutputStream(DB_PATH+ DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
