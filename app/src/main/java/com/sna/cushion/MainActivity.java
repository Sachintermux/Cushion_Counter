package com.sna.cushion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListView_Adapter adapter;
    private Main_ViewModel viewModel;
    private TextView chairTextView;
    private ImageView settingsImgV,chairState_imgView;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        allClickHandle();
        observers();
    }

    private void allClickHandle() {
        settingsImgV.setOnClickListener(view -> {
startActivity(new Intent(MainActivity.this,Settings_Activity.class)
        .putExtra("SSID",viewModel.ssid.getValue())
        .putExtra("Password",viewModel.password.getValue()));
        });
    }

    private void observers() {
        viewModel.fireDataList.observe(MainActivity.this,data->{
            if(data.size() != 0) setListView(data);
        });
viewModel.currentState.observe(MainActivity.this,data->{
    if(data.contains("0")){
        chairTextView.setText("Chair Is Empty");
        chairState_imgView.setImageDrawable(getDrawable(R.drawable.empty_chair));
    }else if(data.contains("1")){
        chairTextView.setText("Chair Is Occupied");
        chairState_imgView.setImageDrawable(getDrawable(R.drawable.occupy_chair));
    }
});

    }

    private void initView() {
        listView = findViewById(R.id.listView_main);
        chairTextView = findViewById(R.id.showChairState_txtV_mainA);
        settingsImgV = findViewById(R.id.settingsIc_mainA);
        chairState_imgView = findViewById(R.id.chairImgView_mainA);
        viewModel = new ViewModelProvider(MainActivity.this).get(Main_ViewModel.class);
        viewModel.getData();

    }

    private void setListView( ArrayList<FireBaseDataModel> arrayList ) {
        adapter = new ListView_Adapter(MainActivity.this, R.layout.lsit_view,arrayList);
        listView.setAdapter(adapter);
    }
}