package com.welove520.qzoneemojiparser;

import android.content.Context;
import android.util.AttributeSet;

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
        type = BufferType.NORMAL;
        text = Rex.transferUbb(text.toString());
        super.setText(text, type);
    }
}
