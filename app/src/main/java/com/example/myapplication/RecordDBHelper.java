package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

public class RecordDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "RecordDBHelper";
    public static final String DB_NAME = "RECORDS";

    public RecordDBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(" +
                "id integer primary key autoincrement ," +
                "type integer ," +
                "amount double," +
                "category text ," +
                "imageId integer," +
                "uuid text," +
                "timestamp integer ," +
                "date date ," +
                "remarks text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addRecord(Record record){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type",record.getType());
        cv.put("amount",record.getAmount());
        cv.put("category",record.getCategory().getName());
        cv.put("imageId",record.getCategory().getImageId());
        cv.put("uuid",record.getUuid());
        cv.put("timestamp",record.getTimestamp());
        cv.put("date",record.getDate());
        cv.put("remarks",record.getRemark());
        db.insert("records",null,cv);
        db.close();
    }

    public void removeRecordd(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from records where uuid=?",new String[]{uuid});
        db.close();
    }

    public void updateRecord(String uuid,Record record){
        removeRecordd(uuid);
        record.setUuid(uuid);
        addRecord(record);
    }

    public LinkedList<Record> searchRecords(String date_index,arrangeMode mode){
        LinkedList<Record> records = new LinkedList<>();
        Record record = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        //根据模式切换排列
        if(mode == arrangeMode.ASC){
            cursor = db.rawQuery("select distinct * from records where date=? order by timestamp asc",new String[]{date_index});
        }else {
            cursor = db.rawQuery("select distinct * from records where date=? order by timestamp desc",new String[]{date_index});
        }
        if(cursor.moveToFirst()){
            do {
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                int timestamp = cursor.getInt(cursor.getColumnIndex("timestamp"));
                String date  = cursor.getString(cursor.getColumnIndex("date"));
                String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
                Category category1 = new Category();
                category1.setName(category);
                category1.setImageId(imageId);

                record = new Record();
                record.setType(type);
                record.setAmount(amount);
                record.setUuid(uuid);
                record.setCategory(category1);
                record.setTimestamp(timestamp);
                record.setDate(date);
                record.setRemark(remarks);

                records.add(record);

            }while (cursor.moveToNext());
            if(db != null) {
                db.close();
            }
        }
        return records;
    }

    public LinkedList<String> getAvaliableDate(){
        LinkedList<String> dates = new LinkedList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct date from records order by date asc",new String[]{});
        if(cursor.moveToFirst()){
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                dates.add(date);
                Log.d(TAG,"数据库里获得的日期 : "+date);
            }while (cursor.moveToNext());
        }
        if(db != null){
            db.close();
        }
        return dates;
    }

}
