package com.example.carassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class CarDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHelper db = new DatabaseHelper(this);
    private Car car = null;
    Button backListBtn;
    Button backMainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        backListBtn = (Button)findViewById(R.id.backListBtn);
        backMainBtn = (Button)findViewById(R.id.backMainBtn);

        backListBtn.setOnClickListener(this);
        backMainBtn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            car = db.getCarDetails(extras.getString("carname"));
            if(!car.getPhotoPath().isEmpty()) {
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                File imgFile = new File(myDir, car.getPhotoPath());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.carImageView);
                    myImage.setImageBitmap(myBitmap);
                }
            }

            TextView tv = (TextView) findViewById(R.id.textView5);
            tv.setText("Car Model: " + car.getName());
            tv = (TextView) findViewById(R.id.textView6);
            tv.setText("Price: " + car.getPrice() + "$");
            tv = (TextView) findViewById(R.id.textView7);
            tv.setText("Agency: " + car.getAgency());
            tv = (TextView) findViewById(R.id.textView8);
            tv.setText("Comments: " + car.getComments());

        }
            }

    @Override
    public void onClick(View view) {
        if(view == backListBtn)
        {
            Intent intent = new Intent(this, CarListActivity.class);
            startActivity(intent);
        }
        else if(view== backMainBtn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
