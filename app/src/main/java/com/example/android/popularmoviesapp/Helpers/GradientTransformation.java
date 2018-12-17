package com.example.android.popularmoviesapp.Helpers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class GradientTransformation implements Transformation {
    private int startColor = Color.argb(180, 0, 0, 0);
    private int endColor = Color.argb(0, 255, 255, 255);

    @Override
    public Bitmap transform(Bitmap source) {
        int x = source.getWidth();
        int y = source.getHeight();

        Bitmap gradientBitmap = source.copy(source.getConfig(), true);
        Canvas canvas = new Canvas(gradientBitmap);
        LinearGradient gradient = new LinearGradient(x/2, y, x/2, y/2, startColor, endColor, Shader.TileMode.CLAMP);
        Paint p = new Paint(Paint.DITHER_FLAG);
        p.setShader(null);
        p.setDither(true);
        p.setFilterBitmap(true);
        p.setShader(gradient);
        canvas.drawPaint(p);
        source.recycle();
        return gradientBitmap;
    }

    @Override
    public String key() {
        return "Gradient";
    }
}
