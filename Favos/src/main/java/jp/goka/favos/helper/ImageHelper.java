package jp.goka.favos.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
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
}
