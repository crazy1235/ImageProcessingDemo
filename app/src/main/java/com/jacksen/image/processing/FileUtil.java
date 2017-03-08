package com.jacksen.image.processing;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @auther jacksen
 * @create_date 2017/2/20.
 * @desc 描述
 */

public class FileUtil {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static File getOutputMediaFile(int type, String folderName) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return null;
        }
        File mediaStorageDir = null;
        if (!TextUtils.isEmpty(folderName)) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
        }

        if (mediaStorageDir == null) {
            return null;
        }

        //
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "img_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "img_" + timeStamp + ".mp4");
        }

        return mediaFile;
    }

    /**
     * @param type
     * @return
     */
    public static File getOutputMediaFile(int type) {
        return getOutputMediaFile(type, "");
    }

    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * save byte[]
     *
     * @param data
     * @return
     */
    public static boolean saveBytesToFile(byte[] data) {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (pictureFile == null) {
            return false;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // *************************** save bitmap to file ***************************** //

    /**
     * @param bitmap
     * @param filePath
     * @return
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, String filePath) {
        return saveBitmapToFile(bitmap, new File(filePath));
    }

    /**
     * save bitmap to file with timestamp file name
     *
     * @param bitmap
     * @return
     */
    public static boolean saveBitmapToFile(Bitmap bitmap) {
        return saveBitmapToFile(bitmap, getOutputMediaFile(MEDIA_TYPE_IMAGE));
    }


    /**
     * @param bitmap
     * @param targetFile
     * @return
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, File targetFile) {
        if (bitmap == null) {
            return false;
        }

        if (targetFile == null) {
            return false;
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // *************************** save bitmap to file ***************************** //


    // *************************** read bitmap from file ***************************** //

    /**
     * read bitmap from file
     *
     * @param targetFile
     * @return
     */
    public static Bitmap readBitmapFromFile(File targetFile) {
        if (targetFile == null || !targetFile.isFile() || !targetFile.canRead()) {
            return null;
        }
        return readBitmapFromFilePath(targetFile.getPath());
    }

    /**
     * read bitmap from file path
     *
     * @param filePath
     * @return
     */
    public static Bitmap readBitmapFromFilePath(String filePath) {
        // TODO  1. judge filePath type

        // TODO 2. read file

        return null;
    }


    // *************************** read bitmap from file ***************************** //


}
