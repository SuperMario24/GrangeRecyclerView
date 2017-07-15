package com.example.saber.grangerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saber.grangerecyclerview.R;
import com.example.saber.grangerecyclerview.interfaze.OnRecyclerViewOnClickListener;
import com.example.saber.grangerecyclerview.javaBean.DetailsBean;

import java.util.List;

/**
 * Created by saber on 2017/7/14.
 */

public class RecyclerViewRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<DetailsBean> detailsBeans;
    private LayoutInflater inflater;
    private OnRecyclerViewOnClickListener mListener;

    private static final int ITEM_TITLE = 0;
    private static final int ITEM_DETAILS = 1;


    public RecyclerViewRightAdapter(Context mContext, List<DetailsBean> detailsBeans,OnRecyclerViewOnClickListener mListener) {
        this.mContext = mContext;
        this.detailsBeans = detailsBeans;
        this.mListener = mListener;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return detailsBeans.get(position).isTitle()? ITEM_TITLE : ITEM_DETAILS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case ITEM_TITLE:
                return new ViewHolder(inflater.inflate(R.layout.item_title, parent, false),ITEM_TITLE);
            case ITEM_DETAILS:
                return new ViewHolder(inflater.inflate(R.layout.rv_item_details, parent, false),ITEM_DETAILS);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        DetailsBean detailsBean = detailsBeans.get(position);

        switch (itemViewType) {
            case 0:
                ((ViewHolder)holder).tvTitle.setText("测试数据" + detailsBean.getTag());
                break;
            case 1:
                ((ViewHolder)holder).tvCity.setText(detailsBean.getName());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return detailsBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvCity;
        private ImageView ivAvatar;
        private TextView tvTitle;

        public ViewHolder(View itemView,int type) {
            super(itemView);
            switch (type){
                case ITEM_TITLE:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case ITEM_DETAILS:
                    tvCity = (TextView) itemView.findViewById(R.id.tvCity);
                    ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
                    break;
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v.getId(),getLayoutPosition());
        }
    }












}
