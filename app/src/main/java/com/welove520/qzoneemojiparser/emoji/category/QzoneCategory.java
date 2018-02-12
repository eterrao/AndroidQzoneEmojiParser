package com.welove520.qzoneemojiparser.emoji.category;

import android.support.annotation.NonNull;

import com.welove520.qzoneemojiparser.R;
import com.welove520.qzoneemojiparser.emoji.Emoji;
import com.welove520.qzoneemojiparser.emoji.EmojiCategory;


/**
 * Created by Administrator on 2017/5/23.
 */

public class QzoneCategory implements EmojiCategory {
    @NonNull
    @Override
    public Emoji[] getEmojis() {
        return new Emoji[0];
    }

    @Override
    public int getIcon() {
        return 0;
    }
//    /**
//     * 微爱自带表情
//     * 0x5b:  "["
//     * 0x30:  "0"
//     * 0x5d:  "]"
//     * so {0x5b, 0x30, 0x5d} means "[0]"
//     */
//    private static final Emoji[] WELOVE_DATA = new Emoji[]{
//            new Emoji(new int[]{0x5b, 0x30, 0x5d}, R.drawable.emoticon_simple_0),
//            new Emoji(new int[]{0x5b, 0x31, 0x5d}, R.drawable.emoticon_simple_1),
//            new Emoji(new int[]{0x5b, 0x32, 0x5d}, R.drawable.emoticon_simple_2),
//            new Emoji(new int[]{0x5b, 0x33, 0x5d}, R.drawable.emoticon_simple_3),
//            new Emoji(new int[]{0x5b, 0x34, 0x5d}, R.drawable.emoticon_simple_4),
//            new Emoji(new int[]{0x5b, 0x35, 0x5d}, R.drawable.emoticon_simple_5),
//            new Emoji(new int[]{0x5b, 0x36, 0x5d}, R.drawable.emoticon_simple_6),
//            new Emoji(new int[]{0x5b, 0x37, 0x5d}, R.drawable.emoticon_simple_7),
//            new Emoji(new int[]{0x5b, 0x38, 0x5d}, R.drawable.emoticon_simple_8),
//            new Emoji(new int[]{0x5b, 0x39, 0x5d}, R.drawable.emoticon_simple_9),
//            new Emoji(new int[]{0x5b, 0x31, 0x30, 0x5d}, R.drawable.emoticon_simple_10),
//            new Emoji(new int[]{0x5b, 0x31, 0x31, 0x5d}, R.drawable.emoticon_simple_11),
//            new Emoji(new int[]{0x5b, 0x31, 0x32, 0x5d}, R.drawable.emoticon_simple_12),
//            new Emoji(new int[]{0x5b, 0x31, 0x33, 0x5d}, R.drawable.emoticon_simple_13),
//            new Emoji(new int[]{0x5b, 0x31, 0x34, 0x5d}, R.drawable.emoticon_simple_14),
//            new Emoji(new int[]{0x5b, 0x31, 0x35, 0x5d}, R.drawable.emoticon_simple_15),
//            new Emoji(new int[]{0x5b, 0x31, 0x36, 0x5d}, R.drawable.emoticon_simple_16),
//            new Emoji(new int[]{0x5b, 0x31, 0x37, 0x5d}, R.drawable.emoticon_simple_17),
//            new Emoji(new int[]{0x5b, 0x31, 0x38, 0x5d}, R.drawable.emoticon_simple_18),
//            new Emoji(new int[]{0x5b, 0x31, 0x39, 0x5d}, R.drawable.emoticon_simple_19),
//            new Emoji(new int[]{0x5b, 0x32, 0x30, 0x5d}, R.drawable.emoticon_simple_20),
//            new Emoji(new int[]{0x5b, 0x32, 0x31, 0x5d}, R.drawable.emoticon_simple_21),
//            new Emoji(new int[]{0x5b, 0x32, 0x32, 0x5d}, R.drawable.emoticon_simple_22),
//            new Emoji(new int[]{0x5b, 0x32, 0x33, 0x5d}, R.drawable.emoticon_simple_23),
//            new Emoji(new int[]{0x5b, 0x32, 0x34, 0x5d}, R.drawable.emoticon_simple_24),
//            new Emoji(new int[]{0x5b, 0x32, 0x35, 0x5d}, R.drawable.emoticon_simple_25),
//            new Emoji(new int[]{0x5b, 0x32, 0x36, 0x5d}, R.drawable.emoticon_simple_26),
//            new Emoji(new int[]{0x5b, 0x32, 0x37, 0x5d}, R.drawable.emoticon_simple_27),
//            new Emoji(new int[]{0x5b, 0x32, 0x38, 0x5d}, R.drawable.emoticon_simple_28),
//            new Emoji(new int[]{0x5b, 0x32, 0x39, 0x5d}, R.drawable.emoticon_simple_29),
//            new Emoji(new int[]{0x5b, 0x33, 0x30, 0x5d}, R.drawable.emoticon_simple_30),
//            new Emoji(new int[]{0x5b, 0x33, 0x31, 0x5d}, R.drawable.emoticon_simple_31),
//            new Emoji(new int[]{0x5b, 0x33, 0x32, 0x5d}, R.drawable.emoticon_simple_32),
//            new Emoji(new int[]{0x5b, 0x33, 0x33, 0x5d}, R.drawable.emoticon_simple_33),
//            new Emoji(new int[]{0x5b, 0x33, 0x34, 0x5d}, R.drawable.emoticon_simple_34),
//            new Emoji(new int[]{0x5b, 0x33, 0x35, 0x5d}, R.drawable.emoticon_simple_35),
//            new Emoji(new int[]{0x5b, 0x33, 0x36, 0x5d}, R.drawable.emoticon_simple_36),
//            new Emoji(new int[]{0x5b, 0x33, 0x37, 0x5d}, R.drawable.emoticon_simple_37),
//            new Emoji(new int[]{0x5b, 0x33, 0x38, 0x5d}, R.drawable.emoticon_simple_38),
//            new Emoji(new int[]{0x5b, 0x33, 0x39, 0x5d}, R.drawable.emoticon_simple_39),
//            new Emoji(new int[]{0x5b, 0x34, 0x30, 0x5d}, R.drawable.emoticon_simple_40),
//            new Emoji(new int[]{0x5b, 0x34, 0x31, 0x5d}, R.drawable.emoticon_simple_41),
//            new Emoji(new int[]{0x5b, 0x34, 0x32, 0x5d}, R.drawable.emoticon_simple_42),
//            new Emoji(new int[]{0x5b, 0x34, 0x33, 0x5d}, R.drawable.emoticon_simple_43),
//            new Emoji(new int[]{0x5b, 0x34, 0x34, 0x5d}, R.drawable.emoticon_simple_44),
//            new Emoji(new int[]{0x5b, 0x34, 0x35, 0x5d}, R.drawable.emoticon_simple_45),
//            new Emoji(new int[]{0x5b, 0x34, 0x36, 0x5d}, R.drawable.emoticon_simple_46),
//            new Emoji(new int[]{0x5b, 0x34, 0x37, 0x5d}, R.drawable.emoticon_simple_47),
//            new Emoji(new int[]{0x5b, 0x34, 0x38, 0x5d}, R.drawable.emoticon_simple_48),
//            new Emoji(new int[]{0x5b, 0x34, 0x39, 0x5d}, R.drawable.emoticon_simple_49),
//            new Emoji(new int[]{0x5b, 0x35, 0x30, 0x5d}, R.drawable.emoticon_simple_50),
//            new Emoji(new int[]{0x5b, 0x35, 0x31, 0x5d}, R.drawable.emoticon_simple_51),
//            new Emoji(new int[]{0x5b, 0x35, 0x32, 0x5d}, R.drawable.emoticon_simple_52),
//            new Emoji(new int[]{0x5b, 0x35, 0x33, 0x5d}, R.drawable.emoticon_simple_53),
//            new Emoji(new int[]{0x5b, 0x35, 0x34, 0x5d}, R.drawable.emoticon_simple_54),
//            new Emoji(new int[]{0x5b, 0x35, 0x35, 0x5d}, R.drawable.emoticon_simple_55),
//            new Emoji(new int[]{0x5b, 0x35, 0x36, 0x5d}, R.drawable.emoticon_simple_56),
//            new Emoji(new int[]{0x5b, 0x35, 0x37, 0x5d}, R.drawable.emoticon_simple_57),
//            new Emoji(new int[]{0x5b, 0x35, 0x38, 0x5d}, R.drawable.emoticon_simple_58),
//    };
//
//
//    @NonNull
//    @Override
//    public Emoji[] getEmojis() {
//        return WELOVE_DATA;
//    }
//
//    @Override
//    public int getIcon() {
//        return R.drawable.emoticon_simple_52;
//    }
}
