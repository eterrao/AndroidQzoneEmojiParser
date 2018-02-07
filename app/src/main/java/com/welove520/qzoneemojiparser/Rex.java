package com.welove520.qzoneemojiparser;

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
    public static String transferUbbToImg(String content) {
        String newContent = content;
        String emojiImg;

        String reg = "\\[em\\]e(\\d{3,10})\\[\\/em\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher result = pattern.matcher(content);

        //因为重构那边不能提供静态的gif，所以空间表情用png（数字小于200的就是空间表情），emoji用gif
        while (result.find()) {
            String imgType;
            if (isIOS()) {
                imgType = "@2x.gif";
            } else {
                int index = Integer.parseInt(result.group(1));
                imgType = (index < 200 ? "@2x.png" : "@2x.gif");
            }
//            emojiImg = "<img class='i-emoji-m' src='http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + imgType + "' alt='表情' >";
            emojiImg = "http://qzonestyle.gtimg.cn/qzone/em/e" + result.group(1) + imgType;
            newContent = newContent.replace(result.group(0), emojiImg);
        }
        return newContent;
    }

    private static boolean isIOS() {
        return false;
    }
}
