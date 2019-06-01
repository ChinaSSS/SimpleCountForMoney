package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.LinkedList;

public class MyListViewAdapter extends BaseAdapter {

    private LinkedList<Record> records ;
    private Context mcontext ;

    public MyListViewAdapter(Context context){
        this.mcontext = context;
    }

    //测试方法
    public void setRecords(LinkedList<Record> records){
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view ;
        Record record = (Record) getItem(position);
        if(convertView == null){
        view = LayoutInflater.from(mcontext).inflate(R.layout.listview_cell,parent,false);
        viewHolder = new ViewHolder();
        viewHolder.imageView = view.findViewById(R.id.iv);
        viewHolder.mtv_category = view.findViewById(R.id.tv_category);
        viewHolder.mtv_remark = view.findViewById(R.id.tv_remark);
        viewHolder.mtv_money = view.findViewById(R.id.tv_money);
        viewHolder.mtv_time = view.findViewById(R.id.tv_time);
        view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(record.getCategory().getImageId());
        viewHolder.mtv_category.setText(record.getCategory().getName());
        viewHolder.mtv_remark.setText(record.getRemark());
        viewHolder.mtv_time.setText(DateUtil.getFormatTime(record.getTimestamp()));
        String amount;
        if(record.getType()==1){
            amount = "- "+record.getAmount();
        }else {{
            amount = "+ "+record.getAmount();
        }}
        viewHolder.mtv_money.setText(String.valueOf(amount));
        return view;
    }

    public double CountMoney(){
        double count = 0;
        for(int i=0;i<records.size();i++){
            if(records.get(i).getType() == 1){
                count = count - records.get(i).getAmount();
            }else {
                count = count + records.get(i).getAmount();
            }
        }
        return count;
    }

    class ViewHolder {
        ImageView imageView ;
        TextView mtv_category ;
        TextView mtv_remark ;
        TextView mtv_money ;
        TextView mtv_time;

    }

}
