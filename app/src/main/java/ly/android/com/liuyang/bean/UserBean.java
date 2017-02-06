package ly.android.com.liuyang.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 409165 on 2017/2/5.
 */
public class UserBean {
    private String profileImg;
    private String avatar;
    private String nick;
    private String username;

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void parse(JSONObject jsonObject){

        try {
            this.profileImg = jsonObject.getString("profile-image");
            this.avatar = jsonObject.getString("avatar");
            this.nick = jsonObject.getString("nick");
            this.username = jsonObject.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
