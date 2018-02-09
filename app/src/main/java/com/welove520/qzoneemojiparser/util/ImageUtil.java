package com.welove520.qzoneemojiparser.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Raomengyang on 18-2-9.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */

public class ImageUtil {

    public static boolean save(String name, Bitmap bitmap, Context context) {
        boolean ret = false;
        File file = getImageByName(context, name);
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                outputStream.flush();
                outputStream.close();
                ret = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ret;
    }

    public static File getImageByName(Context context, String name) {
        File bgFolder = getQQEmojiFolder(context);
        if (bgFolder != null) {
            String fileStoreDirPath = bgFolder.getAbsolutePath() + File.separator;
            return new File(fileStoreDirPath + name);
        }
        return null;
    }

    /**
     * 创建 sdcard/Android/data/com.welove520.welove/files/Pictures/bg 文件夹
     */
    public static File getQQEmojiFolder(Context context) {
        StringBuilder filePath = new StringBuilder();
        File diskDataDir = DiskUtil.getDiskDataDir(context);
        if (diskDataDir != null) {
            filePath.append(diskDataDir.getAbsolutePath());
            filePath.append(File.separator);
            filePath.append("qqemoji");
            filePath.append(File.separator);
        }

        File bgFile = new File(filePath.toString());
        if (!bgFile.exists()) {
            bgFile.mkdirs();
        }

        return bgFile;
    }
}
