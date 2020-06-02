package com.stupendous.jumbledwords.provider.jumblewords;

import android.database.Cursor;

import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code jumblewords} table.
 */
public class JumblewordsCursor extends AbstractCursor implements JumblewordsModel {
    public JumblewordsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(JumblewordsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code jumble_word} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getJumbleWord() {
        String res = getStringOrNull(JumblewordsColumns.JUMBLE_WORD);
        return res;
    }
}
