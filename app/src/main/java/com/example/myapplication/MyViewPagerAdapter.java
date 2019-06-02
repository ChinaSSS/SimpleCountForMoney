package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import java.util.LinkedList;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MyViewPagerAdapter";

    private LinkedList<MainFrame> mainFrames  = new LinkedList<>();
    private LinkedList<String> dates ;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        MainFrame mainFrame;
        dates = GlobalResourceMannager.getInstance().getHelper().getAvaliableDate();
        if(!dates.contains(DateUtil.getFormatDate())){
            dates.addLast(DateUtil.getFormatDate());
        }
        for(int i=0;i<dates.size();i++){
            mainFrame = new MainFrame(dates.get(i));
            mainFrames.add(mainFrame);
        }
    }

    @Override
    public Fragment getItem(int i) {
        return mainFrames.get(i);
    }

    @Override
    public int getCount() {
        return mainFrames.size();
    }

    public int getCurrentFrame(){
        return mainFrames.size() - 1;
    }

    public double getCurrentFramentCountMoney(){
        return getCountMoneyOfFragment_I(getCurrentFrame());
    }

    public void Flash_PagerAdapter(){
        // to flush fragment
        if(mainFrames != null){
            for(MainFrame m : mainFrames){
                m.Flush_mainFrame();
            }
        }
        else {
            Log.d(TAG," the frames is null");
        }
    }

    public double getCountMoneyOfFragment_I(int i){
        return mainFrames.get(i).getCountMoneyByListviewAdapter();
    }
}
