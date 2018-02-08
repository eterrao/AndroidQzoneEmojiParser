package com.welove520.qzoneemojiparser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_emoji.view.*

class MainActivity : AppCompatActivity() {

    private val LOG_TAG: String? = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

        }
        initRecyclerView()
        parseQQEmoji()
    }

    private lateinit var emojiAdapter: EmojiAdapter

    private fun initRecyclerView() {
        emojiAdapter = EmojiAdapter()
        rv_emoji.adapter = emojiAdapter
        rv_emoji.layoutManager = GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false)
    }

    var i: Int = 0
    private fun parseQQEmoji() {
        while (i < 3000) {
            val result = Rex.transferUbbToImg("[em]e$i[/em]")
            Log.e(LOG_TAG, "parse result : " + result)
            var emoji: QQEmoji = QQEmoji()
            emoji.url = result
            emoji.id = "[em]e$i[/em]"
            emojiAdapter!!.emojiList!!.add(emoji)
            i += 1
        }
        emojiAdapter.notifyDataSetChanged()
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

        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

            fun bindEmoji(emoji: QQEmoji) {
                Glide.with(itemView.iv_emoji.context)
                        .load(emoji.url)
                        .into(itemView.iv_emoji!!.iv_emoji)
                itemView.tv_emoji_url.text = emoji.id
            }
        }
    }

    class QQEmoji {
        var url: String? = null
        var id: String? = null
    }
}
