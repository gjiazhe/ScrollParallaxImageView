package com.gjiazhe.scrollparallaximageview.parallaxstyle;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;

/**
 * When the imageView is scrolling vertically, the image in imageView will change its alpha.
 * The alpha is according to the vertical position of the imageView and range
 * from 1.0f to <code>finalAlpha</code>.
 * When the imageView is at the middle of the screen, the alpha is 1.0f. And When
 * it just scroll out of the screen, the alpha is <code>finalAlpha</code>.
 *
 * Created by gjz on 25/11/2016.
 */

public class VerticalAlphaStyle implements ScrollParallaxImageView.VerticalParallaxStyle {
    private float finalAlpha = 0.3f;

    private ScrollParallaxImageView.VerticalParallaxStyle innerVerticalStyle;

    public VerticalAlphaStyle() {}

    public VerticalAlphaStyle(@NonNull ScrollParallaxImageView.VerticalParallaxStyle style) {
        this.innerVerticalStyle = style;
    }

    public VerticalAlphaStyle(float finalAlpha) {
        if (finalAlpha < 0 || finalAlpha > 1.0f) {
            throw new IllegalArgumentException("the alpha must between 0 and 1.");
        }
        this.finalAlpha = finalAlpha;
    }

    public VerticalAlphaStyle(float finalAlpha, @NonNull ScrollParallaxImageView.VerticalParallaxStyle style) {
        this(finalAlpha);
        this.innerVerticalStyle = style;
    }

    public void setFinalAlpha(float alpha) {
        finalAlpha = alpha;
    }

    public void setInnerVerticalStyle(ScrollParallaxImageView.VerticalParallaxStyle innerVerticalStyle) {
        this.innerVerticalStyle = innerVerticalStyle;
    }

    @Override
    public void transform(ScrollParallaxImageView view, Canvas canvas, int x, int y) {
        // view's height
        int vHeight = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        // device's height
        int dHeight = view.getResources().getDisplayMetrics().heightPixels;

        if (vHeight >= dHeight) {
            // Do nothing if imageView's height is bigger than device's height.
            return;
        }

        float alpha;
        int pivot = (dHeight - vHeight) / 2;
        if (y <= pivot) {
            alpha = 2 * (1 - finalAlpha) * (y + vHeight) / (dHeight + vHeight) + finalAlpha;
        } else {
            alpha = 2 * (1 - finalAlpha) * (dHeight - y) / (dHeight + vHeight) + finalAlpha;
        }
        view.setAlpha(alpha);

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
