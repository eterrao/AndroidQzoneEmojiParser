package com.welove520.qzoneemojiparser.emoji;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import com.welove520.qzoneemojiparser.util.DensityUtil;


public class QzoneEmojiTextView extends AppCompatTextView {
    public QzoneEmojiTextView(final Context context) {
        this(context, null);
    }

    public QzoneEmojiTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            EmojiManager.getInstance().verifyInstalled();
        }
        setText(getText());
    }

    @Override
    public void setText(final CharSequence rawText, final BufferType type) {
        final CharSequence text = rawText == null ? "" : rawText;
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        QzoneEmojiHandler.addEmojis(getContext(), spannableStringBuilder, DensityUtil.dip2px(19));
        super.setText(spannableStringBuilder, type);
    }
}
