package models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import models.Room;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "room";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "room";

    private static final String KEY_ID = "id";
    private static final String KEY_AREA = "area";
    private static final String KEY_PRICE = "price";
    private static final String KEY_ELECTRIC = "electric";
    private static final String KEY_WATER = "water";
    private static final String KEY_KHU_VUC = "khu_vuc";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE room(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "area INTEGER," +
                "price INTEGER," +
                "electric INTEGER," +
                "water INTEGER," +
                "khu_vuc INTEGER" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop_room_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_room_table);

        onCreate(db);
    }
    public void addRoom(Room room){
        String sql = "INSERT INTO room(area,price,electric,water,khu_vuc) values(?,?,?,?,?)";
        String[] args = {room.getArea().toString(), String.valueOf(room.getPrice()),room.getElectric().toString(),room.getWater().toString(),room.getSection().toString()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }
    public void updateRoom(float area, float price, float electric, float water, String khu_vuc){
        String sql = "UPDATE room set area = ?,price=?, electric=?, water=?, khu_vuc=? ";
        String[] args = {String.valueOf(area), String.valueOf(price), String.valueOf(electric), String.valueOf(water), khu_vuc.toString()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }
    public void deleteRoom(int id){
        String sql = "DELETE FROM room WHERE id = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }
    @SuppressLint("Range")
    public List<Room> getAllRoom(){
        List<Room> roomList = new ArrayList<>();
        String sql = "SELECT * FROM " + DATABASE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                room.setArea(cursor.getInt(cursor.getColumnIndex(KEY_AREA)));
                room.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRICE)));
                room.setElectric(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setWater(cursor.getInt(cursor.getColumnIndex(KEY_WATER)));
                room.setSection(cursor.getInt(cursor.getColumnIndex(KEY_KHU_VUC)));

                roomList.add(room);
            } while (cursor.moveToNext());
        }
        return roomList;
    }
    @SuppressLint("Range")
    public List<Room> searchByRent(Integer minRent, Integer maxRent) {
        List<Room> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DATABASE_NAME + " WHERE " + KEY_PRICE + " BETWEEN " + minRent + " AND " + maxRent;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                room.setArea(cursor.getInt(cursor.getColumnIndex(KEY_AREA)));
                room.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRICE)));
                room.setElectric(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setWater(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setSection(cursor.getInt(cursor.getColumnIndex(KEY_KHU_VUC)));

                list.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    @SuppressLint("Range")
    public List<Room> searchAll(Integer minRent, Integer maxRent,  Integer minArea, Integer maxArea, Integer section) {
        List<Room> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DATABASE_NAME + " WHERE " + KEY_PRICE + " >= " + minRent + " AND " + KEY_PRICE + " <= " + maxRent
                + " AND "+ KEY_AREA + " >= " + minArea + " AND "+ KEY_AREA + " <= " + maxArea
                + " AND "+ KEY_KHU_VUC + " = " + section;
        System.out.println(selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                room.setArea(cursor.getInt(cursor.getColumnIndex(KEY_AREA)));
                room.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRICE)));
                room.setElectric(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setWater(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setSection(cursor.getInt(cursor.getColumnIndex(KEY_KHU_VUC)));

                list.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        System.out.println("Size: "+list.size());
        return list;
    }

    @SuppressLint("Range")
    public List<Room> searchByArea(Integer area) {
        List<Room> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DATABASE_NAME + " WHERE " + KEY_PRICE + "=" + area;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                room.setArea(cursor.getInt(cursor.getColumnIndex(KEY_AREA)));
                room.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRICE)));
                room.setElectric(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setWater(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setSection(cursor.getInt(cursor.getColumnIndex(KEY_KHU_VUC)));

                list.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
    @SuppressLint("Range")
    public List<Room> searchByElectricAndKhuvuc(Integer electric, String khu_vuc) {
        List<Room> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DATABASE_NAME + " WHERE " + KEY_ELECTRIC + "=" + electric + "AND" +KEY_KHU_VUC+"="+khu_vuc;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                room.setArea(cursor.getInt(cursor.getColumnIndex(KEY_AREA)));
                room.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRICE)));
                room.setElectric(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setWater(cursor.getInt(cursor.getColumnIndex(KEY_ELECTRIC)));
                room.setSection(cursor.getInt(cursor.getColumnIndex(KEY_KHU_VUC)));

                list.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
}
