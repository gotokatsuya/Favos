package jp.goka.favos.model;

import java.util.List;
import java.util.Locale;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Media extends Base {
	enum Type{
		IMAGE, VIDEO
	}

	private String attribution;
	private List<String> tags;
	private Type type;
	private Location location;
	private Comments comments;
	private String filter;
	private String link;
	private Likes likes;
	private List<Image> images;
	private List<Video> videos;
	private List<UsersInPhoto> usersInPhoto;
	private Caption caption;
	private boolean userHasLiked;
	private User user;


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
		return type;
	}

	public void setType(Type type) {
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<UsersInPhoto> getUsersInPhoto() {
		return usersInPhoto;
	}

	public void setUsersInPhoto(List<UsersInPhoto> usersInPhoto) {
		this.usersInPhoto = usersInPhoto;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	public boolean isUserHasLiked() {
		return userHasLiked;
	}

	public void setUserHasLiked(boolean userHasLiked) {
		this.userHasLiked = userHasLiked;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}