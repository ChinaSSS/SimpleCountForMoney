package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TickerView tickerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tickerView = findViewById(R.id.tickerview);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());


    }
}
