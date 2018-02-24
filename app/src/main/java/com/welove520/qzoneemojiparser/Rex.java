package com.welove520.qzoneemojiparser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.welove520.qzoneemojiparser.util.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jne on 2018/1/3.
 */

public class Rex {

    public static void downloadAllEmojis() {
        int index = 100;
        while (index++ < 400000) {
            String imgType = (index < 200 ? "@2x.png" : "@2x.gif");
            String emojiImg = "http://qzonestyle.gtimg.cn/qzone/em/e" + index + imgType;
            final String emojiName = "e" + index + ".png";
            SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (resource != null && !resource.isRecycled()) {
//                                Bitmap dstBmp = Bitmap.createScaledBitmap(resource, 25, 25, true);
                                boolean result = ImageUtil.save(emojiName, resource, MyApplication.Companion.getContext());
                                Log.e("save_tag", "bitmap saved : " + emojiName + " result : " + result);
                            }
                        }
                    }).start();
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);

                }
            };
            Glide.with(MyApplication.Companion.getContext())
                    .load(emojiImg)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .fitCenter()
                    .into(target);
        }
    }

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
            final SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (resource != null && !resource.isRecycled()) {
                                Log.e("save_tag", "bitmap saved : " + emojiName);
                                ImageUtil.save(emojiName, resource, MyApplication.Companion.getContext());
                            }
                        }
                    }).start();
                }
            };
            final String finalEmojiImg = emojiImg;
            Glide.with(MyApplication.Companion.getContext())
                    .load(finalEmojiImg)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .fitCenter()
                    .into(target);
            newContent = newContent.replace(result.group(0), emojiImg);
        }

        return newContent;
    }

    /**
     * eg:
     * content:[em]e102[/em]
     */
    public static CharSequence transferUbb(String content) {
        String newContent = content;
        String emojiImg;
        String reg = "\\[em\\]e(\\d{3,10})\\[\\\\*/em\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher result = pattern.matcher(content);
        //因为重构那边不能提供静态的gif，所以空间表情用png（数字小于200的就是空间表情），emoji用gif
        SpannableStringBuilder builderTotal = new SpannableStringBuilder();
        while (result.find()) {
//            emojiImg = "<img class='i-emoji-m' src='http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + imgType + "' alt='表情' >";
            SpannableStringBuilder builder = new SpannableStringBuilder();
            emojiImg = "e" + result.group(1) + ".png";
            File imgFile = ImageUtil.getImageByName(MyApplication.Companion.getContext(), emojiImg);
            if (imgFile != null && imgFile.exists()) {
                Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile.getPath()), 70, 70, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append(emojiImg, new ImageSpan(MyApplication.Companion.getContext(), bmp), 0);
                } else {
                    builder.append("a")
                            .setSpan(new ImageSpan(MyApplication.Companion.getContext(), bmp),
                                    builder.length() - 1, builder.length(), 0);
                }
            }
            builderTotal.append(builder);
            newContent = newContent.replace(result.group(0), builder);
        }
        return builderTotal;
    }

    private static Html.ImageGetter assetsImageGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            InputStream inputStream = null;
            try {
                inputStream = MyApplication.Companion.getContext().getAssets().open(source);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                bitmap = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return drawable;
        }
    };

    public static CharSequence getRichContent(String content) {
        String html = "(:<img src='e101@2x.png'/>AND<img src='e101@2x.png'/>:)";
        CharSequence richText = Html.fromHtml(html, assetsImageGetter, null);
        return richText;
    }


    /**
     * eg:
     * content:[em]e102[/em]
     */
    public static String transferUbbToImg(String content) {
        String newContent = content;
        String emojiImg;

        String reg = "\\[em\\]e(\\d{3,10})\\[\\/em\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher result = pattern.matcher(content);
        while (result.find()) {
            emojiImg = "http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + "@2x.png";
            String emojiUrl = "<img src='" + emojiImg + "'" + "height=\"42\" width=\"42\"" + "  />";
            newContent = result.replaceAll(emojiUrl);
        }
        //因为重构那边不能提供静态的gif，所以空间表情用png（数字小于200的就是空间表情），emoji用gif
//        while (result.find()) {
//            String imgType;
//            int index = Integer.parseInt(result.group(1));
//            imgType = (index < 200 ? "@2x.png" : "@2x.gif");
////            emojiImg = "<img class='i-emoji-m' src='http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + imgType + "' alt='表情' >";
//            emojiImg = "http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + "@2x.png";
//            String emojiUrl = "<img src='" + emojiImg + "'" + "height=\"42\" width=\"42\"" + "  />";
//            newContent = result.replaceAll(emojiUrl)
////            newContent = newContent.replace(result.group(0), emojiUrl);
//        }
        Log.e("TAG", newContent);
        return newContent;
    }
}
