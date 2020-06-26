package com.stupendous.jumbledwords.provider.jumblewords;

// @formatter:off
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stupendous.jumbledwords.provider.base.BaseModel;

import java.util.Date;


/**
 * Jumbled words table
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class JumblewordsBean implements JumblewordsModel {
    private long mId;
    private String mJumbleWord;
    private Integer mLevel;

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
     * Jumbled words
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getJumbleWord() {
        return mJumbleWord;
    }

    /**
     * Jumbled words
     * Must not be {@code null}.
     */
    public void setJumbleWord(@NonNull String jumbleWord) {
        if (jumbleWord == null) throw new IllegalArgumentException("jumbleWord must not be null");
        mJumbleWord = jumbleWord;
    }

    /**
     * Get the {@code level} value.
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getLevel() {
        return mLevel;
    }

    /**
     * Set the {@code level} value.
     * Can be {@code null}.
     */
    public void setLevel(@Nullable Integer level) {
        mLevel = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JumblewordsBean bean = (JumblewordsBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new JumblewordsBean with specified values.
     */
    @NonNull
    public static JumblewordsBean newInstance(long id, @NonNull String jumbleWord, @Nullable Integer level) {
        if (jumbleWord == null) throw new IllegalArgumentException("jumbleWord must not be null");
        JumblewordsBean res = new JumblewordsBean();
        res.mId = id;
        res.mJumbleWord = jumbleWord;
        res.mLevel = level;
        return res;
    }

    /**
     * Instantiate a new JumblewordsBean with all the values copied from the given model.
     */
    @NonNull
    public static JumblewordsBean copy(@NonNull JumblewordsModel from) {
        JumblewordsBean res = new JumblewordsBean();
        res.mId = from.getId();
        res.mJumbleWord = from.getJumbleWord();
        res.mLevel = from.getLevel();
        return res;
    }

    public static class Builder {
        private JumblewordsBean mRes = new JumblewordsBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Jumbled words
         * Must not be {@code null}.
         */
        public Builder jumbleWord(@NonNull String jumbleWord) {
            if (jumbleWord == null) throw new IllegalArgumentException("jumbleWord must not be null");
            mRes.mJumbleWord = jumbleWord;
            return this;
        }

        /**
         * Set the {@code level} value.
         * Can be {@code null}.
         */
        public Builder level(@Nullable Integer level) {
            mRes.mLevel = level;
            return this;
        }

        /**
         * Get a new JumblewordsBean built with the given values.
         */
        public JumblewordsBean build() {
            if (mRes.mJumbleWord == null) throw new IllegalArgumentException("jumbleWord must not be null");
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
