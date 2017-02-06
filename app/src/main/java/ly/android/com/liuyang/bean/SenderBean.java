package ly.android.com.liuyang.bean;

import org.json.JSONObject;

/**
 * Created by 409165 on 2017/2/5.
 */
public class SenderBean {
    private String username;
    private String nick;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public SenderBean parse(JSONObject json) {
        SenderBean sbean = null;
        try {
            SenderBean  bean = new SenderBean();
            if(json.has("avatar")){
                bean.setAvatar(json.optString("avatar"));
            }if(json.has("username")){
                bean.setUsername(json.optString("username"));
            }if(json.has("nick")){
                bean.setNick(json.optString("nick"));
            }
            sbean = bean;
        } catch (Exception e) {

        }
        return  sbean;
    }

}
