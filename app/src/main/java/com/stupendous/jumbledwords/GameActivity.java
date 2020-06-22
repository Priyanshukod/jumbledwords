package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsContentValues;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsContentValues;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsCursor;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsSelection;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = GameActivity.class.getName();
    TextView tv_finalAnsArr[];
    TextView tv_firstLetter, tv_secondLetter, tv_thirdletter, tv_fourthLetter, tv_bestScore, tv_timer;
    private int index = 0;
    int id = 0;
    private int score=0;
    private TextView tv_currentScore;
    String correctWordsStr = "";
    int totalWordsCount;
    ImageView iv_isCorrect;
    volatile long totalTime = 60000;
    Handler handler;
    Runnable runnableCode;
    ArrayList<String> randomGeneratedIdList = new ArrayList<>();
    private LinearLayout answerslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
       // insertTempJumbleWords();
       // insertTempCorrectWords();

        initBanner();

       /* Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);*/

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
        tv_timer = (TextView)findViewById(R.id.tv_timer);
        iv_isCorrect = (ImageView)findViewById(R.id.iv_isCorrect);
        answerslayout = (LinearLayout)findViewById(R.id.answersLayout);
       // tv_timer.startAnimation(anim);
        tv_bestScore.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0) +"");
        findViewById(R.id.iv_quitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
                GameActivity.this.finish();
            }
        });
        findViewById(R.id.restartLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showInterstitial();
               startActivity(new Intent(GameActivity.this, GameActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        tv_firstLetter.setOnClickListener(this);
        tv_secondLetter.setOnClickListener(this);
        tv_thirdletter.setOnClickListener(this);
        tv_fourthLetter.setOnClickListener(this);
        totalWordsCount = new JumblewordsSelection().query(this).getCount();
        // Create the Handler object (on the main thread by default)
         handler = new Handler();
// Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 1 seconds
                if( totalTime > 0 ) {
                    tv_timer.setText("Seconds remaining: " + totalTime / 1000);
                    totalTime = totalTime - 1000;
                    handler.postDelayed(runnableCode, 1000);
                } else
                {
                    iv_isCorrect.setVisibility(View.GONE);
                    tv_firstLetter.setEnabled(false);
                    tv_secondLetter.setEnabled(false);
                    tv_thirdletter.setEnabled(false);
                    tv_fourthLetter.setEnabled(false);
                    if(score > (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0)))
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("score", score).commit();
                    tv_timer.setText("Times Up!");
                    handler.removeCallbacks(runnableCode);
                }
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
        /*new CountDownTimer(totalTime, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tv_timer.setText("Times Up!");
                iv_isCorrect.setVisibility(View.GONE);
                tv_firstLetter.setEnabled(false);
                tv_secondLetter.setEnabled(false);
                tv_thirdletter.setEnabled(false);
                tv_fourthLetter.setEnabled(false);
                if(score > (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("score", 0)))
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("score", score).commit();
            }

        }.start();*/
        getJumledWords();
    }

    private void getJumledWords() {

        getRandomId();

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

    private void getRandomId() {
        Random r = new Random();
        id = r.nextInt(totalWordsCount) + 1;
        if(randomGeneratedIdList.contains(""+id))
        {
            if(randomGeneratedIdList.size() == totalWordsCount)
                return;
            getRandomId();
        }
        else
        {
            randomGeneratedIdList.add(id+"");
            return;
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
       // Cursor cursor = getContentResolver().query(CorrectwordsColumns.CONTENT_URI, null, CorrectwordsColumns.JUMBLE_WORD_ID +"=? AND "+CorrectwordsColumns.CORRECT_WORD+"=?", new String[]{id+"", sb.toString()}, null);
        Cursor cursor = getContentResolver().query(CorrectwordsColumns.CONTENT_URI, null, CorrectwordsColumns.JUMBLE_WORD_ID +"=?", new String[]{id+""}, null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
             correctWordsStr = cursor.getString(cursor.getColumnIndex(CorrectwordsColumns.CORRECT_WORD));
        }
        if(correctWordsStr.contains(sb.toString().toUpperCase()) || correctWordsStr.contains(sb.toString().toLowerCase()))
        {
           // totalTime = totalTime + 1000;
            iv_isCorrect.setVisibility(View.VISIBLE);
            iv_isCorrect.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, R.drawable.correct));
            score = score + 1;
           /* for (int i = 0; i < tv_finalAnsArr.length; i++) {
                tv_finalAnsArr[i].setText("");
            }
            getJumledWords();
            tv_currentScore.setText(score +"");
            tv_firstLetter.setEnabled(true);
            tv_secondLetter.setEnabled(true);
            tv_thirdletter.setEnabled(true);
            tv_fourthLetter.setEnabled(true);*/
        }
        else
        {
            shakeView(GameActivity.this, answerslayout);
            iv_isCorrect.setVisibility(View.VISIBLE);
            iv_isCorrect.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, R.drawable.incorrect));
        }
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

    public static void shakeView(Context context, View viewToShake) {
        Animation anim = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.shake);
        viewToShake.setAnimation(anim);
        viewToShake.startAnimation(anim);
    }
}
