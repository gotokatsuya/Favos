package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "UsersInPhotos")
public class UsersInPhoto extends Base {
	private Position position;
	private User user;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static UsersInPhoto parse(JSONObject jsonObject){
		UsersInPhoto usersInPhoto = new Gson().fromJson(jsonObject.toString(), UsersInPhoto.class);
		return usersInPhoto;
	}

	public static List<UsersInPhoto> parse(JSONArray jsonArray){
		List<UsersInPhoto> usersInPhotos = new ArrayList<UsersInPhoto>();
		for(int i=0;i<jsonArray.length();i++){
			try {
				usersInPhotos.add(parse(jsonArray.getJSONObject(i)));
			}catch (JSONException e){
				e.printStackTrace();
			}
		}
		return usersInPhotos;
	}
}
