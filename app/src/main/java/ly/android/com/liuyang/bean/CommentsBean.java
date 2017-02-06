package ly.android.com.liuyang.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 409165 on 2017/2/5.
 */
public class CommentsBean {
    private String content;
    private SenderBean sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public CommentsBean parse(JSONObject json){
        CommentsBean commentsBean = null;
        try {
            CommentsBean Bean = new CommentsBean();
            if(json.has("content")){
                Bean.setContent(json.getString("content"));
            }

//            this.sender = new SenderBean();
//            sender.parse(json.optJSONObject("sender"));
           if(json.has("sender")) {
               SenderBean senderbean = new SenderBean();
               senderbean = senderbean.parse(json.optJSONObject("sender"));
               Bean.setSender(senderbean);
               commentsBean = Bean;
           }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  commentsBean;
    }

}
