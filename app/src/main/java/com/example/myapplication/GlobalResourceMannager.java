package com.example.myapplication;

import android.content.Context;

import java.util.LinkedList;

public class GlobalResourceMannager {
    private static final String TAG = "GlobalResourceMannager";

    private Context context;
    private RecordDBHelper recordDBHelper;
    private LinkedList<Category> income;
    private LinkedList<Category> expend;

    public static GlobalResourceMannager globalResourceMannager;

    public static GlobalResourceMannager getInstance(){
        if(globalResourceMannager == null){
            globalResourceMannager = new GlobalResourceMannager();
            globalResourceMannager.InitCategory();
        }
        return globalResourceMannager;
    }

    private static String[] category_in_name = {"工资","兼职","理财收益","其他"};
    private static String[] category_out_name = {"一般","餐饮","购物","服饰","交通","娱乐",
                                          "社交","居家","通讯","零食","美容","运动","旅行",
                                            "数码","学习","医疗","书籍","宠物","礼物","理财"};
    private static int[] in_src = {R.drawable.salary,R.drawable.part_time_job,R.drawable.financemangage,R.drawable.other};
    private static int[] out_src = {R.drawable.just_so_so,R.drawable.food,R.drawable.shop,R.drawable.clothes,R.drawable.traffic,
                    R.drawable.entertain,R.drawable.social,R.drawable.home_live,R.drawable.phone_pay,R.drawable.snack,
                    R.drawable.face_care,R.drawable.sport,R.drawable.travel,R.drawable.digital,R.drawable.learn,R.drawable.medicine,
                    R.drawable.book,R.drawable.pet,R.drawable.gift,R.drawable.reward};

    public void InitCategory(){
        income = new LinkedList<>();
        expend = new LinkedList<>();
        Category category ;
        //income
        for(int i=0;i<category_in_name.length;i++){
            category = new Category();
            category.setName(category_in_name[i]);
            category.setImageId(in_src[i]);
            income.add(category);
        }
        //expend
        for(int i=0;i<category_out_name.length;i++){
            category = new Category();
            category.setName(category_out_name[i]);
            category.setImageId(out_src[i]);
            expend.add(category);
        }
    }

    public LinkedList<Category> getIncome() {
        return income;
    }

    public LinkedList<Category> getExpend() {
        return expend;
    }

    public Context getContext(){
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
        recordDBHelper =new RecordDBHelper(context,RecordDBHelper.DB_NAME,null,1);
    }

    public RecordDBHelper getHelper(){
        return recordDBHelper;
    }
}
