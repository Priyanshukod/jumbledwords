package com.stupendous.jumbledwords.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.stupendous.jumbledwords.BuildConfig;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;

public class JumbleDb extends SQLiteOpenHelper {
    private static final String TAG = JumbleDb.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;
    private static JumbleDb sInstance;
    private final Context mContext;
    private final JumbleDbCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_CORRECTWORDS = "CREATE TABLE IF NOT EXISTS "
            + CorrectwordsColumns.TABLE_NAME + " ( "
            + CorrectwordsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CorrectwordsColumns.CORRECT_WORD + " TEXT, "
            + CorrectwordsColumns.JUMBLE_WORD_ID + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_JUMBLEWORDS = "CREATE TABLE IF NOT EXISTS "
            + JumblewordsColumns.TABLE_NAME + " ( "
            + JumblewordsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + JumblewordsColumns.JUMBLE_WORD + " TEXT "
            + ", CONSTRAINT unique_jumble UNIQUE (jumble_word) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static JumbleDb getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static JumbleDb newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static JumbleDb newInstancePreHoneycomb(Context context) {
        return new JumbleDb(context);
    }

    private JumbleDb(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new JumbleDbCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static JumbleDb newInstancePostHoneycomb(Context context) {
        return new JumbleDb(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private JumbleDb(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new JumbleDbCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_CORRECTWORDS);
        db.execSQL(SQL_CREATE_TABLE_JUMBLEWORDS);
        mOpenHelperCallbacks.onPostCreate(mContext, db);

        Log.e(TAG,"Database created############");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
