package com.boohee.scroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by sky on 16/4/29.
 */
public class ScrollTextView extends TextView {
    private static final String sFormatStr = "vX=%.1f\nvY=%.1f";
    private static int DEFAULT_DURATION = 500;
    private Context mContext;
    private Scroller mScroller;
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;
    private int mPointerId;
    private int mMaxVelocity;
    private Paint mPaint;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    public ScrollTextView(Context context) {
        this(context, null);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mScroller = new Scroller(mContext);
        mOverScroller = new OverScroller(mContext);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(getTextSize());
        mMaxVelocity = ViewConfiguration.getMaximumFlingVelocity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLeft = getLeft();
        mRight = getRight();
        mTop = getTop();
        mBottom = getBottom();
        canvas.drawText(String.format(mContext.getString(R.string.left_top_string), mLeft, mTop), 0 , 50, mPaint);
        canvas.drawText(String.format(mContext.getString(R.string.right_bottom_string), mRight, mBottom), 20 , 170, mPaint);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            offsetLeftAndRight(mScroller.getCurrX() - mLeft);
            offsetTopAndBottom(mScroller.getCurrY() - mTop);
            invalidate();
        }
        if (mOverScroller.computeScrollOffset()) {
            offsetLeftAndRight(mOverScroller.getCurrX() - mLeft);
            offsetTopAndBottom(mOverScroller.getCurrY() - mTop);
            invalidate();
        }
    }

    public void startScrollerScroll() {
        mScroller.startScroll(mLeft, mTop, 0, -200, DEFAULT_DURATION);
        invalidate();
    }

    public void startScrollerFling() {
        mScroller.fling(mLeft, mTop, 0, -5000, mLeft, mLeft, 200, 1200);
        invalidate();
    }

    public void startOverScrollerScroll() {
        mOverScroller.startScroll(mLeft, mTop, 0, -200, DEFAULT_DURATION);
        invalidate();
    }

    public void startOverScrollerFling() {
        mOverScroller.fling(mLeft, mTop, 0, -5000, mLeft, mLeft, 200, 1200);
        invalidate();
    }

    public void startOverScrollerSpringBack() {
        mOverScroller.springBack(mLeft, mTop, mLeft + 1, mLeft + 1, 1100, 1200);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        acquireVelocityTracker(event);
        final VelocityTracker verTracker = mVelocityTracker;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //求第一个触点的id， 此时可能有多个触点，但至少一个
                mPointerId = event.getPointerId(0);
                break;

            case MotionEvent.ACTION_MOVE:
                //求伪瞬时速度
                verTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityX = verTracker.getXVelocity(mPointerId);
                final float velocityY = verTracker.getYVelocity(mPointerId);
                recodeInfo(velocityX, velocityY);
                break;

            case MotionEvent.ACTION_UP:
                releaseVelocityTracker();
                break;

            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;

            default:
                break;
        }
        return true;
    }

    /**
     *
     * @param event 向VelocityTracker添加MotionEvent
     *
     * @see android.view.VelocityTracker#obtain()
     * @see android.view.VelocityTracker#addMovement(MotionEvent)
     */
    private void acquireVelocityTracker(final MotionEvent event) {
        if(null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 释放VelocityTracker
     *
     * @see android.view.VelocityTracker#clear()
     * @see android.view.VelocityTracker#recycle()
     */
    private void releaseVelocityTracker() {
        if(null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 记录当前速度
     *
     * @param velocityX x轴速度
     * @param velocityY y轴速度
     */
    private void recodeInfo(final float velocityX, final float velocityY) {
        String info = String.format(sFormatStr, velocityX, velocityY);
        setText(info);
    }
}
