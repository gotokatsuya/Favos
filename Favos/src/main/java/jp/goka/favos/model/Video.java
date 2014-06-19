package jp.goka.favos.model;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Video extends Base {

	private LowBandWidth lowBandWidth;
	private LowResolution lowResolution;
	private StandardResolution standardResolution;

	public LowBandWidth getLowBandWidth() {
		return lowBandWidth;
	}

	public void setLowBandWidth(LowBandWidth lowBandWidth) {
		this.lowBandWidth = lowBandWidth;
	}

	public LowResolution getLowResolution() {
		return lowResolution;
	}

	public void setLowResolution(LowResolution lowResolution) {
		this.lowResolution = lowResolution;
	}

	public StandardResolution getStandardResolution() {
		return standardResolution;
	}

	public void setStandardResolution(StandardResolution standardResolution) {
		this.standardResolution = standardResolution;
	}
}
