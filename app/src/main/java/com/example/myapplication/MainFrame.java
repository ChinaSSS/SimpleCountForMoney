package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

@SuppressLint("ValidFragment")

public class MainFrame extends Fragment {
    private static final String TAG = "MainFrame";

    private TextView mtv_display_date ;
    private ListView listView ;
    private TextView mtv_display_norecords ;
    private RelativeLayout relativeLayout_one ;
    private RelativeLayout relativeLayout_two ;
    private String date = "";
    private MyListViewAdapter listViewAdapter ;
    private LinkedList<Record> records = new LinkedList<>();
    private LocalBroadcastManager localBroadcastManager;
    private Intent FlushMoneyInTickerView = new Intent(MainActivity.BraodcastFlitterMessage);

    public MainFrame(String date){
        this.date = date;
        records = GlobalResourceMannager.getInstance().getHelper().searchRecords(date,arrangeMode.DESC);
        localBroadcastManager = LocalBroadcastManager.getInstance(GlobalResourceMannager.getInstance().getContext());
    }

    //此方法耗时
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_frame,container,false);
        //to do for init view
        mtv_display_date = view.findViewById(R.id.tv_Display_Date);
        listView = view.findViewById(R.id.listview);
        mtv_display_norecords = view.findViewById(R.id.tv_no_records);
        mtv_display_date.setText(date);
        relativeLayout_one = view.findViewById(R.id.frame_one);
        relativeLayout_two = view.findViewById(R.id.frame_two);

        //to doff
        listViewAdapter = new MyListViewAdapter(getContext());
        adapterListview();
        addListenerToListView();
        return view;
    }

    public void Flush_mainFrame(){
        records = GlobalResourceMannager.getInstance().getHelper().searchRecords(date,arrangeMode.DESC);
        if(records.size() != 0){
            if(listViewAdapter == null){
                listViewAdapter = new MyListViewAdapter(getContext());
            }
            // to flush data
            adapterListview();
        }else {
            //如果数据被删完了,无法刷新逻辑，就隐藏
            relativeLayout_one.setVisibility(View.GONE);
            relativeLayout_two.setVisibility(View.VISIBLE);
        }
    }

    public void adapterListview(){
        records = GlobalResourceMannager.getInstance().getHelper().searchRecords(date,arrangeMode.DESC);
        listViewAdapter.setRecords(records);//传递records
        listViewAdapter.notifyDataSetChanged();
        listView.setAdapter(listViewAdapter);
        if(records.size() == 0){
            relativeLayout_one.setVisibility(View.GONE);
        }else {
            relativeLayout_two.setVisibility(View.GONE);
        }
    }

    public void addListenerToListView(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemsDialogFragment itemsDialogFragment = new ItemsDialogFragment();
                itemsDialogFragment.show("", new String[]{"编辑", "删除"}, getFragmentManager(), (dialog, which) -> {
                    switch (which){
                        case 0:
                            // to edit

                            break;
                        case 1:
                            //to del
                            if(records.get(position)!=null){
                                del(records.get(position).getUuid());
                            }
                            break;
                    }
                });
                return true;
            }
        });
    }

    public double getCountMoneyByListviewAdapter(){
        Flush_mainFrame();
        // to flush money in tickerview
        return listViewAdapter.CountMoney();
    }

    public void del(String uuid){
        //del the record by uuid in database
        GlobalResourceMannager.getInstance().getHelper().removeRecordd(uuid);
        //flush all the frame , it is so stupid
        Flush_mainFrame();
        //to flush the money in tickerview in main activity by local broadcast
        localBroadcastManager.sendBroadcast(FlushMoneyInTickerView);
    }

    public void edit(){

    }

}
