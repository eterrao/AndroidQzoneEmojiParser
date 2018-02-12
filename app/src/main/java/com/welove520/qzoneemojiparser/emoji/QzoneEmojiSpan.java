package com.welove520.qzoneemojiparser.emoji;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.DynamicDrawableSpan;

import com.welove520.qzoneemojiparser.MyApplication;
import com.welove520.qzoneemojiparser.util.ImageUtil;

import java.io.File;

import static com.welove520.qzoneemojiparser.emoji.Utils.checkNotNull;

final class QzoneEmojiSpan extends DynamicDrawableSpan {
    private final Context context;
    private final int size;
    private final int emojiId;
    private Drawable drawable;

    QzoneEmojiSpan(@NonNull final Context context, final int emojiId, final int size) {
        this.context = context;
        this.emojiId = emojiId;
        this.size = size;
    }

    @Override
    public Drawable getDrawable() {
        if (drawable == null) {
            File imgFile = ImageUtil.getImageByName(MyApplication.Companion.getContext(), "e" + emojiId + ".png");
            drawable = checkNotNull(new BitmapDrawable(MyApplication.Companion.getContext().getResources(), imgFile.getPath()), "emoji drawable == null");
            drawable.setBounds(0, 0, size, size);
        }

        return drawable;
    }

    @Override
    public int getSize(final Paint paint, final CharSequence text, final int start,
                       final int end, final Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fontMetricsInt.ascent = -bottom;
            fontMetricsInt.top = -bottom;
            fontMetricsInt.bottom = top;
            fontMetricsInt.descent = top;
        }
        return rect.right;
    }


}
