package jp.goka.favos.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import jp.goka.favos.helper.ImageHelper;
import jp.goka.favos.model.SavedImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/05/10.
 */
public class MediaUtils {
	public static final int IMAGE = 0;
	public static final int VIDEO = 1;
	public static final int FOLDER = 2;

	public static final int COL_ID				= 0;
	public static final int COL_DATA 			= 1;
	public static final int COL_DISPLAY_NAME   	= 2;
	public static final int COL_DATE_TAKEN   	= 3;
	public static final int COL_LATITUDE   		= 4;
	public static final int COL_LONGITUDE   	= 5;
	public static final int COL_TITLE		   	= 6;
	public static final int COL_MIME_TYPE		= 7;
	public static final int COL_ORIENTATION   	= 8;
	public static final int COL_DURATION   		= 8;
	public static final int COL_RESOLUTION   	= 9;


	public static final String[] BUCKET_IMAGE_COLUMNS = {
			MediaStore.Images.ImageColumns.BUCKET_ID,
			MediaStore.Images.ImageColumns.DATA,
			MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME

	};

	public static final String[] BUCKET_VIDEO_COLUMNS = {
			MediaStore.Video.VideoColumns.BUCKET_ID,
			MediaStore.Video.VideoColumns.DATA,
			MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME
	};

	public static final String[] IMAGE_COLUMNS = {
			MediaStore.Images.ImageColumns._ID,
			MediaStore.Images.ImageColumns.DATA,
			MediaStore.Images.ImageColumns.DISPLAY_NAME,
			MediaStore.Images.ImageColumns.DATE_TAKEN,
			MediaStore.Images.ImageColumns.LATITUDE,
			MediaStore.Images.ImageColumns.LONGITUDE,
			MediaStore.Images.ImageColumns.TITLE,
			MediaStore.Images.ImageColumns.MIME_TYPE,
			MediaStore.Images.ImageColumns.ORIENTATION
	};

	public static final String[] VIDEO_COLUMNS = {
			MediaStore.Video.VideoColumns._ID,
			MediaStore.Video.VideoColumns.DATA,
			MediaStore.Video.VideoColumns.DISPLAY_NAME,
			MediaStore.Video.VideoColumns.DATE_TAKEN,
			MediaStore.Video.VideoColumns.LATITUDE,
			MediaStore.Video.VideoColumns.LONGITUDE,
			MediaStore.Video.VideoColumns.TITLE,
			MediaStore.Video.VideoColumns.MIME_TYPE,
			MediaStore.Video.VideoColumns.DURATION,
			MediaStore.Video.VideoColumns.RESOLUTION

	};

	public static String[] getBucketColumns(int mediaType) {
		return (mediaType == IMAGE)
				? BUCKET_IMAGE_COLUMNS
				: BUCKET_VIDEO_COLUMNS;
	}

	public static Uri getContentUri(int mediaType) {
		return (mediaType == IMAGE)
				? MediaStore.Images.Media.EXTERNAL_CONTENT_URI
				: MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	}

	public static String getBucketId(int mediaType) {
		return (mediaType == IMAGE)
				? MediaStore.Images.ImageColumns.BUCKET_ID
				: MediaStore.Video.VideoColumns.BUCKET_ID;
	}

	public static String getBucketDisplayName(int mediaType) {
		return (mediaType == IMAGE)
				? MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
				: MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME;
	}


	public static String getBucketData(int mediaType) {
		return (mediaType == IMAGE)
				? MediaStore.Images.ImageColumns.DATA
				: MediaStore.Video.VideoColumns.DATA;
	}

	public static int getItemCount(long bucketId, ContentResolver cr) {
		int count = 0;
		Cursor cursor = getFolderImageCursor(bucketId, cr);
		if (cursor != null) {
			count += cursor.getCount();
			cursor.close();

			/*if (isIncludeVideo) {
			//TODO video cursor( not have video function on couples..)
				cursor = getFolderVideoCursor(bucketId, cr);
				if (cursor != null) {
					count += cursor.getCount();
				}
				cursor.close();
			}*/
		}

		return count;
	}


	public static Cursor getFolderImageCursor(long bucketId, final ContentResolver cr) {
		String where = MediaStore.Images.ImageColumns.BUCKET_ID + "=?";
		String whereValue[] = {String.valueOf(bucketId)};
		return  cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaUtils.IMAGE_COLUMNS, where, whereValue, null);
	}


	public static Cursor specificFolderCursor(ContentResolver contentResolver, String folderPath){
		String folder = folderPath;
		folder = folder + "%";
		String where = MediaStore.Images.Media.DATA + " LIKE ?";
		String[] whereValue = new String[]{folder};

		return contentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				MediaUtils.IMAGE_COLUMNS,
				where,
				whereValue,
				null);
	}


	public static boolean existSavedImage(Context context) {
		ContentResolver cr = context.getContentResolver();
		final Cursor specificFolderCursor = specificFolderCursor(cr, ImageHelper.appPath(context));
		if (specificFolderCursor == null){
			return false;
		}
		return specificFolderCursor.moveToLast();
	}


	public static List<SavedImage> getSavedImage(Context context) {
		ContentResolver cr = context.getContentResolver();
		final List<SavedImage> savedImages = new ArrayList<SavedImage>();
		final Cursor specificFolderCursor = specificFolderCursor(cr, ImageHelper.appPath(context));

		if (specificFolderCursor == null){
			return savedImages;
		}

		if(specificFolderCursor.moveToLast()) {
			SavedImage lastSavedImage = new SavedImage(specificFolderCursor);
			savedImages.add(lastSavedImage);
		}else {
			return savedImages;
		}

		while (specificFolderCursor.moveToPrevious()) {
			SavedImage savedImage = new SavedImage(specificFolderCursor);
			savedImages.add(savedImage);
		}

		specificFolderCursor.close();
		return savedImages;
	}

}

