package com.sna.cushion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings_Activity extends AppCompatActivity {

    private EditText ssid_edt,password_edt;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Chair");
    private String ssid;
    private  String password;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settigns);
        Intent intent = getIntent();
        ssid = intent.getStringExtra("SSID");
        password = intent.getStringExtra("Password");
        initView(ssid,password);

    }

    private void initView(String ssid,String password) {
        ssid_edt = findViewById(R.id.wifiSSID_edt_settingsActivity);
        password_edt = findViewById(R.id.wifiPassword_edt_settingsActivity);
        ssid_edt.setText(ssid);
        password_edt.setText(password);
    }

    public void saveBtnClick( View view ) {
        ssid = ssid_edt.getText().toString();
        password = password_edt.getText().toString();
        myRef.child("SSID").setValue(ssid);
        myRef.child("Password").setValue(password);
        myRef.child("Change").setValue(1);
    }

    public void backButtonClick( View view ) {
        finish();
    }

}