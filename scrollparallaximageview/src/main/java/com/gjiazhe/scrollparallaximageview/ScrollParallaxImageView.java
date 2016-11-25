package com.gjiazhe.scrollparallaximageview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by gjz on 25/11/2016.
 */

public class ScrollParallaxImageView extends ImageView implements ViewTreeObserver.OnScrollChangedListener {
    private int[] viewLocation = new int[2];
    private boolean enableScrollParallax = true;

    // view's width and height
    private int vWidth;
    private int vHeight;
    // device's width and height
    private int dWidth;
    private int dHeight;

    public ScrollParallaxImageView(Context context) {
        this(context, null);
    }

    public ScrollParallaxImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollParallaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(ScaleType.CENTER_CROP);

        dWidth = getResources().getDisplayMetrics().widthPixels;
        dHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        vWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        vHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!enableScrollParallax || getDrawable() == null) {
            super.onDraw(canvas);
            return;
        }

        int iWidth = getDrawable().getIntrinsicWidth();
        int iHeight = getDrawable().getIntrinsicHeight();
        if (iWidth <= 0 || iHeight <= 0) {
            super.onDraw(canvas);
            return;
        }

        getLocationInWindow(viewLocation);
        if (iWidth * vHeight > vWidth * iHeight) {
            int currentX = viewLocation[0];
            if (currentX < -vWidth) {
                currentX = -vWidth;
            } else if (currentX > dWidth) {
                currentX = dWidth;
            }
            float imgScale = (float) vHeight / (float) iHeight;
            float max_dx = Math.abs((vWidth - iWidth * imgScale) * 0.5f);
            float translateX = -(2 * max_dx * currentX + max_dx * (vWidth - dWidth)) / (vWidth + dWidth);
            canvas.translate(translateX, 0);
        } else {
            int currentY = viewLocation[1];
            if (currentY < -vHeight) {
                currentY = -vHeight;
            } else if (currentY > dHeight) {
                currentY = dHeight;
            }
            float imgScale = (float) vWidth / (float) iWidth;
            float max_dy = Math.abs((vHeight - iHeight * imgScale) * 0.5f);
            float translateY = -(2 * max_dy * currentY + max_dy * (vHeight - dHeight)) / (vHeight + dHeight);
            canvas.translate(0, translateY);
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnScrollChangedListener(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollChanged() {
        if (enableScrollParallax) {
            invalidate();
        }
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        // The scaleType must be CENTER_CROP, so do nothing in this method.
    }

    public void setEnableScrollParallax(boolean enableScrollParallax) {
        this.enableScrollParallax = enableScrollParallax;
    }
}
