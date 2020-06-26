package com.stupendous.jumbledwords.provider.correctwords;

// @formatter:off
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.BaseModel;

import java.util.Date;


/**
 * correctwords words table
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class CorrectwordsBean implements CorrectwordsModel {
    private long mId;
    private String mCorrectWord;
    private Integer mJumbleWordId;

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        return mId;
    }

    /**
     * Primary key.
     */
    public void setId(long id) {
        mId = id;
    }

    /**
     * correct words
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getCorrectWord() {
        return mCorrectWord;
    }

    /**
     * correct words
     * Must not be {@code null}.
     */
    public void setCorrectWord(@NonNull String correctWord) {
        if (correctWord == null) throw new IllegalArgumentException("correctWord must not be null");
        mCorrectWord = correctWord;
    }

    /**
     * Get the {@code jumble_word_id} value.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getJumbleWordId() {
        return mJumbleWordId;
    }

    /**
     * Set the {@code jumble_word_id} value.
     * Can be {@code null}.
     */
    public void setJumbleWordId(@Nullable Integer jumbleWordId) {
        mJumbleWordId = jumbleWordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorrectwordsBean bean = (CorrectwordsBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new CorrectwordsBean with specified values.
     */
    @NonNull
    public static CorrectwordsBean newInstance(long id, @NonNull String correctWord, @Nullable Integer jumbleWordId) {
        if (correctWord == null) throw new IllegalArgumentException("correctWord must not be null");
        CorrectwordsBean res = new CorrectwordsBean();
        res.mId = id;
        res.mCorrectWord = correctWord;
        res.mJumbleWordId = jumbleWordId;
        return res;
    }

    /**
     * Instantiate a new CorrectwordsBean with all the values copied from the given model.
     */
    @NonNull
    public static CorrectwordsBean copy(@NonNull CorrectwordsModel from) {
        CorrectwordsBean res = new CorrectwordsBean();
        res.mId = from.getId();
        res.mCorrectWord = from.getCorrectWord();
        res.mJumbleWordId = from.getJumbleWordId();
        return res;
    }

    public static class Builder {
        private CorrectwordsBean mRes = new CorrectwordsBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * correct words
         * Must not be {@code null}.
         */
        public Builder correctWord(@NonNull String correctWord) {
            if (correctWord == null) throw new IllegalArgumentException("correctWord must not be null");
            mRes.mCorrectWord = correctWord;
            return this;
        }

        /**
         * Set the {@code jumble_word_id} value.
         * Can be {@code null}.
         */
        public Builder jumbleWordId(@Nullable Integer jumbleWordId) {
            mRes.mJumbleWordId = jumbleWordId;
            return this;
        }

        /**
         * Get a new CorrectwordsBean built with the given values.
         */
        public CorrectwordsBean build() {
            if (mRes.mCorrectWord == null) throw new IllegalArgumentException("correctWord must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
