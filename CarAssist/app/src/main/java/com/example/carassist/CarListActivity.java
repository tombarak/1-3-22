package com.example.carassist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CarListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView carList;
    private Button backBtn;
    private DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        backBtn = (Button)(findViewById(R.id.button3));
        backBtn.setOnClickListener(this);
        carList = findViewById(R.id.carList);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                db.getAllCarsList());
        carList.setAdapter(arr);
        carList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backBtn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String carname =(String) adapterView.getItemAtPosition(i);

        Intent intent = new Intent(this, CarDetailsActivity.class);
        intent.putExtra("carname",carname);
        startActivity(intent);
    }
}