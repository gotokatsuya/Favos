package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import jp.goka.favos.parser.MediaParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

public class Media extends Base {

	public enum Type{
		IMAGE, VIDEO
	}

	private String attribution;
	private List<String> tags;
	private String type;
	private Location location;
	private Comments comments;
	private String filter;
	private String link;
	private Likes likes;
	private Image images;
	private Video videos;
	private List<UsersInPhoto> users_in_photo;
	private Caption caption;
	private boolean user_has_liked;
	private User user;


	public static Media parse(JSONObject jsonObject){
		Media media = MediaParser.parse(jsonObject);
		return media;
	}


	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Type getType() {
		if(type.equals("image")){
			return Type.IMAGE;
		}else if(type.equals("video")){
			return Type.VIDEO;
		}
		return Type.IMAGE;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Likes getLikes() {
		return likes;
	}

	public void setLikes(Likes likes) {
		this.likes = likes;
	}

	public Image getImages() {
		return images;
	}

	public void setImages(Image images) {
		this.images = images;
	}

	public Video getVideos() {
		return videos;
	}

	public void setVideos(Video videos) {
		this.videos = videos;
	}

	public List<UsersInPhoto> getUsersInPhoto() {
		return users_in_photo;
	}

	public void setUsersInPhoto(List<UsersInPhoto> usersInPhoto) {
		this.users_in_photo = usersInPhoto;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	public boolean isUserHasLiked() {
		return user_has_liked;
	}

	public void setUserHasLiked(boolean userHasLiked) {
		this.user_has_liked = userHasLiked;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}