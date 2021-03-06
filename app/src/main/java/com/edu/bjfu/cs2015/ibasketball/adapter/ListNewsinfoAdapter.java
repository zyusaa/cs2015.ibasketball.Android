package com.edu.bjfu.cs2015.ibasketball.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.bjfu.cs2015.ibasketball.R;
import com.edu.bjfu.cs2015.ibasketball.tool.LoadImagesTask;

import java.util.List;

import JSONPO.Newsinfo;

/**
 * Created by ChrisYoung on 2017/12/26.
 */

public class ListNewsinfoAdapter extends RecyclerView.Adapter<ListNewsinfoAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<Newsinfo> mNewsinfoList;

    public ListNewsinfoAdapter(List<Newsinfo> list, Context context){
        this.mNewsinfoList = list;
        this.mContext = context;
    }

    // Innerclass ViewHolder 用来操控UI控件
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mNewsPic;
        private TextView mNewsTitle;
        private TextView mNewsShortContent;

        public ViewHolder(View itemView){
            super(itemView);
            mNewsPic = (ImageView) itemView.findViewById(R.id.iv_news_pic);
            mNewsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            mNewsShortContent = (TextView) itemView.findViewById(R.id.tv_news_shortContent);
        }
    }

    //定义点击item接口
    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, Newsinfo bean);
    }

    //添加接口
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    //设置Adapter接口的方法
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnRecyclerViewItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(mOnRecyclerViewItemClickListener != null){
            mOnRecyclerViewItemClickListener.onItemClick(v, (Newsinfo) v.getTag());
        }
        Log.e("Adapter",v.getId()+"");  // 被点击的控件的id
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        ViewHolder holder = new ViewHolder(view);

        //为创建的每个view注册点击事件
        view.setOnClickListener(this);
        return holder;
    }


    // 为每一个view
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // TODO  holder的各种view要通过JDBC操作来set modify by molinli
        holder.mNewsShortContent.setText(mNewsinfoList.get(position).getNewsContent().substring(0,100));
        holder.mNewsTitle.setText(mNewsinfoList.get(position).getNewsTitle());
        //图片
        new LoadImagesTask(holder.mNewsPic).execute(mNewsinfoList.get(position).getNewsPic());
        holder.itemView.setTag(mNewsinfoList.get(position));  // 必不可少!


    }

    @Override
    public int getItemCount() {
        return mNewsinfoList.size();
    }

}
