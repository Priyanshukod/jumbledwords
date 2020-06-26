package com.stupendous.jumbledwords.provider.correctwords;

// @formatter:off
import java.util.Date;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code correctwords} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class CorrectwordsCursor extends AbstractCursor implements CorrectwordsModel {
    public CorrectwordsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(CorrectwordsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * correct words
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getCorrectWord() {
        String res = getStringOrNull(CorrectwordsColumns.CORRECT_WORD);
        if (res == null)
            throw new NullPointerException("The value of 'correct_word' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code jumble_word_id} value.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getJumbleWordId() {
        Integer res = getIntegerOrNull(CorrectwordsColumns.JUMBLE_WORD_ID);
        return res;
    }
}
