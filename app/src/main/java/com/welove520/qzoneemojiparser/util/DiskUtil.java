package com.welove520.qzoneemojiparser.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;

import com.welove520.qzoneemojiparser.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tielei
 */
public class DiskUtil {

    private static final String LOG_TAG = "DiskUtil";

    public static final String DIR_NAME_PICK_PHOTO = "pick_photo";
    public static final String DIR_NAME_COMPRESS_IMAGE = "compress_image";
    private static final String TAG = DiskUtil.class.getSimpleName();

    /**
     * @return null, if the external storage is not available
     */
    public static File getDiskCacheDir(Context context) {
        return getExternalCacheDir(context);
    }

    public static boolean isExternalStorageAvailable() {
        return isExternalStorageMounted() || !isExternalStorageRemovable();
    }

    public static boolean isExternalStorageMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }

    public static File getExternalCacheDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static File getExternalDataDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        if (BuildConfig.DEBUG) {
            Log.d("DiskUtil", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/files/" + Environment.DIRECTORY_PICTURES;
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static File getExternalVideoDataDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        }
        if (BuildConfig.DEBUG) {
            Log.d("DiskUtil", context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath());
        }
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/files/Movies";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static File getDiskDataDir(Context context) {
        return isExternalStorageAvailable() ? getExternalDataDir(context) : null;
    }

    public static File getDiskVideoDataDir(Context context) {
        return isExternalStorageAvailable() ? getExternalVideoDataDir(context) : null;
    }

    private static File getFileStoreDir(String fileStoreDirPath, String fileName, String fileSuffix) {
        String fileOrDirPath = fileStoreDirPath;
        if (fileName != null) {
            fileOrDirPath += fileName + (fileSuffix == null ? "" : fileSuffix);
        }
        return new File(fileOrDirPath);
    }

    /**
     * 这个方法返回一个符合welove规范的文件系统路径，路径或者文件不存在不会自动创建，这只是一个缓存目录，会随时被清理掉
     *
     * @param context
     * @param dirName 这个主要用来标记你所创建的路径的最后一个文件夹的名称，例如Timeline的所有缓存图片你可能会命名为"timeline"
     * @return null, if the external storage is not available
     */
    public static File getImageFileStoreDir(Context context, String dirName) {
        File diskCacheDir = getDiskCacheDir(context);
        if (diskCacheDir != null) {
            String fileStoreDirPath = diskCacheDir.getAbsolutePath() + File.separator + dirName;
            File fileStoreDir = new File(fileStoreDirPath);
            if (!fileStoreDir.exists()) {
                fileStoreDir.mkdirs();
            }
            return fileStoreDir;
        }
        return null;
    }

    /**
     * 这个方法返回一个符合welove规范的文件系统路径，路径或者文件不存在不会自动创建，这只是一个缓存目录，会随时被清理掉
     *
     * @param diskCacheDir 这个主要用来标记你所创建的路径的最后一个文件夹的名称，例如Timeline的所有缓存图片你可能会命名为"timeline"
     * @param fileName     图片的名称
     * @return null, if the external storage is not available
     */
    public static File getImageFileStorePath(File diskCacheDir, String fileName, String fileSuffix) {
        if (diskCacheDir != null) {
            Log.d(LOG_TAG, "getImageFileStorePath exists " + diskCacheDir.exists());
            if (!diskCacheDir.exists()) {
                diskCacheDir.mkdirs();
            }
            String fileStoreDirPath = diskCacheDir.getAbsolutePath() + File.separator;
            return getFileStoreDir(fileStoreDirPath, fileName, fileSuffix);
        }
        return null;
    }

    /**
     * 这个方法返回一个符合welove规范的文件系统路径，路径或者文件不存在不会自动创建；这是一个数据文件目录，不会被缓存清理机制清理掉
     *
     * @param context
     * @param pathName 这个主要用来标记你所创建的路径的最后一个文件夹的名称，例如Timeline的所有缓存图片你可能会命名为"timeline"
     * @param fileName 图片的名称
     * @return 返回一个文件对象，null if the external storage is not available
     */
    public static File getImageDataFileStoreDir(Context context, String pathName, String fileName, String fileSuffix) {
        File diskDataDir = DiskUtil.getDiskDataDir(context);
        if (diskDataDir != null) {
            String fileStoreDirPath = diskDataDir.getAbsolutePath() + File.separator;
            if (pathName != null) {
                fileStoreDirPath = diskDataDir.getAbsolutePath() + File.separator + pathName + File.separator;
            }
            Log.d(LOG_TAG, "fileStoreDirPath is " + fileStoreDirPath);
            return getFileStoreDir(fileStoreDirPath, fileName, fileSuffix);
        }
        Log.d(LOG_TAG, "diskDataDir is " + diskDataDir);
        return null;
    }

    public static File getVideoDataFileStoreDir(Context context, String pathName, String fileName, String fileSuffix) {
        File diskVideoDataDir = DiskUtil.getDiskVideoDataDir(context);
        if (diskVideoDataDir != null) {
            String fileStoreDirPath = diskVideoDataDir.getAbsolutePath() + File.separator + pathName + File.separator;
            return getFileStoreDir(fileStoreDirPath, fileName, fileSuffix);
        }
        return null;
    }

    /**
     * 这个方法返回一个符合welove规范的文件系统路径
     *
     * @param context
     * @param pathName
     * @param fileName
     * @param fileSuffix
     * @return 返回图片文件的路径
     */
    public static String getImageDataFileStorePath(Context context, String pathName, String fileName, String fileSuffix) {
        File diskDataDir = DiskUtil.getDiskDataDir(context);
        if (diskDataDir != null) {
            String fileStoreDirPath = diskDataDir.getAbsolutePath() + File.separator + pathName + File.separator;
            if (fileName != null) {
                fileStoreDirPath += fileName + (fileSuffix == null ? "" : fileSuffix);
            }
            return fileStoreDirPath;
        }
        return null;
    }

    /**
     * 通过传入图片地址返回对应的Drawable对象
     *
     * @return 对应的Drawable对象
     */
    public static BitmapDrawable getFileDrawable(Context context, String pathName, String fileName, String fileSuffix) {
        String filePath = getImageDataFileStorePath(context, pathName, fileName, fileSuffix);
        if (filePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return new BitmapDrawable(bitmap);
        }
        return null;
    }

    /**
     * @param context
     * @param pathName
     * @param fileName
     * @param fileSuffix
     * @return 判断一个文件是否存在
     */
    public static boolean isDataFileExists(Context context, String pathName, String fileName, String fileSuffix) {
        File dataFileStoreDir = getDataFileStoreDir(context, pathName, fileName, fileSuffix);
        if (dataFileStoreDir != null) {
            return dataFileStoreDir.exists();
        }
        return false;
    }

    public static boolean isVideoDataFileExists(Context context, String pathName, String fileName, String fileSuffix) {
        File diskVideoDataDir = DiskUtil.getDiskVideoDataDir(context);
        if (diskVideoDataDir != null) {
            String pathAndName = diskVideoDataDir.getAbsolutePath() + pathName + File.separator + fileName + fileSuffix;
            File file = new File(pathAndName);
            return file.exists();
        }
        return false;
    }

    public static File getDataFileStoreDir(Context context, String pathName, String fileName, String fileSuffix) {
        String fileNameStr = getFileName(context, pathName, fileName, fileSuffix);
        if (fileNameStr != null) {
            return new File(fileNameStr);
        }
        return null;
    }

    public static String getFileName(Context context, String pathName, String fileName, String fileSuffix) {
        File diskDataDir = DiskUtil.getDiskDataDir(context);
        if (diskDataDir != null) {
            return diskDataDir.getAbsolutePath() + pathName + File.separator + fileName + fileSuffix;
        }
        return null;
    }

    /**
     * Write the given bitmap into the given file. JPEG is used as the compression format with
     * quality set
     * to 100.
     *
     * @param bm   The bitmap.
     * @param file The file to write the bitmap into.
     */
    public static void writeBitmapToFile(Bitmap bm, File file, int quality) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
