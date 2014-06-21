package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Videos")
public class Video extends Base {

	private LowBandWidth low_bandWidth;
	private LowResolution low_resolution;
	private StandardResolution standard_resolution;

	public LowBandWidth getLowBandWidth() {
		return low_bandWidth;
	}

	public void setLowBandWidth(LowBandWidth lowBandWidth) {
		this.low_bandWidth = lowBandWidth;
	}

	public LowResolution getLowResolution() {
		return low_resolution;
	}

	public void setLowResolution(LowResolution lowResolution) {
		this.low_resolution = lowResolution;
	}

	public StandardResolution getStandardResolution() {
		return standard_resolution;
	}

	public void setStandardResolution(StandardResolution standardResolution) {
		this.standard_resolution = standardResolution;
	}

	public static Video parse(JSONObject jsonObject){
		Video video = new Gson().fromJson(jsonObject.toString(), Video.class);
		return video;
	}
}
