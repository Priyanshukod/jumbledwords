package com.stupendous.jumbledwords.provider.jumblewords;

// @formatter:off
import java.util.Date;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code jumblewords} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class JumblewordsCursor extends AbstractCursor implements JumblewordsModel {
    public JumblewordsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(JumblewordsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Jumbled words
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getJumbleWord() {
        String res = getStringOrNull(JumblewordsColumns.JUMBLE_WORD);
        if (res == null)
            throw new NullPointerException("The value of 'jumble_word' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code level} value.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getLevel() {
        Integer res = getIntegerOrNull(JumblewordsColumns.LEVEL);
        return res;
    }
}
