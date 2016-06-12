package com.stupendous.jumbledwords.provider.correctwords;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code correctwords} table.
 */
public class CorrectwordsCursor extends AbstractCursor implements CorrectwordsModel {
    public CorrectwordsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CorrectwordsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code correct_word} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCorrectWord() {
        String res = getStringOrNull(CorrectwordsColumns.CORRECT_WORD);
        return res;
    }

    /**
     * Get the {@code jumble_word_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getJumbleWordId() {
        Integer res = getIntegerOrNull(CorrectwordsColumns.JUMBLE_WORD_ID);
        return res;
    }
}
