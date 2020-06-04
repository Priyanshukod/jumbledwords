package com.stupendous.jumbledwords.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.stupendous.jumbledwords.BuildConfig;
import com.stupendous.jumbledwords.provider.base.BaseContentProvider;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;

public class JumbleContentProvider extends BaseContentProvider {
    private static final String TAG = JumbleContentProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.stupendous.jumbledwords.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_CORRECTWORDS = 0;
    private static final int URI_TYPE_CORRECTWORDS_ID = 1;

    private static final int URI_TYPE_JUMBLEWORDS = 2;
    private static final int URI_TYPE_JUMBLEWORDS_ID = 3;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CorrectwordsColumns.TABLE_NAME, URI_TYPE_CORRECTWORDS);
        URI_MATCHER.addURI(AUTHORITY, CorrectwordsColumns.TABLE_NAME + "/#", URI_TYPE_CORRECTWORDS_ID);
        URI_MATCHER.addURI(AUTHORITY, JumblewordsColumns.TABLE_NAME, URI_TYPE_JUMBLEWORDS);
        URI_MATCHER.addURI(AUTHORITY, JumblewordsColumns.TABLE_NAME + "/#", URI_TYPE_JUMBLEWORDS_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return JumbleDb.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_CORRECTWORDS:
                return TYPE_CURSOR_DIR + CorrectwordsColumns.TABLE_NAME;
            case URI_TYPE_CORRECTWORDS_ID:
                return TYPE_CURSOR_ITEM + CorrectwordsColumns.TABLE_NAME;

            case URI_TYPE_JUMBLEWORDS:
                return TYPE_CURSOR_DIR + JumblewordsColumns.TABLE_NAME;
            case URI_TYPE_JUMBLEWORDS_ID:
                return TYPE_CURSOR_ITEM + JumblewordsColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_CORRECTWORDS:
            case URI_TYPE_CORRECTWORDS_ID:
                res.table = CorrectwordsColumns.TABLE_NAME;
                res.idColumn = CorrectwordsColumns._ID;
                res.tablesWithJoins = CorrectwordsColumns.TABLE_NAME;
                res.orderBy = CorrectwordsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_JUMBLEWORDS:
            case URI_TYPE_JUMBLEWORDS_ID:
                res.table = JumblewordsColumns.TABLE_NAME;
                res.idColumn = JumblewordsColumns._ID;
                res.tablesWithJoins = JumblewordsColumns.TABLE_NAME;
                res.orderBy = JumblewordsColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_CORRECTWORDS_ID:
            case URI_TYPE_JUMBLEWORDS_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
