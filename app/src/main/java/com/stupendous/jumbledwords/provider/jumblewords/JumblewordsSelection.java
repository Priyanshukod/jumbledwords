package com.stupendous.jumbledwords.provider.jumblewords;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stupendous.jumbledwords.provider.base.AbstractSelection;

/**
 * Selection for the {@code jumblewords} table.
 */
public class JumblewordsSelection extends AbstractSelection<JumblewordsSelection> {
    @Override
    protected Uri baseUri() {
        return JumblewordsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code JumblewordsCursor} object, which is positioned before the first entry, or null.
     */
    public JumblewordsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new JumblewordsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public JumblewordsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code JumblewordsCursor} object, which is positioned before the first entry, or null.
     */
    public JumblewordsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new JumblewordsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public JumblewordsCursor query(Context context) {
        return query(context, null);
    }


    public JumblewordsSelection id(long... value) {
        addEquals("jumblewords." + JumblewordsColumns._ID, toObjectArray(value));
        return this;
    }

    public JumblewordsSelection idNot(long... value) {
        addNotEquals("jumblewords." + JumblewordsColumns._ID, toObjectArray(value));
        return this;
    }

    public JumblewordsSelection orderById(boolean desc) {
        orderBy("jumblewords." + JumblewordsColumns._ID, desc);
        return this;
    }

    public JumblewordsSelection orderById() {
        return orderById(false);
    }

    public JumblewordsSelection jumbleWord(String... value) {
        addEquals(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsSelection jumbleWordNot(String... value) {
        addNotEquals(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsSelection jumbleWordLike(String... value) {
        addLike(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsSelection jumbleWordContains(String... value) {
        addContains(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsSelection jumbleWordStartsWith(String... value) {
        addStartsWith(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsSelection jumbleWordEndsWith(String... value) {
        addEndsWith(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsSelection orderByJumbleWord(boolean desc) {
        orderBy(JumblewordsColumns.JUMBLE_WORD, desc);
        return this;
    }

    public JumblewordsSelection orderByJumbleWord() {
        orderBy(JumblewordsColumns.JUMBLE_WORD, false);
        return this;
    }
    public JumblewordsSelection level(int value) {
        addEquals("jumblewords." + JumblewordsColumns.LEVEL, toObjectArray(value));
        return this;
    }
}
