package com.jacksen.image.processing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Admin
 * @create_date 2017/3/15.
 * @desc 描述
 */

public class TestXfermodeView extends View {

    private Paint paint;

    private PorterDuffXfermode xfermode;

    public TestXfermodeView(Context context) {
        this(context, null);
    }

    public TestXfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestXfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     *
     */
    private void init() {
        paint = new Paint();

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    /**
     * @return
     */
    private Bitmap getCircleBitmap() {
        Bitmap circle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(circle);
        c.drawARGB(0, 0, 0, 0);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
        c.drawCircle(100, 100, 100, p);
        return circle;
    }

    private Bitmap getRectangleBitmap() {
        Bitmap rectangle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rectangle);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(0, 0, 200, 200, paint);
        return rectangle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap circleBitmap = getCircleBitmap();
        Bitmap rectangleBitmap = getRectangleBitmap();

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        canvas.drawARGB(0, 0, 0, 0);

        // 1. 先画正方形
        canvas.drawBitmap(rectangleBitmap, 100, 100, paint);

        //
        paint.setXfermode(xfermode);

        // 2. 后画圆形
        canvas.drawBitmap(circleBitmap, 0, 0, paint);



//        canvas.drawBitmap(circleBitmap, 0, 0, paint);
//        paint.setXfermode(xfermode);
//        canvas.drawBitmap(rectangleBitmap, 100, 100, paint);

    }
}
