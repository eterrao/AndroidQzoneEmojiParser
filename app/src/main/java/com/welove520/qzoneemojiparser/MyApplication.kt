package com.welove520.qzoneemojiparser

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Raomengyang on 18-2-9.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
open class MyApplication : Application() {
    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Fresco.initialize(this)
    }
}