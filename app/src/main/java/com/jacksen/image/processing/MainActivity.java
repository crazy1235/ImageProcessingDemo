package com.jacksen.image.processing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jacksen.image.processing.utils.BitmapUtil;
import com.jacksen.image.processing.utils.FileUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private ImageView qrCodeIv;

    private ImageView addIv;

    private float lastX, lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_view);

        qrCodeIv = (ImageView) findViewById(R.id.qr_code_iv);


        qrCodeIv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        Log.d("ACTION_DOWN", lastX + "---" + lastY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - lastX;
                        float dy = event.getRawY() - lastY;

                        float left = v.getLeft() + dx;
                        float top = v.getTop() + dy;
                        float right = v.getRight() + dx;
                        float bottom = v.getBottom() + dy;

                        if (left < 0 || top < 0 || right > ((View) v.getParent()).getWidth() || bottom > ((View) v.getParent()).getHeight()) {
                            return true;
                        }

                        Log.d("ACTION_MOVE", left + "--" + top + "--" + right + "--" + bottom);

                        v.layout((int) left, (int) top, (int) right, (int) bottom);

                        lastX = event.getRawX();
                        lastY = event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("MainActivity", "lastX & lastY:" + lastX + "--" + lastY);
                        break;
                }
                return true;
            }
        });

        addIv = (ImageView) findViewById(R.id.add_iv);

        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "qrCodeIv.getLeft & getTop:" + qrCodeIv.getLeft() + "--" + qrCodeIv.getTop());

                Bitmap resultBitmap = BitmapUtil.compoundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.beauty),
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), qrCodeIv.getLeft(), qrCodeIv.getTop());
                FileUtil.saveBitmapToFile(resultBitmap);
            }
        });

//        imageView.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.beauty, 300, 300));

//        imageView.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.beauty, 500, 500), 50, 50));

        Bitmap srcBitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.beauty, 500, 500);

//        imageView.setImageBitmap(BitmapUtil.compoundBitmap(srcBitmap, BitmapFactory.decdeResource(getResources(), R.mipmap.ic_launcher), 120, 150));

//        imageView.setImageBitmap(BitmapUtil.getRotateBitmap(srcBitmap, 30));

//        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.beauty, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        String config = options.inPreferredConfig.name();
        int inScreenDensity = options.inScreenDensity;
        int inTargetDensity = options.inTargetDensity;
        int inDensity = options.inDensity;
        int inSampleSize = options.inSampleSize;
        Bitmap inBitmap = options.inBitmap;
        boolean inMutable = options.inMutable;

        Log.d("MainActivity", imageHeight + "--" + imageWidth + "--" + imageType + "--" + config + "--" + inScreenDensity + "--" + inTargetDensity
                + "--" + inDensity + "--" + inSampleSize + "--" + (inBitmap == null ? "null" : inBitmap.getByteCount()) + "--" + inMutable);
    }
}
