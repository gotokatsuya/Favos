package jp.goka.favos.helper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import jp.goka.favos.R;
import jp.goka.favos.model.Count;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by katsuyagoto on 2014/06/28.
 */
public class ImageHelper {

	public static String appPath(Context context){
		return Environment.getExternalStorageDirectory()
				.getPath() + "/" + context.getString(R.string.app_name);
	}

	public static void saveImage(Context context, Bitmap bitmap){
		if (!sdCardWriteReady()){
			return;
		}

		String attachName = "";
		try {
			File file = new File(appPath(context));
			if (!file.exists()) {
				file.mkdir();
			}
			attachName = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
			FileOutputStream out = new FileOutputStream(attachName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
		}


		String[] paths = {attachName};
		String[] mimeTypes = {"image/jpeg"};
		MediaScannerConnection.scanFile(context,
				paths,
				mimeTypes,
				new MediaScannerConnection.MediaScannerConnectionClient() {
					@Override
					public void onMediaScannerConnected() {
					}

					@Override
					public void onScanCompleted(String path, Uri uri) {
					}
				});

		ToastHelper.shortMessage(context, "Save");
	}

	private static boolean sdCardWriteReady() {
		String state = Environment.getExternalStorageState();
		return (Environment.MEDIA_MOUNTED.equals(state));
	}

	//Delete from Gallery
	public static boolean delete(Context context, String path) {
		if(sdCardWriteReady()) {
			Cursor cursor = null;
			try{
				ContentResolver cr = context.getContentResolver();
				cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						new String[] {MediaStore.Images.Media._ID},
						MediaStore.Images.Media.DATA + " = ?",
						new String[]{path},
						null);

				if(cursor.getCount() != 0) {
					cursor.moveToFirst();
					Uri uri = ContentUris.appendId(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon(),
							cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))).build();
					cr.delete(uri, null, null);
					ToastHelper.shortMessage(context, "Delete");
					return true;
				}
			}finally {
				if(cursor != null)
					cursor.close();
			}
		}
		return false;
	}
}
