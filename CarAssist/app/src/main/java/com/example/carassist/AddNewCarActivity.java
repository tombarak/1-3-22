package com.example.carassist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AddNewCarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap photo;
    private Button photoButton;
    private Button backButton;
    private Button finishButton;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText agencyEditText;
    private EditText commentsEditText;
    private String photoFileName;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        //TODOthis.imageView = (ImageView)this.findViewById(R.id.imageView1);
        photoButton = (Button) this.findViewById(R.id.takePhotoBtn);
        backButton =  (Button) this.findViewById(R.id.button4);
        finishButton = (Button) this.findViewById(R.id.finishBtn);
        nameEditText = (EditText) this.findViewById(R.id.carNameEditText);
        priceEditText = (EditText) this.findViewById(R.id.priceEditText);
        agencyEditText = (EditText) this.findViewById(R.id.carAgencyDetailsEditText);
        commentsEditText = (EditText) this.findViewById(R.id.commentsEditText);
        photoButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        photo = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            SavePhoto();
        }
    }

    private void SavePhoto() {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();


        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        String dateString = formatter.format(currentTime);
        photoFileName = "Image-" + dateString + ".jpg";
        File file = new File(myDir, photoFileName);
        FileOutputStream oFile = null;
        try {
            file.createNewFile();
            oFile = new FileOutputStream(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (file.exists())
        {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    public boolean saveNewCar() {
        if (nameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter valid car name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (priceEditText.getText().toString().isEmpty() || !isInteger(priceEditText.getText().toString())) {
            Toast.makeText(this, "Please enter valid car price (integer)", Toast.LENGTH_LONG).show();
            return false;
        }
        if (agencyEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter valid agency", Toast.LENGTH_LONG).show();
            return false;
        }
        String photoPath = "";
        if (photo != null) {
            photoPath = photoFileName;
        }
        Car c = new Car(nameEditText.getText().toString(), Integer.valueOf(priceEditText.getText().toString()), agencyEditText.getText().toString(), commentsEditText.getText().toString(), photoPath);
        db.addCar(c);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == photoButton) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        else if (view == finishButton) {
            if( saveNewCar()) {
                Intent intent = new Intent(this, FinishAddActivity.class);
                startActivity(intent);
            }
        }
        else if (view == backButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

