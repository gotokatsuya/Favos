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

	@Column(name = "User")
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

	public static Self parse(JSONObject jsonObject){
		Self self = new Gson().fromJson(jsonObject.toString(), Self.class);
		User user = self.getUser();
		if(user != null) {
			Count count = user.getCounts();
			if(count != null){
				count.save();
				user.setCounts(count);
			}

			user.save();
			self.setUser(user);
		}


		self.setCreatedTime((new Date().getTime()));

		Self find = Self.find();
		if(find == null){
			self.save();
		}else {
			find = self;
			find.save();
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
