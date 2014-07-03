package jp.goka.favos.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import jp.goka.favos.R;

/**
 * Created by katsuyagoto on 2014/03/05.
 */
public class SquaredImageView extends ImageView {

	private int mLength = 0;
	private boolean isWidth = false;
	private boolean isHeight = false;

	public SquaredImageView(Context context) {
		super(context);
	}

	public SquaredImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public SquaredImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquaredImageView, defStyle, 0);
		mLength = a.getInteger(R.styleable.SquaredImageView_length, 0);
		isWidth = a.getBoolean(R.styleable.SquaredImageView_is_width, false);
		isHeight = a.getBoolean(R.styleable.SquaredImageView_is_height, false);
		a.recycle();
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(isWidth) {
			setMeasuredDimension(getMeasuredWidth()+mLength, getMeasuredWidth());
		}else if(isHeight){
			setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()+mLength);
		}else {
			setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
		}
	}
}