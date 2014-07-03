package jp.goka.favos.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.request.volley.VolleyManager;

import java.util.List;
import java.util.Random;

/**
 * Created by katsuyagoto on 2014/03/03.
 */
public class KenBurnsView extends FrameLayout {

	public enum Mode{
		URL, FILE;
	}

	private int mImageNum = 3;
	private Context mContext;
	private final Handler mHandler;
	private ImageView[] mImageViews;
	private int mActiveImageIndex = -1;

	private final Random mRandom = new Random();
	private int mSwapMs = 11000;
	private int mFadeInOutMs = 500;

	private float maxScaleFactor = 1.5F;
	private float minScaleFactor = 1.2F;

	private FrameLayout mRoot;
	private int mPosition = 0;
	private int mPrevPosition = 0;

	private List<String> mUrls;
	private LoopViewPager mPager;

	private ImageView.ScaleType mScaleType = null;

	private float mScaleX = 0f;
	private float mScaleY = 0f;

	private Mode mode = Mode.URL;

	public KenBurnsView(Context context) {
		this(context, null);
	}

	public KenBurnsView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public KenBurnsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHandler = new Handler();
		mContext = context;
	}

	public void setScaleType(ImageView.ScaleType mScaleType) {
		this.mScaleType = mScaleType;
	}

	public void setScale(float sx, float sy) {
		this.mScaleX = sx;
		this.mScaleY = sy;
	}

	public void setScaleImageView(ImageView imageView) {
		Matrix m = imageView.getImageMatrix();
		m.reset();
		m.postScale(mScaleX, mScaleY);
		imageView.setImageMatrix(m);
	}

	public void setPager(LoopViewPager mPager) {
		this.mPager = mPager;
	}

	public void forceSelected(int position){
		mPrevPosition = mPosition;
		if(mHandler != null){
			stopKenBurnsAnimation();
			startForceKenBurnsAnimation();
		}
		mPosition = position;
	}

	private void forceSwapImage() {

		if(mImageViews.length <= 0){
			return;
		}

		if(mActiveImageIndex == -1) {
			mActiveImageIndex = 0;
			animate(mImageViews[mActiveImageIndex]);
			return;
		}

		int inactiveIndex = mActiveImageIndex;

		if(mPrevPosition >= mPosition){
			mActiveImageIndex = swapDirection(mActiveImageIndex, true);
		}else {
			mActiveImageIndex = swapDirection(mActiveImageIndex, false);
		}

		if(mPrevPosition == 0 && mPosition == mUrls.size()-1){
			mActiveImageIndex = swapDirection(mActiveImageIndex, true);
		}

		if(mPrevPosition == mUrls.size()-1 && mPosition == 0){
			mActiveImageIndex = swapDirection(mActiveImageIndex, false);
		}

		if(mActiveImageIndex >= mImageViews.length){
			mActiveImageIndex = 0;
		}

		final ImageView activeImageView = mImageViews[mActiveImageIndex];
		loadImage(mActiveImageIndex, mPosition);
		activeImageView.setAlpha(0.0f);

		ImageView inactiveImageView = mImageViews[inactiveIndex];

		animate(activeImageView);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(mFadeInOutMs);
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(inactiveImageView, "alpha", 1.0f, 0.0f),
				ObjectAnimator.ofFloat(activeImageView, "alpha", 0.0f, 1.0f)
		);
		animatorSet.start();
	}


	private void autoSwapImage() {

		if(mImageViews.length <= 0){
			return;
		}

		if(mActiveImageIndex == -1) {
			mActiveImageIndex = 0;
			animate(mImageViews[mActiveImageIndex]);
			return;
		}

		int inactiveIndex = mActiveImageIndex;
		mActiveImageIndex = (1 + mActiveImageIndex);

		if(mActiveImageIndex >= mImageViews.length){
			mActiveImageIndex = 0;
		}

		if(mPager != null){
			mPosition++;
			if(mPosition >= mUrls.size()){
				mPosition = 0;
			}

			mPager.setCurrentItemAfterCancelListener(mPosition, false);
		}

		final ImageView activeImageView = mImageViews[mActiveImageIndex];
		loadImage(mActiveImageIndex, mPosition);
		activeImageView.setAlpha(0.0f);

		ImageView inactiveImageView = mImageViews[inactiveIndex];

		animate(activeImageView);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(mFadeInOutMs);
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(inactiveImageView, "alpha", 1.0f, 0.0f),
				ObjectAnimator.ofFloat(activeImageView, "alpha", 0.0f, 1.0f)
		);
		animatorSet.start();
	}

	private void start(ImageView view, long duration, float fromScale, float toScale, float fromTranslationX, float fromTranslationY, float toTranslationX, float toTranslationY) {
		view.setScaleX(fromScale);
		view.setScaleY(fromScale);
		view.setTranslationX(fromTranslationX);
		view.setTranslationY(fromTranslationY);
		ViewPropertyAnimator propertyAnimator = view.animate().
				translationX(toTranslationX).
				translationY(toTranslationY).
				scaleX(toScale).
				scaleY(toScale).
				setDuration(duration);
		propertyAnimator.start();
	}

	private void preStart(ImageView view, long duration, float fromScale, float toScale, float fromTranslationX, float fromTranslationY) {
		view.setScaleX(fromScale);
		view.setScaleY(fromScale);
		view.setTranslationX(fromTranslationX);
		view.setTranslationY(fromTranslationY);
		ViewPropertyAnimator propertyAnimator = view.animate().
				scaleX(toScale).
				scaleY(toScale).
				setDuration(duration);
		propertyAnimator.start();
	}

	private float pickScale() {
		return this.minScaleFactor + this.mRandom.nextFloat() * (this.maxScaleFactor - this.minScaleFactor);
	}

	private float pickTranslation(int value, float ratio) {
		return value * (ratio - 1.0f) * (this.mRandom.nextFloat() - 0.5f);
	}

	public void animate(final ImageView view) {
		final float fromScale = pickScale();
		final float toScale = pickScale();
		final float fromTranslationX = pickTranslation(view.getWidth(), fromScale);
		final float fromTranslationY = pickTranslation(view.getHeight(), fromScale);
		final float toTranslationX = pickTranslation(view.getWidth(), toScale);
		final float toTranslationY = pickTranslation(view.getHeight(), toScale);

		start(view, mSwapMs, fromScale, toScale, fromTranslationX, fromTranslationY, toTranslationX, toTranslationY);

		/*preStart(view, mSwapMs, fromScale, toScale, fromTranslationX, fromTranslationY);

		Drawable drawable = view.getDrawable();
		if(drawable != null && ((BitmapDrawable)view.getDrawable()).getBitmap() != null){
			Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
			findFacePoint(bitmap, new onFaceCallBack() {
				@Override
				public void onFind(PointF point) {
					float toX = pickTranslation((int) point.x, toScale);
					float toY = pickTranslation((int) point.y, toScale);
					start(view, mSwapMs, fromScale, toScale, fromTranslationX, fromTranslationY, toX, toY);
				}

				@Override
				public void onNotFind() {
					start(view, mSwapMs, fromScale, toScale, fromTranslationX, fromTranslationY, toTranslationX, toTranslationY);
				}
			});
		}else {
			start(view, mSwapMs, fromScale, toScale, fromTranslationX, fromTranslationY, toTranslationX, toTranslationY);
		}*/

	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startKenBurnsAnimation();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopKenBurnsAnimation();
	}

	private Runnable mSwapImageRunnable = new Runnable() {
		@Override
		public void run() {
			autoSwapImage();
			mHandler.postDelayed(mSwapImageRunnable, mSwapMs - mFadeInOutMs*2);
		}
	};

	private Runnable mForceSwapImageRunnable = new Runnable() {
		@Override
		public void run() {
			forceSwapImage();
			mHandler.postDelayed(mSwapImageRunnable, mSwapMs - mFadeInOutMs*2);
		}
	};

	private void stopKenBurnsAnimation(){
		mHandler.removeCallbacks(mSwapImageRunnable);
		mHandler.removeCallbacks(mForceSwapImageRunnable);
	}

	private void startKenBurnsAnimation() {
		mHandler.post(mSwapImageRunnable);
	}

	private void startForceKenBurnsAnimation() {
		mHandler.post(mForceSwapImageRunnable);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		View view = inflate(getContext(), R.layout.layout_ken_burns_view, this);
		mRoot = (FrameLayout)view.findViewById(R.id.ken_burns_root);
	}

	public void initUrls(List<String> urls) {
		mode = Mode.URL;
		mUrls = urls;
		if(mRoot != null){
			createImages(mRoot);
		}
	}

	public void initFiles(List<String> urls) {
		mode = Mode.FILE;
		mUrls = urls;
		if(mRoot != null){
			createImages(mRoot);
		}
	}

	public void createImages(FrameLayout root){

		mImageViews = new ImageView[mImageNum];

		for(int i=mImageNum-1; i>=0; i--){
			mImageViews[i] = new ImageView(mContext);

			if(i != 0){
				mImageViews[i].setAlpha(0.0f);
			}else {
				loadImage(i,i);
			}

			if(mScaleType != null){
				mImageViews[i].setScaleType(mScaleType);
			}else {
				if(mScaleX != 0f && mScaleY != 0f){
					setScaleImageView(mImageViews[i]);
				}
			}


			mImageViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			root.addView(mImageViews[i]);
		}
	}

	private void displayImage(String url , final ImageView imageView){
		switch (mode) {
			case URL:
				VolleyManager.getInstance().getImageLoader().get(url, new ImageLoader.ImageListener() {
					@Override
					public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						if (bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
				break;
			case FILE:
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeFile(url, options);
				imageView.setImageBitmap(bitmap);
				break;
		}
	}

	private void loadImage(final int activeIndex, int position){

		if(mUrls.isEmpty()){
			return;
		}

		if(mScaleType != null){
			for(int i=0; i< mImageViews.length; i++){
				mImageViews[i].setScaleType(mScaleType);
			}
		}

		displayImage(mUrls.get(position), mImageViews[activeIndex]);

		int prePosition  = position - 1;
		int nextPosition = position + 1;

		if(prePosition < 0){
			prePosition = mUrls.size()-1;
		}

		if(nextPosition > mUrls.size()-1){
			nextPosition = 0;
		}

		if(activeIndex == 0){
			displayImage(mUrls.get(prePosition), mImageViews[2]);
			displayImage(mUrls.get(nextPosition), mImageViews[1]);
		}else if(activeIndex == 1){
			displayImage(mUrls.get(prePosition), mImageViews[0]);
			displayImage(mUrls.get(nextPosition), mImageViews[2]);
		}else if(activeIndex == 2){
			displayImage(mUrls.get(prePosition), mImageViews[1]);
			displayImage(mUrls.get(nextPosition), mImageViews[0]);
		}
	}

	private int swapDirection(final int activeIndex, boolean isPrev){
		if(activeIndex == 0){
			if(isPrev){
				return 2;
			}else {
				return 1;
			}
		}else if(activeIndex == 1){
			if(isPrev){
				return 0;
			}else {
				return 2;
			}

		}else if(activeIndex == 2){
			if(isPrev){
				return 1;
			}else {
				return 0;
			}
		}

		return 0;
	}

}