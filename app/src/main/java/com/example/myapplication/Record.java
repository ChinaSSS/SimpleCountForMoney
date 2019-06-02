package com.example.myapplication;

import java.io.Serializable;
import java.util.UUID;

// to serializable for transport
public class Record implements Serializable {

    private TYPE type ;
//    private String category ;
    private Category category;
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

    public void setType(TYPE type){
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    @Override
    public String toString() {
        return "Record{" +
                "type=" + type +
                ", category.name=" + category.getName() +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                ", uuid='" + uuid + '\'' +
                ", date='" + date + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
