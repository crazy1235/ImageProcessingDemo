package com.jacksen.image.processing.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author jacksen
 * @create_date 2017/3/4.
 * @desc Bitmap工具类
 */

public class BitmapUtil {

    /**
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // first decode with inJustDecodeBounds = true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * @param targetFile
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromLocal(File targetFile, int reqWidth, int reqHeight) {
        if (targetFile == null || !targetFile.isFile() || !targetFile.canRead()) {
            return null;
        }

        return decodeSampledBitmapFromLocalByCompress(targetFile, reqWidth, reqHeight, 80);
    }

    /**
     * @param targetFile
     * @param reqWidth
     * @param reqHeight
     * @param quality
     * @return
     */
    public static Bitmap decodeSampledBitmapFromLocalByCompress(File targetFile, int reqWidth, int reqHeight, int quality) {
        // TODO judge the file is pic or not


        if (quality < 0 || quality > 100) {
            quality = 100;
        }

        // first decode with inJustDecodeBounds = true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(targetFile.getPath(), options);

        // calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(targetFile.getPath(), options);

        // compress bitmap
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * calculate the inSampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // raw height and width of the image
        int height = options.outHeight;
        int width = options.outWidth;

        // default inSampleSize value
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            // calculate the largest inSampleSize value that is a power of 2and keeps both
            // height and width larger than the requested height and width
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * return the rounded corner bitmap
     *
     * @param originBitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap originBitmap, int xRadius, int yRadius) {
        if (xRadius <= 0 || yRadius <= 0) {
            return originBitmap;
        }
        Bitmap outputBitmap = Bitmap.createBitmap(originBitmap.getWidth(), originBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, originBitmap.getWidth(), originBitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setColor(0xff424242);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, xRadius, yRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, rect, rect, paint);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return outputBitmap;
    }


    /**
     * compound two bitmap
     * the objectBitmap is default locate in the left-top corner
     *
     * @param srcBitmap
     * @param objectBitmap
     * @return
     */
    public static Bitmap compoundBitmap(Bitmap srcBitmap, Bitmap objectBitmap) {
        if (srcBitmap == null) {
            return null;
        }
        if (objectBitmap == null) {
            return srcBitmap;
        }
        return compoundBitmap(srcBitmap, objectBitmap, 0, 0);
    }


    /**
     * compound two bitmap with the appointed position
     *
     * @param srcBitmap
     * @param objectBitmap
     * @param left
     * @param top
     * @return
     */
    public static Bitmap compoundBitmap(Bitmap srcBitmap, Bitmap objectBitmap, float left, float top) {
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();

        // create a new bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);

        // draw the src bitmap
        canvas.drawBitmap(srcBitmap, 0, 0, null);

        // draw the object bitmap
        canvas.drawBitmap(objectBitmap, left, top, null);

        // save
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return resultBitmap;
    }

    /**
     * 将objectBitmap合成到右下角，可以设置margin值
     *
     * @param srcBitmap
     * @param objectBitmap
     * @param margin       unit : px
     * @return
     */
    public static Bitmap compoundBitmapAtBottomRightCorner(Bitmap srcBitmap, Bitmap objectBitmap, int margin) {
        if (srcBitmap == null || objectBitmap == null) {
            return null;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();

        int objectWidth = objectBitmap.getWidth();
        int objectHeight = objectBitmap.getHeight();


        if (objectWidth + margin * 2 > srcWidth || objectHeight + margin * 2 > srcHeight) {
            margin = 0;
        }

        return compoundBitmap(srcBitmap, objectBitmap, (srcWidth - objectWidth - margin), (srcHeight - objectHeight - margin));
    }


    /**
     * rotate the src bitmap with some degrees
     *
     * @param srcBitmap
     * @param degrees
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap srcBitmap, float degrees) {
        if (srcBitmap == null) {
            return null;
        }

        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();

        // rotate matrix
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);

        return Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth, srcHeight, matrix, true);
    }

    /**
     * @param bitmap
     * @param padding
     * @param radius
     * @return
     */
    public static Bitmap createRoundBitmap(Bitmap bitmap, int padding, int radius) {
        if (bitmap == null) {
            return null;
        }
        Bitmap outputBitmap = Bitmap.createBitmap((bitmap.getWidth() + padding * 2), (bitmap.getWidth() + padding * 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(outputBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0, 0, outputBitmap.getWidth(), outputBitmap.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawPaint(paint);

        // 2.
//        canvas.drawBitmap(bitmap, padding, padding, null);

        // 3.
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return outputBitmap;
    }

    /**
     * @param originBitmap
     * @param radius
     * @return
     */
    public static Bitmap createCircleImage(Bitmap originBitmap, int radius) {
        Bitmap targetBitmap = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 1. circle
        canvas.drawCircle(radius, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 2.

        canvas.drawBitmap(originBitmap, (originBitmap.getWidth() - radius * 2) / 2, (originBitmap.getHeight() - radius * 2), paint);

        paint.setXfermode(null);

        return targetBitmap;
    }

}
