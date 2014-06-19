package jp.goka.favos.model;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Image extends Base {

	private LowResolution lowResolution;
	private Thumbnail thumbnail;
	private StandardResolution standardResolution;

	public LowResolution getLowResolution() {
		return lowResolution;
	}

	public void setLowResolution(LowResolution lowResolution) {
		this.lowResolution = lowResolution;
	}

	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

	public StandardResolution getStandardResolution() {
		return standardResolution;
	}

	public void setStandardResolution(StandardResolution standardResolution) {
		this.standardResolution = standardResolution;
	}
}
