package com.stupendous.jumbledwords.provider;

// @formatter:off
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.stupendous.jumbledwords.provider.base.BaseSQLiteOpenHelperCallbacks;
import com.stupendous.jumbledwords.BuildConfig;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;

public class JumbleDbSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = JumbleDbSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "words.db";
    private static final int DATABASE_VERSION = 3;
    private static JumbleDbSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final BaseSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    public static final String SQL_CREATE_TABLE_CORRECTWORDS = "CREATE TABLE IF NOT EXISTS "
            + CorrectwordsColumns.TABLE_NAME + " ( "
            + CorrectwordsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CorrectwordsColumns.CORRECT_WORD + " TEXT NOT NULL, "
            + CorrectwordsColumns.JUMBLE_WORD_ID + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_JUMBLEWORDS = "CREATE TABLE IF NOT EXISTS "
            + JumblewordsColumns.TABLE_NAME + " ( "
            + JumblewordsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + JumblewordsColumns.JUMBLE_WORD + " TEXT NOT NULL, "
            + JumblewordsColumns.LEVEL + " INTEGER "
            + ", CONSTRAINT unique_jumble UNIQUE (jumble_word) ON CONFLICT REPLACE"
            + " );";


    public static JumbleDbSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static JumbleDbSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static JumbleDbSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new JumbleDbSQLiteOpenHelper(context);
    }

    private JumbleDbSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new BaseSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static JumbleDbSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new JumbleDbSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private JumbleDbSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new BaseSQLiteOpenHelperCallbacks();
    }

    public static void setInstanceNull() {
        sInstance = null;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_CORRECTWORDS);
        db.execSQL(SQL_CREATE_TABLE_JUMBLEWORDS);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
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
        onCreate(db);
    }
}
