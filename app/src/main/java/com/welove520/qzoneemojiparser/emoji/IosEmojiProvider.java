package com.welove520.qzoneemojiparser.emoji;

import android.support.annotation.NonNull;

import com.welove520.qzoneemojiparser.emoji.category.QzoneCategory;


public final class IosEmojiProvider implements EmojiProvider {
    @Override
    @NonNull
    public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{
                new QzoneCategory()
        };
    }
}
