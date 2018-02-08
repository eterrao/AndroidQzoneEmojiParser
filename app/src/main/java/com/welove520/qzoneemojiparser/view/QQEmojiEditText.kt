package com.welove520.qzoneemojiparser.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.AppCompatEditText
import android.text.style.ImageSpan
import android.util.AttributeSet
import com.welove520.qzoneemojiparser.R

/**
 * Created by Raomengyang on 18-2-8.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
class QQEmojiEditText(context: Context?) : AppCompatEditText(context) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs) {

        init(context)
    }

    private fun init(context: Context?) {
        var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        var imageSpan: ImageSpan = ImageSpan(context, bitmap)

    }


}