package jp.goka.favos.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import jp.goka.favos.Config;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Selfs")
public class Self extends Base {

	@Column(name = "AccessToken")
	private String access_token;

	private User user;

	public String getAccessToken() {
		return access_token;
	}

	public void setAccessToken(String accessToken) {
		this.access_token = accessToken;
	}

	public boolean hasAccessToken(){
		if(access_token == null || access_token.isEmpty()){
			return false;

		}else {
			return true;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static boolean sameAccessToken(Self s1, Self s2){
		if(!((s1.getAccessToken()).equals((s2.getAccessToken())))){
			return false;
		}else {
			return true;
		}
	}

	public static Self parse(JSONObject jsonObject){
		Self self = new Gson().fromJson(jsonObject.toString(), Self.class);
		self.setCreatedTime((new Date().getTime()));
		Self findSelf = find();
		if(findSelf == null){
			self.save();
		}else {
			if(!sameAccessToken(findSelf, self)){
				self.save();
			}
		}
		return self;
	}

	public static Self find(){
		Self self = new Select()
				.from(Self.class)
				.orderBy("CreatedTime DESC")
				.executeSingle();
		return self;
	}
}
