package com.welove520.qzoneemojiparser;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.welove520.qzoneemojiparser.util.ImageUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jne on 2018/1/3.
 */

public class Rex {

    /**
     * eg:
     * content:[em]e102[/em]
     */
    public static String transferUbbToImg(String content, int number) {
        String newContent = content;
        String emojiImg;

        String reg = "\\[em\\]e(\\d{3,10})\\[\\/em\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher result = pattern.matcher(content);


        //因为重构那边不能提供静态的gif，所以空间表情用png（数字小于200的就是空间表情），emoji用gif
        while (result.find()) {
            String imgType;
            int index = Integer.parseInt(result.group(1));
            imgType = (index < 200 ? "@2x.png" : "@2x.gif");
//            emojiImg = "<img class='i-emoji-m' src='http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + imgType + "' alt='表情' >";
            emojiImg = "http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + imgType;
            final String emojiName = "e" + number + ".png";
            final SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                SpannableStringBuilder builder = new SpannableStringBuilder();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder.append("", new ImageSpan(MyApplication.Companion.getContext(), resource), 0);
//                }

                    final Bitmap emojiBmp = Bitmap.createBitmap(resource).copy(Bitmap.Config.ARGB_8888, true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (emojiBmp != null && !emojiBmp.isRecycled()) {
                                ImageUtil.save(emojiName, emojiBmp, MyApplication.Companion.getContext());
                            }
                        }
                    }).start();
                }
            };
            final String finalEmojiImg = emojiImg;
            new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    Glide.with(MyApplication.Companion.getContext())
                            .load(finalEmojiImg)
                            .asBitmap()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(100, 100)
                            .fitCenter()
                            .into(target);
                }
            }.sendEmptyMessage(0);

//            SpannableStringBuilder builder = new SpannableStringBuilder();
//            File imgFile = ImageUtil.getImageByName(MyApplication.Companion.getContext(), emojiName);
//            if (imgFile != null && imgFile.exists()) {
//                ImageSpan span = new ImageSpan(MyApplication.Companion.getContext(), BitmapFactory.decodeFile(imgFile.getPath()));
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder.append("", span, 0);
//                }
//            }
            newContent = newContent.replace(result.group(0), emojiImg);
        }

        return newContent;
    }
}
