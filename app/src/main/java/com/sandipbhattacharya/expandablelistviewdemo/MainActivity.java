package com.sandipbhattacharya.expandablelistviewdemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Room;
import models.SQLiteHelper;

public class MainActivity extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    SQLiteHelper sqLiteHelper;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText maxPrice;
    EditText minPrice;
    EditText maxArea;
    EditText minArea;

    List<Room> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(MainActivity.this);
        roomList = sqLiteHelper.getAllRoom();
        createGroupList();
        createCollection();
        expandableListView = findViewById(R.id.elvMobiles);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, mobileCollection);
        expandableListView.setAdapter(expandableListAdapter);

        editText1 = findViewById(R.id.edtArea);
        editText2 = findViewById(R.id.edtPrice);
        editText3 = findViewById(R.id.edtElectric);
        editText4 = findViewById(R.id.edtWater);
        editText5 = findViewById(R.id.edtSection);
        maxPrice = findViewById(R.id.edtPriceMax);
        minPrice = findViewById(R.id.edtPriceMin);
        maxArea = findViewById(R.id.edtAreaMax);
        minArea = findViewById(R.id.edtAreaMin);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Button addRoom = findViewById(R.id.addButton);
        Button searchBtn = findViewById(R.id.search);

        NumberPicker sectionPicker = findViewById(R.id.section);
        sectionPicker.setMinValue(1);
        sectionPicker.setMaxValue(5);

        searchBtn.setOnClickListener(view -> {
            maxPrice = findViewById(R.id.edtPriceMax);
            minPrice = findViewById(R.id.edtPriceMin);
            maxArea = findViewById(R.id.edtAreaMax);
            minArea = findViewById(R.id.edtAreaMin);
            roomList = sqLiteHelper.searchAll(Integer.parseInt(minPrice.getText().toString()),
                    Integer.parseInt(maxPrice.getText().toString()),
                    Integer.parseInt(minArea.getText().toString()),
                    Integer.parseInt(maxArea.getText().toString()),
                    sectionPicker.getValue());
            createGroupList();
            createCollection();
            expandableListAdapter = new MyExpandableListAdapter(this, groupList, mobileCollection);
            expandableListView.setAdapter(expandableListAdapter);
        });

        addRoom.setOnClickListener(view -> {
            addRoom();
            BaseExpandableListAdapter itemAdapter = (BaseExpandableListAdapter) expandableListView.getExpandableListAdapter();
            itemAdapter.notifyDataSetChanged();
        });
    }

    private void createCollection() {
        Room roomDummy = new Room(20, 5, 3, 3, 4);
        mobileCollection = new HashMap<String, List<String>>();
        for(Room room : roomList){
            loadChild(room);
            mobileCollection.put("Room "+room.getId(), childList);
        }
    }

    private void loadChild(Room room) {
        childList = new ArrayList<>();
        childList.add("Diện Tích: "+room.getArea());
        childList.add("Giá Thuê: "+room.getPrice()+" Triệu");
        childList.add("Tiền điện: "+room.getElectric()+"/Số");
        childList.add("Tiền nước: "+room.getWater()+"/Số");
        childList.add("Khu vực: "+room.getSection()+"/Số");

    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        for(Room room: roomList){
            groupList.add("Room "+room.getId());
        }

    }

    private void addRoom(){
        Room roomDummy = new Room(Integer.parseInt(editText1.getText().toString()),
                Integer.parseInt(editText2.getText().toString()),
                Integer.parseInt(editText3.getText().toString()),
                Integer.parseInt(editText4.getText().toString()),
                Integer.parseInt(editText5.getText().toString()));
        sqLiteHelper.addRoom(roomDummy);
        String name = "Room " + (groupList.size()+1);
        groupList.add(name);
        loadChild(roomDummy);
        mobileCollection.put(name, childList);
    }

}