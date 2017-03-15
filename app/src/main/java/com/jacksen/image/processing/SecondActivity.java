package com.jacksen.image.processing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView = (ImageView) findViewById(R.id.image_view);

        Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.img1, 200, 200);


        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        imageView.setImageBitmap(BitmapUtil.createRoundBitmap(bitmap, SystemUtil.dpToPixel(this, 8), SystemUtil.dpToPixel(this, 10)));
    }


}
