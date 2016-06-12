package com.stupendous.jumbledwords.provider.correctwords;

import android.net.Uri;
import android.provider.BaseColumns;

import com.stupendous.jumbledwords.provider.JumbleContentProvider;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;

/**
 * Columns for the {@code correctwords} table.
 */
public class CorrectwordsColumns implements BaseColumns {
    public static final String TABLE_NAME = "correctwords";
    public static final Uri CONTENT_URI = Uri.parse(JumbleContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CORRECT_WORD = "correct_word";

    public static final String JUMBLE_WORD_ID = "jumble_word_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CORRECT_WORD,
            JUMBLE_WORD_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CORRECT_WORD) || c.contains("." + CORRECT_WORD)) return true;
            if (c.equals(JUMBLE_WORD_ID) || c.contains("." + JUMBLE_WORD_ID)) return true;
        }
        return false;
    }

}
