package ly.android.com.liuyang.adpter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ly.android.com.liuyang.R;

public class MyGridAdapter extends BaseAdapter {
	private List<String> imageUrl;
    private Context context;
	private LayoutInflater mLayoutInflater;

	public MyGridAdapter( Context context,List<String> imageUrl) {
		mLayoutInflater = LayoutInflater.from(context);
		this.context = context;
	    this.imageUrl = imageUrl;
	}

	@Override
	public int getCount() {
		return imageUrl == null ? 0 : imageUrl.size();
	}

	@Override
	public String getItem(int position) {
		return imageUrl.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyGridViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MyGridViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.user_img_item,
					parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.iv_user_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MyGridViewHolder) convertView.getTag();
		}

		Glide.with(context)
				.load(imageUrl.get(position))
				.into(viewHolder.imageView);
		return convertView;
	}

	private static class MyGridViewHolder {
		ImageView imageView;
	}
}
