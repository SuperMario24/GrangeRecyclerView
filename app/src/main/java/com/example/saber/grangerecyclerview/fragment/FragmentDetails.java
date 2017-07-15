package com.example.saber.grangerecyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saber.grangerecyclerview.R;
import com.example.saber.grangerecyclerview.adapter.RecyclerViewRightAdapter;
import com.example.saber.grangerecyclerview.customView.ItemHeaderDecoration;
import com.example.saber.grangerecyclerview.interfaze.ChangeLeftListener;
import com.example.saber.grangerecyclerview.interfaze.CheckListener;
import com.example.saber.grangerecyclerview.interfaze.OnRecyclerViewOnClickListener;
import com.example.saber.grangerecyclerview.javaBean.DetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2017/7/14.
 */

public class FragmentDetails extends Fragment implements CheckListener,ChangeLeftListener{

    private static final String TAG = "FragmentDetails";

    private View mView;
    private Context mContext;

    private RecyclerView recyclerViewRight;
    private RecyclerViewRightAdapter recyclerViewRightAdapter;

    private GridLayoutManager mGridLayoutManager;
    private List<DetailsBean> mDatas = new ArrayList<>();
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;
    private ChangeLeftListener changeLeftListener;

    private boolean isLeftSet = false;//左表联动右表，右表不回调

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_details,null);

        initViews();

        initData();

        return mView;
    }

    private void initData() {

        mGridLayoutManager = new GridLayoutManager(mContext,3);
        //设置一个item的跨度，如果是title跨度为3，如果是内容，跨度为1
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle()? 3:1;
            }
        });
        recyclerViewRight.setLayoutManager(mGridLayoutManager);
        recyclerViewRightAdapter = new RecyclerViewRightAdapter(mContext, mDatas, new OnRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ingore
            }

            @Override
            public void onItemClick(int id, int position) {
                String content = "";
                switch (id){
                    //点击了标题
                    case R.id.root:
                        content = "title";
                        break;
                    //点击了内容
                    case R.id.content:
                        content = "content";
                        break;
                }

                  Snackbar.make(recyclerViewRight,"当前点击的是" + content + ":" + mDatas.get(position).getName(),Snackbar.LENGTH_SHORT).show();
//                Snackbar snackbar = Snackbar.make(recyclerViewRight, "当前点击的是" + content + ":" + mDatas.get(position).getName(), Snackbar.LENGTH_SHORT);
//                View mView = snackbar.getView();
//                mView.setBackgroundColor(Color.BLUE);
//                TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
//                text.setTextColor(Color.WHITE);
//                text.setTextSize(25);
//                snackbar.show();
            }
        });

        recyclerViewRight.setAdapter(recyclerViewRightAdapter);
        mDecoration = new ItemHeaderDecoration(mContext, mDatas);
        recyclerViewRight.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);

        recyclerViewRight.addOnScrollListener(new ReceiverViewListener());


        //左侧数据的个数
        String[] data = getResources().getStringArray(R.array.pill);

        for(int i=0;i<data.length;i++){
            //设置头部
            DetailsBean titleBean = new DetailsBean(String.valueOf(i));
            titleBean.setTitle(true);//头部设置为true
            titleBean.setTag(String.valueOf(i));
            mDatas.add(titleBean);

            //设置每个头部下面的item
            for(int j=0;j<10;j++){
                DetailsBean detailsBean = new DetailsBean(String.valueOf(i + "行" + j + "个"));
                detailsBean.setTag(String.valueOf(i));
                mDatas.add(detailsBean);

            }
        }
        recyclerViewRightAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);

    }

    private void initViews() {

        mContext = getActivity();
        recyclerViewRight = (RecyclerView) mView.findViewById(R.id.rv_right);

    }

    public void setListener(CheckListener listener, ChangeLeftListener changeLeftListener) {
        this.checkListener = listener;
        this.changeLeftListener = changeLeftListener;
    }

    public void setData(int position){
        isLeftSet = true;
        if (position < 0 || position >= recyclerViewRightAdapter.getItemCount()) {
            Toast.makeText(mContext, "超出范围了", Toast.LENGTH_SHORT).show();
            return;
        }
        mIndex = position;
        //从上往下滚动,如果是从上往下滚动的时候，发现每次达不到预期的效果，只能将需要滚动的item的显示出来而已，并不能滚动到顶部
        //从下滚动,这种情况是OK的，每次能够达到想要的效果
        //recyclerViewRight.scrollToPosition(mIndex);

        recyclerViewRight.stopScroll();
        smoothMoveToPosition(position);
        

    }
    //recyclerView滚动到顶部
    private void smoothMoveToPosition(int position) {
        int firstItem = mGridLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mGridLayoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            recyclerViewRight.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int top = recyclerViewRight.getChildAt(position - firstItem).getTop();
            recyclerViewRight.smoothScrollBy(0, top);
        } else {
            recyclerViewRight.smoothScrollToPosition(position);
            move = true;
        }
    }

    /**
     * recyclerView滑动监听
     */
    private class ReceiverViewListener extends RecyclerView.OnScrollListener{
        public ReceiverViewListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //如果move是true并且是静止状态
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                if(move){//第一次静止，没滑动完
                    move = false;
                    int firstItem = mGridLayoutManager.findFirstVisibleItemPosition();
                    int currentIndex = mIndex - firstItem;
                    if(currentIndex >= 0 && currentIndex < recyclerViewRight.getChildCount()){
                        int top = recyclerViewRight.getChildAt(currentIndex).getTop();
                        recyclerView.smoothScrollBy(0,top);
                    }
                }else {//已经滑动完，完全静止了
                    if (!isLeftSet){
                        int firstItem = mGridLayoutManager.findFirstVisibleItemPosition();
                        Log.d(TAG,"firstItem:"+firstItem);
                        //回调，改变左表状态
                        int index = 0;
                        for(int i=0;i<=firstItem;i++){
                            DetailsBean detailsBean = mDatas.get(i);
                            if(detailsBean.isTitle()){
                                index++;
                            }
                        }
                        setCheck(index-1);
                    } 
                    isLeftSet = false;
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position,isScroll);
    }

    /**
     * 改变左侧RecyclerView状态
     */
    @Override
    public void setCheck(int position) {
        changeLeftListener.setCheck(position);
    }
}
