package com.example.saber.grangerecyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saber.grangerecyclerview.R;
import com.example.saber.grangerecyclerview.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by saber on 2017/7/14.
 */

public class RecyclerViewLeftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> leftTitles;
    private OnRecyclerViewOnClickListener mListener;


    private int checkPosition = 0;

    public RecyclerViewLeftAdapter(Context context,List<String> leftTitles,OnRecyclerViewOnClickListener mListener){
        mContext = context;
        this.leftTitles = leftTitles;
        inflater = LayoutInflater.from(mContext);
        this.mListener = mListener;
    }

    //设置选中的项
    public void setCheckPosition(int position){
        this.checkPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_item_left,parent,false);

        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String title = leftTitles.get(position);
        ((ViewHolder)holder).tvName.setText(title);

        if(checkPosition == position){
            ((ViewHolder)holder).itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            ((ViewHolder)holder).tvName.setTextColor(Color.parseColor("#3F51B5"));
        }else {
            ((ViewHolder)holder).itemView.setBackgroundColor(Color.parseColor("#dddddd"));
            ((ViewHolder)holder).tvName.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return leftTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName;
        private View itemView;

        public ViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.itemView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_left_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getLayoutPosition());
            }
        }
    }



}
