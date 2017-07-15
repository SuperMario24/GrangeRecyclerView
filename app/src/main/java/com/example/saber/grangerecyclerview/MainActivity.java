package com.example.saber.grangerecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.saber.grangerecyclerview.adapter.RecyclerViewLeftAdapter;
import com.example.saber.grangerecyclerview.fragment.FragmentDetails;
import com.example.saber.grangerecyclerview.interfaze.ChangeLeftListener;
import com.example.saber.grangerecyclerview.interfaze.CheckListener;
import com.example.saber.grangerecyclerview.interfaze.OnRecyclerViewOnClickListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CheckListener,ChangeLeftListener{

    private RecyclerView recyclerViewLeft;
    private FragmentDetails fragmentDetails;
    private RecyclerViewLeftAdapter recyclerViewLeftAdapter;
    
    private Context mContext;

    public static boolean left;
    public static int finalNumber = 0;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        initData();
        
    }

    private void initViews() {
        mContext = this;
        recyclerViewLeft = (RecyclerView) findViewById(R.id.rv_left);
        linearLayoutManager= new LinearLayoutManager(mContext);
        recyclerViewLeft.setLayoutManager(linearLayoutManager);




    }

    private void initData() {

        String[] strings = getResources().getStringArray(R.array.pill);
        List<String> leftTitles = Arrays.asList(strings);

        recyclerViewLeftAdapter = new RecyclerViewLeftAdapter(mContext, leftTitles, new OnRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //改变左侧recyclerView状态
                recyclerViewLeftAdapter.setCheckPosition(position);
                //TODO
                if(fragmentDetails != null){
                    check(position,true);
                }
            }

            @Override
            public void onItemClick(int id, int position) {
                //ingore
            }
        });
        recyclerViewLeft.setAdapter(recyclerViewLeftAdapter);

        createFragment();

    }

    private void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentDetails = new FragmentDetails();
        fragmentDetails.setListener(this,this);
        fragmentTransaction.add(R.id.fragment_details, fragmentDetails);
        fragmentTransaction.commit();
    }

    @Override
    public void check(int position, boolean isScroll) {
        finalNumber = position;
        left = isScroll;
        if(left){
            fragmentDetails.setData(position * 10 + position);
        }
    }

    /**
     * 右表联动左表
     * @param position
     */
    @Override
    public void setCheck(int position) {

        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();

        if(position < firstItem || position >= lastItem){
            recyclerViewLeft.scrollToPosition(position);
        }
        recyclerViewLeftAdapter.setCheckPosition(position);
    }
}
