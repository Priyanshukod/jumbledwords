package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsContentValues;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsContentValues;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsCursor;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsSelection;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = GameActivity.class.getName();
   // TextView tv_finalAnsArr[];
    TextView tv_firstLetter, tv_secondLetter, tv_thirdletter, tv_fourthLetter, tv_bestScore, tv_timer;
    private int index = 0;
    int id = 0;
    private int score=0;
    private TextView tv_currentScore;
    String correctWordsStr = "";
    int totalWordsCount = 300;
    ImageView iv_isCorrect;
    volatile long totalTime = 60000  ;
    Handler handler;
    Runnable runnableCode;
    ArrayList<String> randomGeneratedIdList = new ArrayList<>();
    private LinearLayout answerslayout;

    ImageView iv_clear;
    AnswerEditText etAnswers;

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

      /*  tv_finalAnsArr = new TextView[]{(TextView)findViewById(R.id.txtVw1),
                (TextView)findViewById(R.id.txtVw2),
                (TextView)findViewById(R.id.txtVw3),
                (TextView)findViewById(R.id.txtVw4)};*/
        iv_clear = (ImageView)findViewById(R.id.ivClear);
        tv_firstLetter = (TextView) findViewById(R.id.tv_first);
        tv_secondLetter = (TextView) findViewById(R.id.tv_second);
        tv_thirdletter = (TextView) findViewById(R.id.tv_third);
        tv_fourthLetter = (TextView) findViewById(R.id.tv_fourth);
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
        tv_bestScore.setText(SharedPrefs.getInstance().getIntPreference(Utility.LEVEL_1_BEST_SCORE, 0) +"");

        findViewById(R.id.iv_quitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    showInterstitial();
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

        iv_clear.setOnClickListener(new View.OnClickListener() {
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
 /*       Cursor cursor =  getContentResolver().query(JumblewordsColumns.CONTENT_URI, null, JumblewordsColumns.LEVEL + "=?", new String[]{"1"}, null);
        if(cursor != null  && cursor.getCount() > 0)
            totalWordsCount = cursor.getCount();*/
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
                }

                else
                {
                    iv_isCorrect.setVisibility(View.GONE);
                    enableJumbleLetters(false);

                    int oldScore = SharedPrefs.getInstance().getIntPreference(Utility.LEVEL_1_BEST_SCORE, 0);

                    if(score > oldScore) {
                        SharedPrefs.getInstance().writeIntPreference(Utility.LEVEL_1_BEST_SCORE, score);

                        new AlertDialog.Builder(GameActivity.this)
                                .setTitle(R.string.new_high_score_title)
                                .setMessage(getString(R.string.new_high_score_message, score))
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .show();
                    }

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
        Cursor cursor = getContentResolver().query(JumblewordsColumns.CONTENT_URI, null, JumblewordsColumns.LEVEL + "=?", new String[]{"1"}, null);

        if(cursor != null && cursor.getCount() > 0)
        {
            totalWordsCount = cursor.getCount();
            
            int randomPos = new Random().nextInt(totalWordsCount);
            cursor.moveToPosition(randomPos);
            
            id = cursor.getInt(cursor.getColumnIndex(JumblewordsColumns._ID));
            
            // Check for repeats
            if(randomGeneratedIdList.contains(""+id) && randomGeneratedIdList.size() < totalWordsCount) {
                cursor.close();
                getJumledWords();
                return;
            }
            randomGeneratedIdList.add(id+"");

            String word = cursor.getString(cursor.getColumnIndex(JumblewordsColumns.JUMBLE_WORD));
            char[] ch = word.toUpperCase().toCharArray();
            if (ch.length >= 4) {
                tv_firstLetter.setText(ch[1] + "");
                tv_secondLetter.setText(ch[0] + "");
                tv_thirdletter.setText(ch[3] + "");
                tv_fourthLetter.setText(ch[2] + "");
            }
            cursor.close();
        }
        else {
            if (cursor != null) cursor.close();
            Toast.makeText(getApplicationContext(), getString(R.string.some_error_occured), Toast.LENGTH_LONG).show();
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
        //etAnswers.setSingleCharHint(((TextView)v).getText().toString());
        //etAnswers.onTextChanged(((TextView)v).getText().toString(), index, index-1, index+1);

    }

    private void validateWord() {
        StringBuilder sb = new StringBuilder();
        sb.append(etAnswers.getText().toString());
        /*for(int i =0 ; i<tv_finalAnsArr.length ; i++)
        {
            sb = sb.append(tv_finalAnsArr[i].getText().toString());
        }*/
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
            iv_isCorrect.setImageTintList(ColorStateList.valueOf(Color.parseColor("#008000")));
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
            iv_isCorrect.setImageTintList(ColorStateList.valueOf(Color.RED));
            iv_isCorrect.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, R.drawable.incorrect));
        }
       /* for (int i = 0; i < tv_finalAnsArr.length; i++) {
            tv_finalAnsArr[i].setText("");
        }*/
       etAnswers.setText("");
        getJumledWords();
        tv_currentScore.setText(score +"");
        enableJumbleLetters(true);

    }

    private void enableJumbleLetters(boolean enabled) {
        tv_firstLetter.setEnabled(enabled);
        tv_secondLetter.setEnabled(enabled);
        tv_thirdletter.setEnabled(enabled);
        tv_fourthLetter.setEnabled(enabled);
        iv_clear.setEnabled(enabled);
        iv_clear.setClickable(enabled);

        if (enabled) {
            iv_clear.setImageTintList(ColorStateList.valueOf(Color.BLACK));

        } else {
            // Optionally add a gray tint to reinforce it's disabled
            iv_clear.setImageTintList(ColorStateList.valueOf(Color.GRAY));
        }
    }

    public static void shakeView(Context context, View viewToShake) {
        Animation anim = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.shake);
        viewToShake.setAnimation(anim);
        viewToShake.startAnimation(anim);
    }
}
