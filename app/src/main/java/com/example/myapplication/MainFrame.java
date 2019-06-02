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
    private View rootview;
    private String date = "";
    private MyListViewAdapter listViewAdapter ;
    private LinkedList<Record> records;
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
        rootview = inflater.inflate(R.layout.main_frame,container,false);
        IniteView();
        return rootview;
    }

    public void IniteView(){
        //to do for init view
        mtv_display_date = rootview.findViewById(R.id.tv_Display_Date);
        listView = rootview.findViewById(R.id.listview);
        mtv_display_norecords = rootview.findViewById(R.id.tv_no_records);
        mtv_display_date.setText(date);
        relativeLayout_one = rootview.findViewById(R.id.frame_one);
        relativeLayout_two = rootview.findViewById(R.id.frame_two);
        //to doff
        listViewAdapter = new MyListViewAdapter(getContext());
        listViewAdapter.setRecords(records);//传递records
        //adapter list view
        listViewAdapter.notifyDataSetChanged();
        listView.setAdapter(listViewAdapter);
        if(records.size() > 0){
           relativeLayout_two.setVisibility(View.GONE);
        }
        //set listener to list view
        addListenerToListView();
    }

    public void Flush_mainFrame(){
        records = GlobalResourceMannager.getInstance().getHelper().searchRecords(date,arrangeMode.DESC);
        if(listViewAdapter == null){
            listViewAdapter = new MyListViewAdapter(GlobalResourceMannager.getInstance().getContext());
        }
        listViewAdapter.setRecords(records);//传递records
        listView.setAdapter(listViewAdapter);
        if(records.size()>0){
            rootview.findViewById(R.id.frame_two).setVisibility(View.GONE);
        }
    }

    public void addListenerToListView(){
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
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
        });
    }

    public double getCountMoneyByListviewAdapter(){
        // return money
        double money = 0;
        for(int i=0;i<records.size();i++){
            if(records.get(i).getType() == 1){
                money-=records.get(i).getAmount();
            }else {
                money+=records.get(i).getAmount();
            }
        }
        return money;
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
