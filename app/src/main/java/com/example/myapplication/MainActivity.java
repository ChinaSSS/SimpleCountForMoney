package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public static final int RequestCode = 0x1;
    private static final String TAG = "MainActivity";

    private TickerView tickerView ;
    private ViewPager viewPager ;
    private MyViewPagerAdapter viewPagerAdapter ;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GlobalResourceMannager.getInstance().setContext(getApplicationContext());
        tickerView = findViewById(R.id.tickerview);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());

        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(viewPagerAdapter.getCurrentFrame());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(viewPagerAdapter != null){
                    double money = viewPagerAdapter.getCountMoneyOfFragment_I(i);
                    tickerView.setText(String.valueOf(money));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setCurrentMoney();

        floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,AddRecord.class);
            startActivityForResult(intent,RequestCode);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // to flush
        switch(requestCode){
            case RequestCode:
                if(resultCode == RESULT_OK){
                    if(viewPagerAdapter != null){
                        viewPagerAdapter.Flash_PagerAdapter();
                        Log.d(TAG," main activity flush ok");
                    }
                }
        }
    }

    public void setCurrentMoney(){
        LinkedList<String> avaliableDate = GlobalResourceMannager.getInstance().getHelper().getAvaliableDate();
        String s = avaliableDate.get(avaliableDate.size() - 1);
        LinkedList<Record> records = GlobalResourceMannager.getInstance().getHelper().searchRecords(s, arrangeMode.DESC);
        double count = 0;
        for(int i=0;i<records.size();i++){
            if(records.get(i).getType() == 1){
                count = count - records.get(i).getAmount();
            }else {
                count = count + records.get(i).getAmount();
            }
        }
        tickerView.setText(String.valueOf(count));
    }
}
