/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.welove520.qzoneemojiparser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

import com.welove520.qzoneemojiparser.util.ImageUtil;

import java.io.File;

public class QzoneImageSpan extends DynamicDrawableSpan {
    private Drawable mDrawable;
    private Context mContext;
    private String mSource;

    public QzoneImageSpan(Context context, Bitmap b) {
        this(context, b, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public QzoneImageSpan(Context context, Bitmap b, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mDrawable = context != null
                ? new BitmapDrawable(context.getResources(), b)
                : new BitmapDrawable(b);
        int width = mDrawable.getIntrinsicWidth();
        int height = mDrawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0);
    }

    public QzoneImageSpan(Drawable d) {
        this(d, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public QzoneImageSpan(Drawable d, int verticalAlignment) {
        super(verticalAlignment);
        mDrawable = d;
    }

    public QzoneImageSpan(Drawable d, String source) {
        this(d, source, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public QzoneImageSpan(Drawable d, String source, int verticalAlignment) {
        super(verticalAlignment);
        mDrawable = d;
        mSource = source;
    }

    public QzoneImageSpan(Context context, String url) {
        this(context, url, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     *                          {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public QzoneImageSpan(Context context, String url, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mSource = url;
    }


    @Override
    public Drawable getDrawable() {
        Drawable drawable = null;

        if (mDrawable != null) {
            drawable = mDrawable;
        } else if (mSource != null) {
            Bitmap bitmap = null;
            try {
                File imgFile = ImageUtil.getImageByName(MyApplication.Companion.getContext(), mSource);
                if (imgFile != null && imgFile.exists()) {
                    bitmap = BitmapFactory.decodeFile(imgFile.getPath());
                    drawable = new BitmapDrawable(MyApplication.Companion.getContext().getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                } else {
                    bitmap = BitmapFactory.decodeResource(MyApplication.Companion.getContext().getResources(), R.mipmap.ic_launcher);
                    drawable = new BitmapDrawable(MyApplication.Companion.getContext().getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                }
            } catch (Exception e) {
                Log.e("sms", "Failed to loaded content " + mSource, e);
            }
        }
        return drawable;
    }

    /**
     * Returns the source string that was saved during construction.
     */
    public String getSource() {
        return mSource;
    }

}
