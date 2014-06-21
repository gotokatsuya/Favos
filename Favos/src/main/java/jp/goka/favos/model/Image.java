package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Images")
public class Image extends Base {

	private LowResolution low_resolution;
	private Thumbnail thumbnail;
	private StandardResolution standard_resolution;

	public LowResolution getLowResolution() {
		return low_resolution;
	}

	public void setLowResolution(LowResolution lowResolution) {
		this.low_resolution = lowResolution;
	}

	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

	public StandardResolution getStandardResolution() {
		return standard_resolution;
	}

	public void setStandardResolution(StandardResolution standardResolution) {
		this.standard_resolution = standardResolution;
	}

	public static Image parse(JSONObject jsonObject){
		Image image = new Gson().fromJson(jsonObject.toString(), Image.class);
		return image;
	}
}
