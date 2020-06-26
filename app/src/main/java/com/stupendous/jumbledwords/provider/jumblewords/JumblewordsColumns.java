package com.stupendous.jumbledwords.provider.jumblewords;

// @formatter:off
import android.net.Uri;
import android.provider.BaseColumns;

import com.stupendous.jumbledwords.provider.JumbleContentProvider;
import com.stupendous.jumbledwords.provider.base.AbstractSelection;
import com.stupendous.jumbledwords.provider.correctwords.CorrectwordsColumns;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsColumns;

/**
 * Jumbled words table
 */
@SuppressWarnings("unused")
public class JumblewordsColumns implements BaseColumns {
    public static final String TABLE_NAME = "jumblewords";
    public static final Uri CONTENT_URI = Uri.parse(JumbleContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Jumbled words
     */
    public static final String JUMBLE_WORD = "jumble_word";

    public static final String LEVEL = "level";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            JUMBLE_WORD,
            LEVEL
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(JUMBLE_WORD) || c.contains("." + JUMBLE_WORD)) return true;
            if (c.equals(LEVEL) || c.contains("." + LEVEL)) return true;
        }
        return false;
    }

}
