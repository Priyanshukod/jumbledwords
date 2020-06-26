package com.stupendous.jumbledwords.provider.correctwords;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code correctwords} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class CorrectwordsContentValues extends AbstractContentValues<CorrectwordsContentValues> {
    @Override
    protected Uri baseUri() {
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
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable CorrectwordsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * correct words
     */
    public CorrectwordsContentValues putCorrectWord(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("correctWord must not be null");
        mContentValues.put(CorrectwordsColumns.CORRECT_WORD, value);
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
