package com.stupendous.jumbledwords.provider.jumblewords;

// @formatter:off
import com.stupendous.jumbledwords.provider.base.BaseModel;

import java.util.Date;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Jumbled words table
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface JumblewordsModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Jumbled words
     * Cannot be {@code null}.
     */
    @NonNull
    String getJumbleWord();

    /**
     * Get the {@code level} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getLevel();
}
