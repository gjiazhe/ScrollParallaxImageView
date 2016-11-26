package com.gjiazhe.scrollparallaximageview.parallaxstyle;

import android.graphics.Canvas;
import android.widget.ImageView;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;

/**
 * When the imageView is scrolling horizontally, the image in imageView will be
 * also scrolling horizontally if the image' width is bigger than imageView's width.
 *
 * The image will not over scroll to it's view bounds.
 *
 * Note: it only supports imageView with CENTER_CROP scale type.
 *
 * Created by gjz on 26/11/2016.
 */

public class HorizontalMovingStyle implements ScrollParallaxImageView.ParallaxStyle {
    @Override
    public void onAttachedToImageView(ScrollParallaxImageView view) {
        // only supports CENTER_CROP
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void onDetachedFromImageView(ScrollParallaxImageView view) {

    }

    @Override
    public void transform(ScrollParallaxImageView view, Canvas canvas, int x, int y) {
        if (view.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
            return;
        }

        // image's width and height
        int iWidth = view.getDrawable().getIntrinsicWidth();
        int iHeight = view.getDrawable().getIntrinsicHeight();
        if (iWidth <= 0 || iHeight <= 0) {
            return;
        }

        // view's width and height
        int vWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        int vHeight = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();

        // device's width
        int dWidth = view.getResources().getDisplayMetrics().widthPixels;

        if (iWidth * vHeight > iHeight * vWidth) {
            // avoid over scroll
            if (x < -vWidth) {
                x = -vWidth;
            } else if (x > dWidth) {
                x = dWidth;
            }

            float imgScale = (float) vHeight / (float) iHeight;
            float max_dx = Math.abs((iWidth * imgScale - vWidth) * 0.5f);
            float translateX = -(2 * max_dx * x + max_dx * (vWidth - dWidth)) / (vWidth + dWidth);
            canvas.translate(translateX, 0);
        }
    }
}
