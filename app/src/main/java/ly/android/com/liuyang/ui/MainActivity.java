package ly.android.com.liuyang.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ly.android.com.liuyang.R;
import ly.android.com.liuyang.adpter.MyAdapter;
import ly.android.com.liuyang.bean.TweetsBean;
import ly.android.com.liuyang.network.HttpUtils;
import ly.android.com.liuyang.network.NetworkResponseHandler;
import ly.android.com.liuyang.util.Constant;
import ly.android.com.liuyang.widget.XRecyclerView;


public class MainActivity extends Activity {
    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<TweetsBean> list;
    private int refreshTime = 0;
    private TextView nickName;
    private ImageView profile;
    private ImageView avatar;
    private int times = 0;

    private int part = 0;
    List<TweetsBean> partlist = new ArrayList<>();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(part<list.size()-5){
                partlist.addAll(list.subList(part,part+5));
            }

            mAdapter = new MyAdapter(MainActivity.this,partlist);
            mRecyclerView.setAdapter(mAdapter);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (XRecyclerView)this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        View header =   LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
        nickName = (TextView) header.findViewById(R.id.mynickname);
        profile = (ImageView) header.findViewById(R.id.iv_mtopimg);
        avatar = (ImageView) header.findViewById(R.id.siv_img);

        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {

                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if(times < 2){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            mRecyclerView.stopLoadMore();
                            part= part + 5;
                            Message msg = new Message();
                            handler.sendMessage(msg);
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.restoreFooter();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.noMoreLoading();
                        }
                    }, 1000);
                }
                times ++;
            }
        });
        mRecyclerView.clickLoadMore();
        HttpUtils.getDataFromServer(Constant.tweets_url, new NetworkResponseHandler() {
            @Override
            public void onFail(String message) {
            }
            @Override
            public void onSuccess(String data) {
                try {
                    list = new ArrayList<TweetsBean>();
                    JSONArray jsonArray = new JSONArray(data);
                    for (int j=0;j<jsonArray.length();j++){
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        TweetsBean bean = new TweetsBean();
                         bean =  bean.parse(jsonObject);
                         if(bean!=null){
                            list.add(bean);
                        }

                    }
                    System.out.print("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                handler.sendMessage(msg);
//                mAdapter = new MyAdapter(MainActivity.this,list);
//                mRecyclerView.setAdapter(mAdapter);
            }
        });

        HttpUtils.getDataFromServer(Constant.user_url, new NetworkResponseHandler() {
            @Override
            public void onFail(String message) {

            }
            @Override
            public void onSuccess(String data) {
                try {

                    JSONObject jsonObject = new JSONObject(data);

                    nickName.setText(jsonObject.getString("nick"));
                    Glide.with(MainActivity.this)
                            .load(jsonObject.getString("profile-image"))
                            .into(profile);
                    Glide.with(MainActivity.this)
                            .load(jsonObject.getString("avatar"))
                            .into(avatar);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
