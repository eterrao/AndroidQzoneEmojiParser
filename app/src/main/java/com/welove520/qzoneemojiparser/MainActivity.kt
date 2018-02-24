package com.welove520.qzoneemojiparser

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.welove520.qzoneemojiparser.util.ImageUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_emoji.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.io.File
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val LOG_TAG: String? = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

        }
//        initView()
//        initListRecyclerView()
//        initRecyclerView()
//        parseQQEmoji()
//        downloadQQEmojis()
        initQQEmojiTextView()

//        initHtmlTextView()
    }

    private fun initHtmlTextView() {
        var imageUrl = "This will display an image to the right <img src='http://qzonestyle.gtimg.cn/qzone/em/e3000@2x.gif' />";

//        tv_html.text = Html.fromHtml(imageUrl, GlideImageGetter(this, tv_html), null)
    }

    private fun initQQEmojiTextView() {
        var index = 100
        var result = StringBuilder()
        Thread(Runnable {

            val currentTimeMS = System.currentTimeMillis()
            Log.e("TAG", " start create content =========> $currentTimeMS ms")
            while (index++ < 4000) {
                result.append("[em]e$index[/em]")
                Log.e(LOG_TAG, "parse result : " + result)
            }
//        val imgFile = ImageUtil.getImageByName(MyApplication.context, "e" + 102 + ".png")
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            qq_emoji_tv.text = "abc"
//            qq_emoji_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(BitmapDrawable(resources, BitmapFactory.decodeFile(imgFile.path)), null, null, null)
//        }
//        = Rex.getRichContent(result);
            Log.e("TAG", " finished create content ======> " + (System.currentTimeMillis() - currentTimeMS) + " ms")

            this.runOnUiThread {
                pb_loading.visibility = View.VISIBLE
                qq_emoji_tv.text = result
            }
        }).start()

//        qzone_emoji_tv.setText(result, TextView.BufferType.NORMAL)
    }


    private fun downloadQQEmojis() {
        Rex.downloadAllEmojis()
//        var index = 100
//        while (index++ < 3000) {
//            val result = Rex.transferUbbToImg("[em]e$index[/em]", index)
//            Log.e(LOG_TAG, "parse result : " + result)
//            var emoji = QQEmoji()
//            emoji.url = result
//            emoji.id = "e$index.png"
//            emojiAdapter!!.emojiList!!.add(emoji)
//        }
    }

    private fun initListRecyclerView() {
        var rvListAdapter = RVListAdapter()
        rv_list.adapter = rvListAdapter
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list.layoutManager = layoutManager

        thread {
            var items = ArrayList<ListItem>()
            var count = 0
            while (count++ < 300) {
                var j = 100
                var item = ListItem()
                item.name = count.toString()
                val builder = SpannableStringBuilder()
                while (j++ < 2000) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val imgFile = ImageUtil.getImageByName(MyApplication.context, "e$j.png")
                        if (imgFile != null && imgFile.exists()) {
                            var bmp: Bitmap = BitmapFactory.decodeFile(imgFile.path)
                            builder.append("aaaaa", ImageSpan(MyApplication.context, bmp), 0)
                        }
                    }
                    Log.e(LOG_TAG, "j value: " + j)
                }
                item.content = builder
                items.add(item)
                rvListAdapter.items = items
                runOnUiThread { rvListAdapter.notifyDataSetChanged() }
            }
        }
    }

    fun decodeSampledBitmapFromFile(res: File, reqWidth: Int, reqHeight: Int): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(res.path, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(res.path, options)
    }

    fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    class RVListAdapter : RecyclerView.Adapter<RVListAdapter.ListViewHolder>() {
        var items: MutableList<ListItem>? = ArrayList()
        override fun getItemCount(): Int {
            return items?.size!!
        }

        override fun onBindViewHolder(holder: ListViewHolder?, position: Int) {
            holder!!.bindView(items!![position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListViewHolder {
            var view: View = View.inflate(parent!!.context, R.layout.item_list, null)
            return ListViewHolder(view)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }


        class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            fun bindView(listItem: ListItem) {
                itemView.tv_item_name.text = listItem.name
                itemView.tv_item_content.text = listItem.content
            }
        }
    }

    class ListItem {
        var name: String? = null
        var content: CharSequence? = null
    }

    private fun initView() {
        val builder = SpannableStringBuilder()
        Thread(Runnable {
            while (i++ < 3000) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val imgFile = ImageUtil.getImageByName(MyApplication.context, "e$i.png")
                    if (imgFile != null && imgFile.exists()) {
                        var bmp: Bitmap = BitmapFactory.decodeFile(imgFile.path)
                        builder.append("a", ImageSpan(MyApplication.context, bmp), 0)
                    }
                }
                Log.e(LOG_TAG, "i value: " + i)
            }
            this.runOnUiThread { tv_preview.text = builder }
        }).start()
//        tv_preview.text = builder
    }

    private lateinit var emojiAdapter: EmojiAdapter

    private fun initRecyclerView() {
        emojiAdapter = EmojiAdapter()
        emojiAdapter.setHasStableIds(true)
        rv_emoji.adapter = emojiAdapter
        rv_emoji.layoutManager = GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false)
    }

    var i: Int = 100
    private fun parseQQEmoji() {
        Observable.just(3000)
                .map { t ->
                    while (i < t!!) {
                        val result = Rex.transferUbbToImg("[em]e$i[/em]")
                        Log.e(LOG_TAG, "parse result : " + result)
                        var emoji = QQEmoji()
                        emoji.url = result
//                        emoji.id = "[em]e$i[/em]"
                        emoji.id = "e$i.png"
                        emojiAdapter!!.emojiList!!.add(emoji)
                        i += 1
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    emojiAdapter.notifyDataSetChanged()
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class EmojiAdapter : RecyclerView.Adapter<EmojiAdapter.ViewHolder>() {
        var emojiList: MutableList<QQEmoji>? = ArrayList<QQEmoji>()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
            var view = View.inflate(parent!!.context, R.layout.item_emoji, null)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return emojiList!!.size
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder!!.bindEmoji(emojiList!![position])
        }

//        override fun getItemViewType(position: Int): Int {
//            return position
//        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

            fun bindEmoji(emoji: QQEmoji) {
//                itemView.tv_emoji.text = emoji.id
//                var target = object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
//                        val builder = SpannableStringBuilder()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder.append(" ", ImageSpan(itemView.context, resource), 0)
//                        }
//                        itemView.tv_emoji.text = builder
//                    }
//
//                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
//                        super.onLoadFailed(e, errorDrawable)
//                        val builder = SpannableStringBuilder()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder.append(" ", ImageSpan(itemView.context,
//                                    BitmapFactory.decodeResource(itemView.resources, R.mipmap.ic_launcher_round)), 0)
//                        }
//                        itemView.tv_emoji.text = builder
//                    }
//                }
//                Glide.with(itemView.context)
//                        .load(emoji.url)
//                        .asBitmap()
//                        .skipMemoryCache(true)
//                        .placeholder(R.mipmap.ic_launcher_round)
//                        .error(R.mipmap.ic_launcher_round)
//                        .format(DecodeFormat.PREFER_ARGB_8888)
//                        .override(50, 50)
//                        .fitCenter()
//                        .into(target)

//                var target1 = object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
//                        val builder = SpannableStringBuilder()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder.append(" ", ImageSpan(itemView.context, resource), 0)
//                        }
//                        itemView.tv_emoji1.text = builder
//                    }
//
//                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
//                        super.onLoadFailed(e, errorDrawable)
//                        val builder = SpannableStringBuilder()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder.append(" ", ImageSpan(itemView.context,
//                                    BitmapFactory.decodeResource(itemView.resources, R.mipmap.ic_launcher_round)), 0)
//                        }
//                        itemView.tv_emoji1.text = builder
//                    }
//                }
//                Glide.with(itemView.context)
//                        .load(emoji.url)
//                        .asBitmap()
//                        .skipMemoryCache(true)
//                        .placeholder(R.mipmap.ic_launcher_round)
//                        .error(R.mipmap.ic_launcher_round)
//                        .format(DecodeFormat.PREFER_ARGB_8888)
//                        .override(50, 50)
//                        .fitCenter()
//                        .into(target1)
//                var target2 = object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
//                        val builder = SpannableStringBuilder()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder.append(" ", ImageSpan(itemView.context, resource), 0)
//                        }
//                        itemView.tv_emoji2.text = builder
//                    }
//
//                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
//                        super.onLoadFailed(e, errorDrawable)
//                        val builder = SpannableStringBuilder()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder.append(" ", ImageSpan(itemView.context,
//                                    BitmapFactory.decodeResource(itemView.resources, R.mipmap.ic_launcher_round)), 0)
//                        }
//                        itemView.tv_emoji2.text = builder
//                    }
//                }
//                Glide.with(itemView.context)
//                        .load(emoji.url)
//                        .asBitmap()
//                        .skipMemoryCache(true)
//                        .placeholder(R.mipmap.ic_launcher_round)
//                        .error(R.mipmap.ic_launcher_round)
//                        .format(DecodeFormat.PREFER_ARGB_8888)
//                        .override(50, 50)
//                        .fitCenter()
//                        .into(target2)

//                var controller: DraweeController = Fresco.newDraweeControllerBuilder()
//                        .setUri(Uri.parse(emoji.url))
//                        .setAutoPlayAnimations(true)
//                        .build();
//                itemView.iv_emoji_drawee_view.controller = controller

//                Glide.with(itemView.iv_emoji.context)
//                        .load(emoji.url)
//                        .fitCenter()
//                        .into(itemView.iv_emoji!!.iv_emoji)
//                val builder = SpannableStringBuilder()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    val imgFile = ImageUtil.getImageByName(MyApplication.context, emoji.id)
////            if (imgFile != null && imgFile.exists()) {
////                val span = ImageSpan(MyApplication.context, BitmapFactory.decodeFile(imgFile.path))
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    builder.append("", span, 0)
////                }
////            }
//                    if (imgFile != null && imgFile.exists()) {
//                        var bmp: Bitmap = BitmapFactory.decodeFile(imgFile.path)
//                        builder.append(" ", ImageSpan(itemView.context, bmp!!), 0)
//                    }
//                }
                itemView.tv_emoji.text = Html.fromHtml(emoji.url, GlideImageGetter(itemView.tv_emoji.context, itemView.tv_emoji), null)
            }
        }
    }

    class QQEmoji {
        var url: String? = null
        var id: String? = null
    }
}
