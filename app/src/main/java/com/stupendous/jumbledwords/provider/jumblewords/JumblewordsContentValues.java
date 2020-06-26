package com.stupendous.jumbledwords.provider.jumblewords;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code jumblewords} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class JumblewordsContentValues extends AbstractContentValues<JumblewordsContentValues> {
    @Override
    protected Uri baseUri() {
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
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable JumblewordsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Jumbled words
     */
    public JumblewordsContentValues putJumbleWord(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("jumbleWord must not be null");
        mContentValues.put(JumblewordsColumns.JUMBLE_WORD, value);
        return this;
    }


    public JumblewordsContentValues putLevel(@Nullable Integer value) {
        mContentValues.put(JumblewordsColumns.LEVEL, value);
        return this;
    }

    public JumblewordsContentValues putLevelNull() {
        mContentValues.putNull(JumblewordsColumns.LEVEL);
        return this;
    }
}
