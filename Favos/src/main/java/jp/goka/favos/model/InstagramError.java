package jp.goka.favos.model;

import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class InstagramError {

	private int code;
	private String error_type;
	private String error_message;

	public String getErrorType() {
		return error_type;
	}

	public void setErrorType(String errorType) {
		this.error_type = errorType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return error_message;
	}

	public void setErrorMessage(String errorMessage) {
		this.error_message = errorMessage;
	}

	public boolean isError(){
		if(this.error_message == null || this.error_message.isEmpty()){
			return false;
		}else {
			return true;
		}
	}

	public static InstagramError parse(JSONObject jsonObject){
		InstagramError instagramError = new Gson().fromJson(jsonObject.toString(), InstagramError.class);
		return instagramError;
	}
}
