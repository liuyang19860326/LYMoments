package ly.android.com.liuyang.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ly.android.com.liuyang.R;
import ly.android.com.liuyang.Interface.OnItemClick;
import ly.android.com.liuyang.bean.CommentsBean;
import ly.android.com.liuyang.bean.TweetsBean;
import ly.android.com.liuyang.widget.CommentListTextView;
import ly.android.com.liuyang.widget.NoScrollGridView;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

//
//    private ILoadCallback mCallback;
    private List<TweetsBean> list = null;
    private OnItemClick onItemClick;
    private Context context;
    public CommentListTextView commenttv;
    public NoScrollGridView Gridview;
    private TextView contentsTV;


    public MyAdapter(Context context, List<TweetsBean> list) {
        this.list = list;
        this.context = context;

    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (onItemClick != null){
            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(this);
        }
        if(list.get(position).getSender().getNick()!=null){
            viewHolder.mTextView.setText(list.get(position).getSender().getNick());
            }
        if(list.get(position).getSender().getAvatar()!=null){
            Glide.with(context)
                    .load(list.get(position).getSender().getAvatar())
                    .into(viewHolder.image);
        }

        if(list.get(position).getContent()!=null){
            contentsTV.setText(list.get(position).getContent());
          }else{
            contentsTV.setVisibility(View.GONE);
          }
         if(list.get(position).getImages() != null&&list.get(position).getImages().size()>0){
             Gridview.setVisibility(View.VISIBLE);
             MyGridAdapter myGridAdapter = new MyGridAdapter(context,list.get(position).getImages());
             Gridview.setAdapter(myGridAdapter);
          }
        if(list.get(position).getComments() != null){
            SetComments(list.get(position).getComments());
        }

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        onItemClick.itemClick(v, position);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView ContentTv;
        public ImageView image;
        public NoScrollGridView gridview;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.nickname);
            image = (ImageView)view.findViewById(R.id.avatar);
            commenttv = (CommentListTextView)view.findViewById(R.id.commentlist);

            ContentTv =  (TextView) view.findViewById(R.id.content);
            gridview = (NoScrollGridView)view.findViewById(R.id.gridView);

            contentsTV = ContentTv;
            Gridview = gridview;
        }
    }



    private void SetComments(List<CommentsBean> comments){

        commenttv.setNameColor (context.getResources().getColor(R.color.nameColor));
        commenttv.setCommentColor (context.getResources().getColor(R.color.commentsColor));
        commenttv.setTalkStr (context.getResources().getString(R.string.reply));
        commenttv.setTalkColor (context.getResources().getColor(R.color.commentsColor));

        List<CommentListTextView.CommentInfo> mCommentInfos = new ArrayList<> ();
        for (int i = 0; i < comments.size(); i++) {
            mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (i+1).setComment (comments.get(i).getContent()).setNickname (comments.get(i).getSender().getNick()).setTonickname ("赵四"));
          }
        commenttv.setData (mCommentInfos);
        commenttv.setonCommentListener (new CommentListTextView.onCommentListener () {


            @Override
            public void onNickNameClick (final int position, final CommentListTextView.CommentInfo mInfo) {
//                logTv.append ("onNickNameClick  position = [" + position + "], mInfo = [" + mInfo + "]" + "\r\n");
            }

            @Override
            public void onToNickNameClick (final int position, final CommentListTextView.CommentInfo mInfo) {
//                logTv.append ("onToNickNameClick  position = [" + position + "], mInfo = [" + mInfo + "]" + "\r\n");
            }

            @Override
            public void onCommentItemClick (final int position, final CommentListTextView.CommentInfo mInfo) {
//                logTv.append ("onCommentItemClick  position = [" + position + "], mInfo = [" + mInfo + "]" + "\r\n");
            }

            @Override
            public void onOtherClick () {
//                logTv.append ("onOtherClick" + "\r\n");
            }
        });
    }

//
//    //对外暴露设置接口方法
//    public void setLoadCallback(ILoadCallback callback) {
//        this.mCallback = callback;
//    }
//
//    public void addData(List<TweetsBean> newlist) {
//        this.list.addAll(newlist);
//        notifyDataSetChanged();//添加数据后通知 adpter 更新
//    }
//
//    //回调接口，用于回调加载数据的方法
//    interface ILoadCallback {
//        void onLoad();
//    }

}
