package com.stupendous.jumbledwords.provider.correctwords;

// @formatter:off
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.BaseModel;

import java.util.Date;


/**
 * correctwords words table
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface CorrectwordsModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * correct words
     * Cannot be {@code null}.
     */
    @NonNull
    String getCorrectWord();

    /**
     * Get the {@code jumble_word_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getJumbleWordId();
}
