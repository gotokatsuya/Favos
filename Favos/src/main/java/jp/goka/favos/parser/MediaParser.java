package jp.goka.favos.parser;

import jp.goka.favos.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class MediaParser {

	private enum ParamKeys {
		KEY_id("id"),
		KEY_created_time("created_time"),
		KEY_attribution("attribution"),
		KEY_tags("tags"),
		KEY_type("type"),
		KEY_location("location"),
		KEY_comments("comments"),
		KEY_filter("filter"),
		KEY_link("link"),
		KEY_likes("likes"),
		KEY_images("images"),
		KEY_videos("videos"),
		KEY_users_in_photo("users_in_photo"),
		KEY_caption("caption"),
		KEY_user_has_liked("user_has_liked"),
		KEY_user("user");

		private String mKey;

		private ParamKeys(String key) {
			mKey = key;
		}

		public String getKey() {
			return mKey;
		}
	}


	public static Media parse(JSONObject obj) {
		Media media = new Media();
		for (ParamKeys key : ParamKeys.values()) {
			try {
				setParam(media, obj, key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return media;
	}



	private static void setParam(Media media, JSONObject obj, ParamKeys key) throws JSONException {
		switch (key) {
			case KEY_id:
				media.setIdentity(obj.getString(key.getKey()));
				break;
			case KEY_created_time:
				media.setCreatedTime(obj.getLong(key.getKey()));
				break;
			case KEY_attribution:
				media.setAttribution(obj.getString(key.getKey()));
				break;
			case KEY_tags:
				media.setTags(convertStrings(obj.getJSONArray(key.getKey())));
				break;
			case KEY_type:
				media.setType(obj.getString(key.getKey()));
				break;
			case KEY_location:
				media.setLocation(Location.parse(obj.getJSONObject(key.getKey())));
				break;
			case KEY_comments:
				media.setComments(Comments.parse(obj.getJSONObject(key.getKey())));
				break;
			case KEY_filter:
				media.setFilter(obj.getString(key.getKey()));
				break;
			case KEY_link:
				media.setLink(obj.getString(key.getKey()));
				break;
			case KEY_likes:
				media.setLikes(Likes.parse(obj.getJSONObject(key.getKey())));
				break;
			case KEY_images:
				media.setImages(Image.parse(obj.getJSONObject(key.getKey())));
				break;
			case KEY_videos:
				media.setVideos(Video.parse(obj.getJSONObject(key.getKey())));
				break;
			case KEY_users_in_photo:
				media.setUsersInPhoto(UsersInPhoto.parse(obj.getJSONArray(key.getKey())));
				break;
			case KEY_caption:
				media.setCaption(Caption.parse(obj.getJSONObject(key.getKey())));
				break;
			case KEY_user_has_liked:
				media.setUserHasLiked(obj.getBoolean(key.getKey()));
				break;
			case KEY_user:
				media.setUser(User.parse(obj.getJSONObject(key.getKey())));
				break;
		}
	}

	private static List<String> convertStrings(JSONArray jsonArray){
		List<String> strings = new ArrayList<String>();
		for(int i=0;i<jsonArray.length();i++){
			try {
				strings.add(jsonArray.getString(i));
			}catch (JSONException e){
				e.printStackTrace();
			}
		}
		return strings;
	}

}
