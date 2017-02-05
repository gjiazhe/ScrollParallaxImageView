package com.gjiazhe.scrollparallaximageview.parallaxstyle;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;

/**
 * When the imageView is scrolling horizontally, the image in imageView will change its alpha.
 * The alpha is according to the horizontal position of the imageView and range
 * from 1.0f to <code>finalAlpha</code>.
 * When the imageView is at the middle of the screen, the alpha is 1.0f. And When
 * it just scroll out of the screen, the alpha is <code>finalAlpha</code>.
 *
 * Created by gjz on 26/11/2016.
 */

public class HorizontalAlphaStyle implements ScrollParallaxImageView.HorizontalParallaxStyle {
    private float finalAlpha = 0.3f;

    private ScrollParallaxImageView.HorizontalParallaxStyle innerHorizontalStyle;

    public HorizontalAlphaStyle() {}

    public HorizontalAlphaStyle(@NonNull ScrollParallaxImageView.HorizontalParallaxStyle style) {
        this.innerHorizontalStyle = style;
    }

    public HorizontalAlphaStyle(float finalAlpha) {
        if (finalAlpha < 0 || finalAlpha > 1.0f) {
            throw new IllegalArgumentException("the alpha must between 0 and 1.");
        }
        this.finalAlpha = finalAlpha;
    }

    public HorizontalAlphaStyle(float finalAlpha, @NonNull ScrollParallaxImageView.HorizontalParallaxStyle style) {
        this(finalAlpha);
        this.innerHorizontalStyle = style;
    }

    public void setFinalAlpha(float alpha) {
        finalAlpha = alpha;
    }

    public void setInnerHorizontalStyle(ScrollParallaxImageView.HorizontalParallaxStyle innerHorizontalStyle) {
        this.innerHorizontalStyle = innerHorizontalStyle;
    }

    @Override
    public void transform(ScrollParallaxImageView view, Canvas canvas, int x, int y) {
        // view's width
        int vWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        // device's width
        int dWidth = view.getResources().getDisplayMetrics().widthPixels;

        if (vWidth >= dWidth) {
            // Do nothing if imageView's width is bigger than device's width.
            return;
        }

        float alpha;
        int pivot = (dWidth - vWidth) / 2;
        if (x <= pivot) {
            alpha = 2 * (1 - finalAlpha) * (x + vWidth) / (dWidth + vWidth) + finalAlpha;
        } else {
            alpha = 2 * (1 - finalAlpha) * (dWidth - x) / (dWidth + vWidth) + finalAlpha;
        }
        view.setAlpha(alpha);

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
