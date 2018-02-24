package com.welove520.qzoneemojiparser;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Raomengyang on 18-2-11.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */

public class QQEmojiTextView extends android.support.v7.widget.AppCompatTextView {
    public QQEmojiTextView(Context context) {
        super(context);
    }

    public QQEmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QQEmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
//        type = BufferType.NORMAL;
//        text = Rex.transferUbb(text.toString());
        long currentTimeMS = System.currentTimeMillis();
        Log.e("TAG", " start set image emoji text =========> " + currentTimeMS + " ms");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        String reg = "\\[em\\]e(\\d{3,10})\\[\\\\*/em\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher result = pattern.matcher(text.toString());
        //因为重构那边不能提供静态的gif，所以空间表情用png（数字小于200的就是空间表情），emoji用gif
        while (result.find()) {
            String emojiImg = "e" + result.group(1) + ".png";
//            Log.e("TAG", "result.start() = " + result.start() + " , result.end() = " + result.end());
            spannableStringBuilder.setSpan(new QzoneImageSpan((Drawable) null, emojiImg), result.start(), result.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        Log.e("TAG", " finished find emoji ======> " + (System.currentTimeMillis() - currentTimeMS) + " ms");

        long currentMS = System.currentTimeMillis();
        Log.e("TAG", " start set text =========> " + currentMS + " ms");
        super.setText(spannableStringBuilder, type);
        Log.e("TAG", " finished set text ======> " + (System.currentTimeMillis() - currentMS) + " ms");

    }
}
