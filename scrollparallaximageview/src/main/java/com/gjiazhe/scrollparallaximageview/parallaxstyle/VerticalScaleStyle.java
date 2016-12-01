package com.gjiazhe.scrollparallaximageview.parallaxstyle;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;

/**
 * When the imageView is scrolling vertically, the image in imageView will be scaled.
 * The scale ratio is according to the vertical position of the imageView and range
 * from 1.0f to <code>finalScaleRatio</code>.
 * When the imageView is at the middle of the screen, the scale ratio is 1.0f. And When
 * it just scroll out of the screen, the scale ratio is <code>finalScaleRatio</code>.
 *
 * Created by gjz on 25/11/2016.
 */

public class VerticalScaleStyle implements ScrollParallaxImageView.VerticalParallaxStyle {
    private float finalScaleRatio = 0.7f;

    private ScrollParallaxImageView.VerticalParallaxStyle innerVerticalStyle;

    public VerticalScaleStyle() {}

    public VerticalScaleStyle(@NonNull ScrollParallaxImageView.VerticalParallaxStyle style) {
        this.innerVerticalStyle = style;
    }

    public VerticalScaleStyle(float finalScaleRatio) {
        this.finalScaleRatio = finalScaleRatio;
    }

    public VerticalScaleStyle(float finalScaleRatio, @NonNull ScrollParallaxImageView.VerticalParallaxStyle style) {
        this(finalScaleRatio);
        this.innerVerticalStyle = style;
    }

    public void setFinalScaleRatio(float scale) {
        finalScaleRatio = scale;
    }

    public void setInnerVerticalStyle(ScrollParallaxImageView.VerticalParallaxStyle innerVerticalStyle) {
        this.innerVerticalStyle = innerVerticalStyle;
    }

    @Override
    public void transform(ScrollParallaxImageView view, Canvas canvas, int x, int y) {
        // view's width and height
        int vWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        int vHeight = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        // device's height
        int dHeight = view.getResources().getDisplayMetrics().heightPixels;

        if (vHeight >= dHeight) {
            // Do nothing if imageView's height is bigger than device's height.
            return;
        }

        float scale;
        int pivot = (dHeight - vHeight) / 2;
        if (y <= pivot) {
            scale = 2 * (1 - finalScaleRatio) * (y + vHeight) / (dHeight + vHeight) + finalScaleRatio;
        } else {
            scale = 2 * (1 - finalScaleRatio) * (dHeight - y) / (dHeight + vHeight) + finalScaleRatio;
        }

        canvas.scale(scale, scale, vWidth/2, vHeight/2);

        if (innerVerticalStyle != null) {
            innerVerticalStyle.transform(view, canvas, x, y);
        }
    }

    @Override
    public void onAttachedToImageView(ScrollParallaxImageView view) {
        if (innerVerticalStyle != null) {
            innerVerticalStyle.onAttachedToImageView(view);
        }
    }

    @Override
    public void onDetachedFromImageView(ScrollParallaxImageView view) {
        if (innerVerticalStyle != null) {
            innerVerticalStyle.onDetachedFromImageView(view);
        }
    }
}
