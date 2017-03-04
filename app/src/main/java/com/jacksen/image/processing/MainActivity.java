package com.jacksen.image.processing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_view);
//        imageView.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.beauty, 300, 300));

        imageView.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.beauty, 500, 500)));

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
