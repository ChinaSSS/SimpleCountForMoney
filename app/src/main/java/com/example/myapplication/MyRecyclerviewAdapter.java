package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.MyViewHolder> {
    private static final String TAG = "MyRecyclerviewAdapter";

    private Context mcontext;
    private MyViewHolder myViewHolder;
    private LinkedList<Category> categories = GlobalResourceMannager.getInstance().getExpend();
    private String selected;
    private OnCategoryClicked onCategoryClicked;

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setType(TYPE type) {
        if(type==TYPE.EXPEND){
            categories = GlobalResourceMannager.getInstance().getExpend();
        }else {
            categories = GlobalResourceMannager.getInstance().getIncome();
        }
        selected = categories.get(0).getName();
        notifyDataSetChanged();
    }

    public void setCurrentUI(TYPE type,String selected){
        if(type == TYPE.EXPEND){
            categories = GlobalResourceMannager.getInstance().getExpend();
        }else {
            categories = GlobalResourceMannager.getInstance().getIncome();
        }
        this.selected = selected;
        notifyDataSetChanged();
    }


    public MyRecyclerviewAdapter(Context context,OnCategoryClicked onCategoryClicked){
        this.mcontext = context;
        this.onCategoryClicked = onCategoryClicked;
        selected = categories.get(0).getName();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.recyclerview_cell,viewGroup,false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Category category = categories.get(i);
        myViewHolder.imageView.setImageResource(category.getImageId());
        myViewHolder.textView.setText(category.getName());
        myViewHolder.relativeLayout.setOnClickListener(v->{
            selected = category.getName();
            notifyDataSetChanged();
            if(onCategoryClicked != null){
                onCategoryClicked.click(category);
            }
        });
        if(category.getName().equals(selected)){
            myViewHolder.relativeLayout.setBackgroundResource(R.drawable.recycler_cell_bg);
        }else {
            myViewHolder.relativeLayout.setBackgroundResource(R.drawable.recycyler_cell_origin_bg);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.textview);
            relativeLayout = itemView.findViewById(R.id.relativelayout);
        }
    }

    interface OnCategoryClicked{
        void click(Category category);
    }
}
