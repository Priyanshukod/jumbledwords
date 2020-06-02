package com.stupendous.jumbledwords.provider.jumblewords;

import com.stupendous.jumbledwords.provider.base.BaseModel;

import androidx.annotation.Nullable;

/**
 * Data model for the {@code jumblewords} table.
 */
public interface JumblewordsModel extends BaseModel {

    /**
     * Get the {@code jumble_word} value.
     * Can be {@code null}.
     */
    @Nullable
    String getJumbleWord();
}
