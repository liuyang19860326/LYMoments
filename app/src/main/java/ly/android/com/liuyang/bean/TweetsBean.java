package ly.android.com.liuyang.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 409165 on 2017/2/5.
 */
public class TweetsBean {
    private String content;
    private List<String> images;
    private SenderBean sender;

    private List<CommentsBean> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

//    public void parse(JSONObject json){
//
//        try {
//            if(json.has("content")){
//                this.content = json.getString("content");
//            }
//              if(json.has("images")){
//                  this.images = new ArrayList<>();
//
//                  JSONArray arr = json.optJSONArray("images");
//                  if(arr!=null){
//                      for(int i =0;i<arr.length();i++){
//                          this.images.add(arr.optJSONObject(i).optString("url"));
//                      }
//                  }
//
//              }
//            if(json.has("sender")){
//                this.sender = new SenderBean();
//                sender.parse(json.optJSONObject("sender"));
//            }
//        if(json.has("comments")){
//            JSONArray arrComment = json.optJSONArray("comments");
//            if(arrComment !=null){
//                this.comments = new ArrayList<>();
//                for(int i =0;i<arrComment.length();i++){
//                    CommentsBean bean = new CommentsBean();
//                    bean.parse(arrComment.optJSONObject(i));
//                    this.comments.add(bean);
//                }
//            }
//        }
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    public TweetsBean parse(JSONObject json) {
        TweetsBean bean = null;
        try {
            TweetsBean tweetsBean = new TweetsBean();
            if (json.has("content")) {
                tweetsBean.setContent(json.getString("content"));
              }
            if (json.has("images")) {
                List<String> imagelist = new ArrayList<>();

                JSONArray arr = json.optJSONArray("images");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        imagelist.add(arr.optJSONObject(i).optString("url"));
                    }
                }
                tweetsBean.setImages(imagelist);
            }

            if (json.has("comments")) {
                JSONArray arrComment = json.optJSONArray("comments");
                if (arrComment != null) {
                    List<CommentsBean> comment = new ArrayList<>();
                    for (int i = 0; i < arrComment.length(); i++) {
                        CommentsBean cbean = new CommentsBean();
                        cbean =  cbean.parse(arrComment.optJSONObject(i));
                        comment.add(cbean);
                    }
                    tweetsBean.setComments(comment);
                }
            }
            if (json.has("sender")) {
                SenderBean senderbean = new SenderBean();
                senderbean = senderbean.parse(json.optJSONObject("sender"));
                tweetsBean.setSender(senderbean);
                bean = tweetsBean;
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }
             return  bean;
      }
}
