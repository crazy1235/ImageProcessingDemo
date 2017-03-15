package com.jacksen.image.processing.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jacksen.image.processing.R;

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

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
    }

    /**
     * @return
     */
    private Bitmap getCircleBitmap() {
        Bitmap circle = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(circle);
        c.drawARGB(0, 0, 0, 0);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
        c.drawCircle(200, 200, 200, p);
        return circle;
    }

    /**
     * @return
     */
    private Bitmap getRectangleBitmap() {
        Bitmap rectangle = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rectangle);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(0, 0, 500, 500, paint);
        return rectangle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("TestXfermodeView", "onDraw");

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        Bitmap circleBitmap = getCircleBitmap();
        Bitmap rectangleBitmap = getRectangleBitmap();

        canvas.drawARGB(255, 139, 197, 186);

        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        // 1. 圆形
        canvas.drawBitmap(circleBitmap, 0, 0, paint);

        //
        paint.setXfermode(xfermode);

        // 2. 正方形
        canvas.drawBitmap(rectangleBitmap, 200, 200, paint);

        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
