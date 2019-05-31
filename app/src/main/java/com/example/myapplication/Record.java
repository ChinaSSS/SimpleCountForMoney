package com.example.myapplication;

import java.util.UUID;

public class Record {

    private TYPE type ;
    private String category ;
    private double amount ;
    private String remark ;
    private String uuid ;
    private String date ;
    private long timestamp ;

    public Record(){
        uuid = UUID.randomUUID().toString();
        timestamp = System.currentTimeMillis();
        date = DateUtil.getFormatDate();
    }

    public int getType() {
        if(type == TYPE.EXPEND){
            return 1;
        }else return 0;
    }

    public void setType(int type) {
        if(type == 1){
            this.type = TYPE.EXPEND;
        }else this.type =TYPE.INCOME;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
