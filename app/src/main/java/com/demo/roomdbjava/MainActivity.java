package com.demo.roomdbjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText edt_text;
    Button btn_add,btn_reset;
    RecyclerView recycler_view;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edt_text = (EditText)findViewById(R.id.edt_text);

        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);

        btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String sText = edt_text.getText().toString();

                if (!sText.equals(""))
                {
                    MainData data = new MainData();
                    data.setText(sText);

                    //insert text in database
                    database.mainDao().insert(data);

                    //clear editText
                    edt_text.setText("");

                    //notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });


        btn_reset = (Button)findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //delete all data from database
                database.mainDao().reset(dataList);

                //notify when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });


        //initialize database
        database = RoomDB.getInstance(this);

        //store database value in dataList
        dataList = database.mainDao().getAll();


        linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);

        adapter = new MainAdapter(MainActivity.this,dataList);
        recycler_view.setAdapter(adapter);

    }
}