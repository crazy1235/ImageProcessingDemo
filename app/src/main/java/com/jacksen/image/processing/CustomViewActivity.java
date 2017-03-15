package com.jacksen.image.processing;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CustomViewActivity extends AppCompatActivity {

    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        imageView1 = (ImageView) findViewById(R.id.image_view1);


        Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.img1, 500, 500);

        imageView1.setImageBitmap(BitmapUtil.createCircleImage(bitmap, 200));

    }
}
