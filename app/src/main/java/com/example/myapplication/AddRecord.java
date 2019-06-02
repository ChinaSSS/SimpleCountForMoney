package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecord extends AppCompatActivity implements View.OnClickListener , MyRecyclerviewAdapter.OnCategoryClicked {
    private static final String TAG = "AddRecord";

    private TextView mtv;
    private EditText met;
    private RecyclerView recyclerView;

    private Button button_one;
    private Button button_two;
    private Button button_three;
    private Button button_four;
    private Button button_five;
    private Button button_six;
    private Button button_seven;
    private Button button_eight;
    private Button button_nine;
    private Button button_zero;
    private Button button_point;
    private Button button_00;

    private ImageButton imageButton_category;
    private ImageButton imageButton_back;
    private ImageButton imageButton_ensure;

    // the input money
    private String input = "";
    private TYPE type =TYPE.EXPEND;
    private Category category;
    private MyRecyclerviewAdapter myRecyclerviewAdapter;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        mtv = findViewById(R.id.textview);
        met = findViewById(R.id.edittext);
        recyclerView = findViewById(R.id.recyclerview);
        myRecyclerviewAdapter = new MyRecyclerviewAdapter(this,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myRecyclerviewAdapter);

        button_one = findViewById(R.id.keyboard_one);
        button_two = findViewById(R.id.keyboard_two);
        button_three = findViewById(R.id.keyboard_three);
        button_four = findViewById(R.id.keyboard_four);
        button_five = findViewById(R.id.keyboard_five);
        button_six = findViewById(R.id.keyboard_six);
        button_seven = findViewById(R.id.keyboard_seven);
        button_eight = findViewById(R.id.keyboard_eight);
        button_nine = findViewById(R.id.keyboard_nine);
        button_zero = findViewById(R.id.keyboard_zero);
        button_00 = findViewById(R.id.keyboard_00);

        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);
        button_three.setOnClickListener(this);
        button_four.setOnClickListener(this);
        button_five.setOnClickListener(this);
        button_six.setOnClickListener(this);
        button_seven.setOnClickListener(this);
        button_eight.setOnClickListener(this);
        button_nine.setOnClickListener(this);
        button_zero.setOnClickListener(this);
        button_00.setOnClickListener(this);

        //to set function button click-listener
        ClickPoint();
        ClickBack();
        ClickSwitch();
        ClickEnsure();
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        String str = button.getText().toString();
        // judge the amount
        if(!input.contains(".")){
            input = input + str;
        }else if(input.split("\\.").length == 1){
            input = input + str;
        }else if((input.split("\\.")[1].length()<2)&&(str.length()<2)){
            input = input + str;
        }
        updateAmount();
    }

    public void ClickPoint(){
        button_point = findViewById(R.id.keyboard_point);
        button_point.setOnClickListener(v->{
            Log.d(TAG,"i am point");
            if(!input.contains(".")&&!input.equals("")){
                input = input + ".";
            }
            updateAmount();
        });
    }

    public void ClickSwitch(){
        imageButton_category = findViewById(R.id.keyboard_switch);
        imageButton_category.setOnClickListener(v->{
            if(type == TYPE.EXPEND){
                type = TYPE.INCOME;
            }else {
                type = TYPE.EXPEND;
            }
            myRecyclerviewAdapter.setType(type);
        });
    }

    public void ClickBack(){
        imageButton_back = findViewById(R.id.keyboard_back);
        imageButton_back.setOnClickListener(v->{
            if(input.contains(".")){
                // 11.
                if(input.split("\\.").length == 1){
                    input = input.split("\\.")[0];
                } else if(input.split("\\.")[1].length()>=1){
                    // 11.1 11.22
                    input = input.substring(0,input.length() - 1);
                }
            }else {
                if(input.length()==0){
                    Toast.makeText(this, "Don't uncover my bottom", Toast.LENGTH_SHORT).show();
                }else {
                    input = input.substring(0,input.length()-1);
                }
            }
            updateAmount();
        });
    }

    //to save the obj(record) into db
    public void ClickEnsure(){
        imageButton_ensure = findViewById(R.id.keyboard_ensure);
        imageButton_ensure.setOnClickListener(v->{
            //create a obj(record)
            if(!input.equals("")){
                record = new Record();
                double amount = Double.valueOf(formatMoney());
                String remark;
                if(met.getText().toString().equals("")){
                    remark = "";
                }else {
                    remark = met.getText().toString();
                }
                //to set data
                record.setAmount(amount);
                if(category == null&&(!input.equals(""))){
                    category = new Category();
                    if(type == TYPE.EXPEND){
                        category.setName("一般");
                        category.setImageId(R.drawable.just_so_so);
                    }else {
                        category.setName("工资");
                        category.setImageId(R.drawable.salary);
                    }
                }
                record.setCategory(category);
                record.setRemark(remark);
                record.setType(type);
                GlobalResourceMannager.getInstance().getHelper().addRecord(record);
                // to return signal for flushing
                setResult(RESULT_OK,new Intent());
                finish();
            }else {
                Toast.makeText(this, "请输入合法金额", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateAmount(){
        mtv.setText(input);
    }

    public String formatMoney(){
        if(input.contains(".")){
            if(input.split("\\.").length==1){
                input = input.split("\\.")[0];
            }
        }
        return input;
    }

    @Override
    public void click(Category category) {
        this.category = category;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // to return the sign
//        setResult(RESULT_OK,new Intent());
        //这里也是这样，不管有没有这个返回函数都返回的是0 ，说明还有更后一步的操作返回了???
        //有点诡异,后期弄懂f
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //按返回返回的是0 RESULT_CANCELED
//        setResult(RESULT_OK,new Intent());
        //这里不管有没有这个都显示的是返回的0,说明在更后一步操作里对返回值进行了更改
    }
}
