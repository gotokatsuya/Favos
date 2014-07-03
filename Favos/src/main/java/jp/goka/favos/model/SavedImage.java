package jp.goka.favos.model;

import android.database.Cursor;
import jp.goka.favos.util.MediaUtils;

import java.io.Serializable;

/**
 * Created by katsuyagoto on 2014/03/24.
 */
public class SavedImage implements Serializable {

	private long mediaId;
	private String path;
	private long dateToken;
	private int orientation;

	private boolean isSelected = false;

	public SavedImage() {
		super();
	}

	public SavedImage(Cursor cursor) {
		super();
		this.mediaId = cursor.getLong(MediaUtils.COL_ID);
		this.path = cursor.getString(MediaUtils.COL_DATA);
		this.dateToken = cursor.getLong(MediaUtils.COL_DATE_TAKEN);
		this.orientation = cursor.getInt(MediaUtils.COL_ORIENTATION);
	}

	public long getMediaId() {
		return mediaId;
	}

	public void setMediaId(long id) {
		this.mediaId = id;
	}

	public String getPath() {
		if(path == null){
			return "";
		}
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public long getDateToken() {
		return dateToken;
	}

	public void setDateToken(long dateToken) {
		this.dateToken = dateToken;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
}
