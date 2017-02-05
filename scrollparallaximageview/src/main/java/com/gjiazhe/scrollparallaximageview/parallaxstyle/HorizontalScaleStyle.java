package com.gjiazhe.scrollparallaximageview.parallaxstyle;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;

import java.io.FileInputStream;

/**
 * When the imageView is scrolling horizontally, the image in imageView will be scaled.
 * The scale ratio is according to the horizontal position of the imageView and range
 * from 1.0f to <code>finalScaleRatio</code>.
 * When the imageView is at the middle of the screen, the scale ratio is 1.0f. And When
 * it just scroll out of the screen, the scale ratio is <code>finalScaleRatio</code>.
 *
 * Created by gjz on 26/11/2016.
 */

public class HorizontalScaleStyle implements ScrollParallaxImageView.HorizontalParallaxStyle {
    private float finalScaleRatio = 0.7f;

    private ScrollParallaxImageView.HorizontalParallaxStyle innerHorizontalStyle;

    public HorizontalScaleStyle() {}

    public HorizontalScaleStyle(@NonNull ScrollParallaxImageView.HorizontalParallaxStyle style) {
        this.innerHorizontalStyle = style;
    }

    public HorizontalScaleStyle(float finalScaleRatio) {
        this.finalScaleRatio = finalScaleRatio;
    }

    public HorizontalScaleStyle(float finalScaleRatio, @NonNull ScrollParallaxImageView.HorizontalParallaxStyle style) {
        this(finalScaleRatio);
        this.innerHorizontalStyle = style;
    }

    public void setFinalScaleRatio(float scale) {
        finalScaleRatio = scale;
    }

    public void setInnerHorizontalStyle(ScrollParallaxImageView.HorizontalParallaxStyle innerHorizontalStyle) {
        this.innerHorizontalStyle = innerHorizontalStyle;
    }

    @Override
    public void transform(ScrollParallaxImageView view, Canvas canvas, int x, int y) {
        // view's width and height
        int vWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        int vHeight = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        // device's width
        int dWidth = view.getResources().getDisplayMetrics().widthPixels;

        if (vWidth >= dWidth) {
            // Do nothing if imageView's width is bigger than device's width.
            return;
        }

        float scale;
        int pivot = (dWidth - vWidth) / 2;
        if (x <= pivot) {
            scale = 2 * (1 - finalScaleRatio) * (x + vWidth) / (dWidth + vWidth) + finalScaleRatio;
        } else {
            scale = 2 * (1 - finalScaleRatio) * (dWidth - x) / (dWidth + vWidth) + finalScaleRatio;
        }

        canvas.scale(scale, scale, vWidth/2, vHeight/2);

        if (innerHorizontalStyle != null) {
            innerHorizontalStyle.transform(view, canvas, x, y);
        }
    }

    @Override
    public void onAttachedToImageView(ScrollParallaxImageView view) {
        if (innerHorizontalStyle != null) {
            innerHorizontalStyle.onAttachedToImageView(view);
        }
    }

    @Override
    public void onDetachedFromImageView(ScrollParallaxImageView view) {
        if (innerHorizontalStyle != null) {
            innerHorizontalStyle.onDetachedFromImageView(view);
        }
    }
}
