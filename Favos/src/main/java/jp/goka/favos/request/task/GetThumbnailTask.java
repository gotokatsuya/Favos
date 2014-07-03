package jp.goka.favos.request.task;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class GetThumbnailTask extends AsyncTask<String, Bitmap, Bitmap> {
	private ImageView mImageView;
    private long mId;
	private Context mContext;
	private int placeHolder;
	private int mOrientation;
	private onCompleteListener listener;

	public interface onCompleteListener{
		public void onComplete(Bitmap result);
	}

    public GetThumbnailTask(Context context, ImageView imageView, long id, int orientation) {
		mContext = context;
        mImageView = imageView;
		mImageView.setTag(String.valueOf(id));
		mImageView.setImageResource(android.R.color.white);
		mId = id;
		mOrientation = orientation;
    }

	//Default is white color
	public void setPlaceHolder(int placeHolder) {
		this.placeHolder = placeHolder;
		if(mImageView != null){
			mImageView.setImageResource(placeHolder);
		}
	}

	@Override
    protected Bitmap doInBackground(String... urls) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inTargetDensity = DisplayMetrics.DENSITY_XHIGH;
		options.inDensity = DisplayMetrics.DENSITY_XHIGH;
		options.inScaled = true;

		ContentResolver cr = mContext.getContentResolver();

		Bitmap thumbnail = rotateImage(MediaStore.Images.Thumbnails.getThumbnail(cr, mId, MediaStore.Images.Thumbnails.MINI_KIND, options),
				mOrientation);
		MemCache.setImage(String.valueOf(mId), thumbnail);
		return thumbnail;
	}

	@Override
	public void onPreExecute(){
		Bitmap bitmap = MemCache.getImage(String.valueOf(mId));
		if (bitmap != null) {
			mImageView.setImageBitmap(bitmap);
			if(listener != null){
				listener.onComplete(bitmap);
			}
			cancel(true);
			return;
		}
	}


	@Override
    protected void onPostExecute(final Bitmap result) {
        if ((String.valueOf(mId)).equals(mImageView.getTag())) {
            if (result != null) {
				mImageView.setImageBitmap(result);
				if(listener != null){
					listener.onComplete(result);
				}
            }
        }
    }


	private static Bitmap rotateImage(Bitmap bitmap, int orientation) {
		if (bitmap !=null) {
			int h = bitmap.getHeight();
			int w = bitmap.getWidth();
			h = 140*h/w;
			w = 140;
			bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
		}

		if (orientation != 0) {
			try {
                Matrix m = new Matrix();
                m.setRotate(orientation, bitmap.getWidth() * 0.5f, bitmap.getHeight() * 0.5f);
				Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
				if (rotated != bitmap) {
					bitmap.recycle();
				}
				return rotated;
			} catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Throwable t) {
			}

		}

		return bitmap;
	}

	public void setListener(onCompleteListener listener) {
		this.listener = listener;
	}

}