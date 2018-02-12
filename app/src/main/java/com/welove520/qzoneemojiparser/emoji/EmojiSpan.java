package com.welove520.qzoneemojiparser.emoji;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.text.style.DynamicDrawableSpan;

import static com.welove520.qzoneemojiparser.emoji.Utils.checkNotNull;

final class EmojiSpan extends DynamicDrawableSpan {
    private final Context context;
    private final int resourceId;
    private final int size;

    private Drawable drawable;

    EmojiSpan(@NonNull final Context context, @DrawableRes final int resourceId, final int size) {
        this.context = context;
        this.resourceId = resourceId;
        this.size = size;
    }

    @Override
    public Drawable getDrawable() {
        if (drawable == null) {
            drawable = checkNotNull(AppCompatResources.getDrawable(context, resourceId), "emoji drawable == null");
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
