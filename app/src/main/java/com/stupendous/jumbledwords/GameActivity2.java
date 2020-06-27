package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsContentValues;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsContentValues;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity2 extends BaseActivity implements View.OnClickListener{
    private static final String TAG = GameActivity2.class.getName();
   // TextView tv_finalAnsArr[];
    TextView tv_firstLetter, tv_secondLetter, tv_thirdletter, tv_fourthLetter, tv_fifthLetter, tv_bestScore, tv_timer;
    private int index = 0;
    int id = 0;
    private int score=0;
    private TextView tv_currentScore;
    String correctWordsStr = "";
    int totalWordsCount = 50;
    ImageView iv_isCorrect;
    volatile long totalTime = 60000;
    Handler handler;
    Runnable runnableCode;
    ArrayList<String> randomGeneratedIdList = new ArrayList<>();
    private LinearLayout answerslayout;
    AnswerEditText etAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
       // insertTempJumbleWords();
       // insertTempCorrectWords();

        initBanner();

       /* Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);*/

      /*  tv_finalAnsArr = new TextView[]{(TextView)findViewById(R.id.txtVw1),
                (TextView)findViewById(R.id.txtVw2),
                (TextView)findViewById(R.id.txtVw3),
                (TextView)findViewById(R.id.txtVw4)};*/
        tv_firstLetter = (TextView) findViewById(R.id.tv_first);
        tv_secondLetter = (TextView) findViewById(R.id.tv_second);
        tv_thirdletter = (TextView) findViewById(R.id.tv_third);
        tv_fourthLetter = (TextView) findViewById(R.id.tv_fourth);
        tv_fifthLetter = (TextView) findViewById(R.id.tv_five);
        tv_currentScore = (TextView)findViewById(R.id.tv_currentScore);
        tv_bestScore = (TextView)findViewById(R.id.tv_totalScore);
        tv_timer = (TextView)findViewById(R.id.tv_timer);
        iv_isCorrect = (ImageView)findViewById(R.id.iv_isCorrect);
        answerslayout = (LinearLayout)findViewById(R.id.answersLayout);
        etAnswers = (AnswerEditText)findViewById(R.id.etAnswers);
        etAnswers.setOnPinEnteredListener(new AnswerEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(@Nullable CharSequence str) {
                validateWord();
            }
        });
       // tv_timer.startAnimation(anim);
        tv_bestScore.setText(SharedPrefs.getInstance().getIntPreference("score", 0) +"");
        findViewById(R.id.iv_quitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
                GameActivity2.this.finish();
            }
        });
        findViewById(R.id.restartLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showInterstitial();
               startActivity(new Intent(GameActivity2.this, GameActivity2.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        findViewById(R.id.ivClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAnswers.setText("");
                enableJumbleLetters(true);
            }
        });
        tv_firstLetter.setOnClickListener(this);
        tv_secondLetter.setOnClickListener(this);
        tv_thirdletter.setOnClickListener(this);
        tv_fourthLetter.setOnClickListener(this);
        tv_fifthLetter.setOnClickListener(this);
       // totalWordsCount = new JumblewordsSelection().level(2).query(this).getCount();
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
                    enableJumbleLetters(false);
                    if(score > (SharedPrefs.getInstance().getIntPreference("score", 0)))
                        SharedPrefs.getInstance().writeIntPreference("score", score);
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

    private void enableJumbleLetters(boolean enabled) {
        tv_firstLetter.setEnabled(enabled);
        tv_secondLetter.setEnabled(enabled);
        tv_thirdletter.setEnabled(enabled);
        tv_fourthLetter.setEnabled(enabled);
        tv_fifthLetter.setEnabled(enabled);
    }

    private void getJumledWords() {

        getRandomId();

        //JumblewordsCursor cursor = new JumblewordsSelection().id(id).level(1).query(this);
        Cursor cursor = getContentResolver().query(JumblewordsColumns.CONTENT_URI, null, JumblewordsColumns._ID +"=? AND " + JumblewordsColumns.LEVEL + "=?", new String[]{id+"", "2"}, null);

        if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
        {
            Log.e(TAG,"count:"+cursor.getCount());

            String word = cursor.getString(cursor.getColumnIndex(JumblewordsColumns.JUMBLE_WORD));
            char[] ch = word.toUpperCase().toCharArray();
            tv_firstLetter.setText(ch[0]+"");
            tv_secondLetter.setText(ch[1]+"");
            tv_thirdletter.setText(ch[2]+"");
            tv_fourthLetter.setText(ch[3]+"");
            tv_fifthLetter.setText(ch[4]+"");
        }
        else
            Toast.makeText(getApplicationContext(), getString(R.string.some_error_occured), Toast.LENGTH_LONG).show();
    }

    private void getRandomId() {
        Random r = new Random();
        id = r.nextInt(totalWordsCount) + 51;
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


    @Override
    public void onClick(View v) {
       /* tv_finalAnsArr[index].setText(((TextView)v).getText());
        index++;
        v.setEnabled(false);
        if(index == 4)
        {
            index = 0;
            validateWord();
        }*/
        v.setEnabled(false);
       StringBuilder sb = new StringBuilder("");
       sb.append(etAnswers.getText().toString());
       sb.append(((TextView)v).getText().toString());
       etAnswers.setText(sb.toString());

    }

    private void validateWord() {
        StringBuilder sb = new StringBuilder();
        sb.append(etAnswers.getText().toString());
        Cursor cursor = getContentResolver().query(CorrectwordsColumns.CONTENT_URI, null, CorrectwordsColumns.JUMBLE_WORD_ID +"=?", new String[]{id+""}, null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
             correctWordsStr = cursor.getString(cursor.getColumnIndex(CorrectwordsColumns.CORRECT_WORD));
        }
        if(correctWordsStr.contains(sb.toString().toUpperCase()) || correctWordsStr.contains(sb.toString().toLowerCase()))
        {
           // totalTime = totalTime + 1000;
            iv_isCorrect.setVisibility(View.VISIBLE);
            iv_isCorrect.setImageDrawable(ContextCompat.getDrawable(GameActivity2.this, R.drawable.correct));
            score = score + 1;

        }
        else
        {
            shakeView(GameActivity2.this, answerslayout);
            iv_isCorrect.setVisibility(View.VISIBLE);
            iv_isCorrect.setImageDrawable(ContextCompat.getDrawable(GameActivity2.this, R.drawable.incorrect));
        }

       etAnswers.setText("");
        getJumledWords();
        tv_currentScore.setText(score +"");
       enableJumbleLetters(true);

    }

    public static void shakeView(Context context, View viewToShake) {
        Animation anim = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.shake);
        viewToShake.setAnimation(anim);
        viewToShake.startAnimation(anim);
    }
}
