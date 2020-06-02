package com.stupendous.jumbledwords.provider.correctwords;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code correctwords} table.
 */
public class CorrectwordsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CorrectwordsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CorrectwordsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable CorrectwordsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CorrectwordsContentValues putCorrectWord(@Nullable String value) {
        mContentValues.put(CorrectwordsColumns.CORRECT_WORD, value);
        return this;
    }

    public CorrectwordsContentValues putCorrectWordNull() {
        mContentValues.putNull(CorrectwordsColumns.CORRECT_WORD);
        return this;
    }

    public CorrectwordsContentValues putJumbleWordId(@Nullable Integer value) {
        mContentValues.put(CorrectwordsColumns.JUMBLE_WORD_ID, value);
        return this;
    }

    public CorrectwordsContentValues putJumbleWordIdNull() {
        mContentValues.putNull(CorrectwordsColumns.JUMBLE_WORD_ID);
        return this;
    }
}
