package com.stupendous.jumbledwords;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsContentValues;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsCursor;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsSelection;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsContentValues;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsCursor;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsSelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = GameActivity.class.getName();
    TextView tv_finalAnsArr[];
    TextView tv_firstLetter, tv_secondLetter, tv_thirdletter, tv_fourthLetter, tv_bestScore;
    private int index = 0;
    HashMap<String,ArrayList<WordsModel>> wordsArr = new HashMap<>();
    int id = 0;
    private int score=0;
    private TextView tv_currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
       // insertTempJumbleWords();
       // insertTempCorrectWords();


        tv_finalAnsArr = new TextView[]{(TextView)findViewById(R.id.txtVw1),
                (TextView)findViewById(R.id.txtVw2),
                (TextView)findViewById(R.id.txtVw3),
                (TextView)findViewById(R.id.txtVw4)};
        tv_firstLetter = (TextView) findViewById(R.id.tv_first);
        tv_secondLetter = (TextView) findViewById(R.id.tv_second);
        tv_thirdletter = (TextView) findViewById(R.id.tv_third);
        tv_fourthLetter = (TextView) findViewById(R.id.tv_fourth);
        tv_currentScore = (TextView)findViewById(R.id.tv_currentScore);
        tv_bestScore = (TextView)findViewById(R.id.tv_totalScore);
        tv_bestScore.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0) +"");
        findViewById(R.id.iv_quitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.this.finish();
            }
        });
        tv_firstLetter.setOnClickListener(this);
        tv_secondLetter.setOnClickListener(this);
        tv_thirdletter.setOnClickListener(this);
        tv_fourthLetter.setOnClickListener(this);
        getJumledWords();
    }

    private void getJumledWords() {
        Random r = new Random();
        id = r.nextInt(4) + 1;

        JumblewordsCursor cursor = new JumblewordsSelection().id(id).query(this);

        if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
        {
            Log.e(TAG,"count:"+cursor.getCount());

               /* while(!cursor.isAfterLast()){
                    Log.e(TAG,"Id:"+cursor.getId()+",word:"+cursor.getJumbleWord());
                    cursor.moveToNext();
                }*/

            // JumblewordsCursor cursor1 = new JumblewordsCursor(cursor);
            String word = cursor.getJumbleWord();
            char[] ch = word.toCharArray();
            tv_firstLetter.setText(ch[0]+"");
            tv_secondLetter.setText(ch[1]+"");
            tv_thirdletter.setText(ch[2]+"");
            tv_fourthLetter.setText(ch[3]+"");
        }
    }

    private void insertTempJumbleWords() {
        ArrayList<String> wordList = new ArrayList<>();
        wordList.add("ilmk");
        wordList.add("olop");
        wordList.add("isht");
        wordList.add("disa");
        JumblewordsContentValues values = new JumblewordsContentValues();
        for(int i = 0 ;i< wordList.size() ;i++)
        {
            values.putJumbleWord(wordList.get(i));
            values.insert(this);
        }
    }

    private void insertTempCorrectWords() {
        /*ArrayList<String> wordList = new ArrayList<>();
        wordList.add("milk");
        wordList.add("polo");
        wordList.add("pool");
        wordList.add("loop");
        wordList.add("this");
        wordList.add("shit");
        wordList.add("aids");
        wordList.add("said");
        */CorrectwordsContentValues values = new CorrectwordsContentValues();

        values.putJumbleWordId(1);
        values.putCorrectWord("milk");
        values.insert(this);

        values.putJumbleWordId(2);
        values.putCorrectWord("polo");
        values.insert(this);

        values.putJumbleWordId(2);
        values.putCorrectWord("pool");
        values.insert(this);

        values.putJumbleWordId(2);
        values.putCorrectWord("loop");
        values.insert(this);

        values.putJumbleWordId(3);
        values.putCorrectWord("this");
        values.insert(this);

        values.putJumbleWordId(3);
        values.putCorrectWord("shit");
        values.insert(this);

        values.putJumbleWordId(4);
        values.putCorrectWord("said");
        values.insert(this);

        values.putJumbleWordId(4);
        values.putCorrectWord("aids");
        values.insert(this);
    }

    @Override
    public void onClick(View v) {
        tv_finalAnsArr[index].setText(((TextView)v).getText());
        index++;
        v.setEnabled(false);
        if(index == 4)
        {
            index = 0;
            validateWord();
        }
    }

    private void validateWord() {
        StringBuilder sb = new StringBuilder();
        for(int i =0 ; i<tv_finalAnsArr.length ; i++)
        {
            sb = sb.append(tv_finalAnsArr[i].getText().toString());
        }
       // CorrectwordsSelection selection = new CorrectwordsSelection();
        //CorrectwordsCursor cursr = selection.jumbleWordId(id).
        Cursor cursor = getContentResolver().query(CorrectwordsColumns.CONTENT_URI, null, CorrectwordsColumns.JUMBLE_WORD_ID +"=? AND "+CorrectwordsColumns.CORRECT_WORD+"=?", new String[]{id+"", sb.toString()}, null);
        if(cursor != null && cursor.getCount()>0)
        {
            score = score + 1;
            for (int i = 0; i < tv_finalAnsArr.length; i++) {
                tv_finalAnsArr[i].setText("");
            }
            getJumledWords();
            tv_currentScore.setText(score +"");
            tv_firstLetter.setEnabled(true);
            tv_secondLetter.setEnabled(true);
            tv_thirdletter.setEnabled(true);
            tv_fourthLetter.setEnabled(true);
        }
        else
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("score", score).commit();


            Toast.makeText(getApplicationContext(), "You lost", Toast.LENGTH_LONG).show();
            GameActivity.this.finish();
        }

    }
}
