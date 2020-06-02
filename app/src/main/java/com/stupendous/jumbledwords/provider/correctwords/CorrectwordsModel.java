package com.stupendous.jumbledwords.provider.correctwords;

import com.stupendous.jumbledwords.provider.base.BaseModel;

import androidx.annotation.Nullable;

/**
 * Data model for the {@code correctwords} table.
 */
public interface CorrectwordsModel extends BaseModel {

    /**
     * Get the {@code correct_word} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCorrectWord();

    /**
     * Get the {@code jumble_word_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getJumbleWordId();
}
