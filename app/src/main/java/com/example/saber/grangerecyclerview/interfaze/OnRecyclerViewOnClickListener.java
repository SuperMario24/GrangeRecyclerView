package com.example.saber.grangerecyclerview.interfaze;

import android.view.View;

/**
 * Created by saber on 2017/7/14.
 */

public interface OnRecyclerViewOnClickListener {
    //左边的导航RecyclerView点击事件
    void onItemClick(View view,int position);
    //右边详细RecyclerView点击事件
    void onItemClick(int id,int position);

}
