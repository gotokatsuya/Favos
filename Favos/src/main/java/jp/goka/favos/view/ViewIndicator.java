package jp.goka.favos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import jp.goka.favos.R;

public class ViewIndicator extends View {

    private static final float RADIUS = 7.0f;
	private static final float DISTANCE = 30.0f;
	private static final int FILL = R.color.blue1;
	private static final int STROKE = R.color.white0;


    private int mNumOfViews;
    private int mPosition;
    private ViewPager mViewPager;

    public ViewIndicator(Context context) {
        super(context);
    }

    public ViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPosition(final int position) {
        if (position < mNumOfViews) {
            mPosition = position;
            if (mViewPager != null) {
                mViewPager.setCurrentItem(mPosition);
            }
            invalidate();
        }
    }

    public void setViewPager(final ViewPager viewPager) {
        mViewPager = viewPager;
        updateNumOfViews();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffest, int positionOffestPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateNumOfViews();
                setPosition(position);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

		for (int i = 0; i < mNumOfViews; i++) {
            float cx = (getWidth() - (mNumOfViews - 1) * DISTANCE) / 2 + i * DISTANCE;
            float cy = getHeight() / 2.0f;
            if (mPosition == i) {
				paint.setColor(getResources().getColor(FILL));
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
            } else {
				paint.setColor(getResources().getColor(STROKE));
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
            }
            canvas.drawCircle(cx, cy, RADIUS, paint);
        }
    }

    private void updateNumOfViews() {
        if (mViewPager.getAdapter() == null) {
            mNumOfViews = 0;
        } else {
            mNumOfViews = mViewPager.getAdapter().getCount();
        }
    }

}