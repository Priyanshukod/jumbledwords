package com.stupendous.jumbledwords.provider.correctwords;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import androidx.loader.content.CursorLoader;

import com.stupendous.jumbledwords.provider.base.AbstractSelection;

/**
 * Selection for the {@code correctwords} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class CorrectwordsSelection extends AbstractSelection<CorrectwordsSelection> {
    @Override
    protected Uri baseUri() {
        return CorrectwordsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CorrectwordsCursor} object, which is positioned before the first entry, or null.
     */
    public CorrectwordsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CorrectwordsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public CorrectwordsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CorrectwordsCursor} object, which is positioned before the first entry, or null.
     */
    public CorrectwordsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CorrectwordsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public CorrectwordsCursor query(Context context) {
        return query(context, null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CursorLoader getCursorLoader(Context context, String[] projection) {
        return new CursorLoader(context, uri(), projection, sel(), args(), order()) {
            @Override
            public Cursor loadInBackground() {
                return new CorrectwordsCursor(super.loadInBackground());
            }
        };
    }


    public CorrectwordsSelection id(long... value) {
        addEquals("correctwords." + CorrectwordsColumns._ID, toObjectArray(value));
        return this;
    }

    public CorrectwordsSelection idNot(long... value) {
        addNotEquals("correctwords." + CorrectwordsColumns._ID, toObjectArray(value));
        return this;
    }

    public CorrectwordsSelection orderById(boolean desc) {
        orderBy("correctwords." + CorrectwordsColumns._ID, desc);
        return this;
    }

    public CorrectwordsSelection orderById() {
        return orderById(false);
    }

    public CorrectwordsSelection correctWord(String... value) {
        addEquals(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsSelection correctWordNot(String... value) {
        addNotEquals(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsSelection correctWordLike(String... value) {
        addLike(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsSelection correctWordContains(String... value) {
        addContains(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsSelection correctWordStartsWith(String... value) {
        addStartsWith(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsSelection correctWordEndsWith(String... value) {
        addEndsWith(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsSelection orderByCorrectWord(boolean desc) {
        orderBy(CorrectwordsColumns.CORRECT_WORD, desc);
        return this;
    }

    public CorrectwordsSelection orderByCorrectWord() {
        orderBy(CorrectwordsColumns.CORRECT_WORD, false);
        return this;
    }

    public CorrectwordsSelection jumbleWordId(Integer... value) {
        addEquals(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsSelection jumbleWordIdNot(Integer... value) {
        addNotEquals(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsSelection jumbleWordIdGt(int value) {
        addGreaterThan(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsSelection jumbleWordIdGtEq(int value) {
        addGreaterThanOrEquals(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsSelection jumbleWordIdLt(int value) {
        addLessThan(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsSelection jumbleWordIdLtEq(int value) {
        addLessThanOrEquals(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsSelection orderByJumbleWordId(boolean desc) {
        orderBy(CorrectwordsColumns.JUMBLE_WORD_ID, desc);
        return this;
    }

    public CorrectwordsSelection orderByJumbleWordId() {
        orderBy(CorrectwordsColumns.JUMBLE_WORD_ID, false);
        return this;
    }
}
