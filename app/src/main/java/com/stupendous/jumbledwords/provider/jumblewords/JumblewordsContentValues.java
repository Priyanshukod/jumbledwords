package com.stupendous.jumbledwords.provider.jumblewords;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code jumblewords} table.
 */
public class JumblewordsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return JumblewordsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable JumblewordsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable JumblewordsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public JumblewordsContentValues putJumbleWord(@Nullable String value) {
        mContentValues.put(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }

    public JumblewordsContentValues putJumbleWordNull() {
        mContentValues.putNull(JumblewordsColumns.JUMBLE_WORD);
        return this;
    }
}
