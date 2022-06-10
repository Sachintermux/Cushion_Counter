package com.sna.cushion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListView_Adapter adapter;
    private Main_ViewModel viewModel;
    private TextView chairTextView, previousTimeThird_TxtView;
    private ImageView settingsImgV, chairState_imgView;
    private LinearLayout secondLayout, fourLayout;
    private RelativeLayout firstLayout;

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
            startActivity(new Intent(MainActivity.this, Settings_Activity.class)
                    .putExtra("SSID", viewModel.ssid.getValue())
                    .putExtra("Password", viewModel.password.getValue()));
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void observers() {
        viewModel.test.observe(MainActivity.this, data -> {
            if (data) {
                deviceIsActive();
            } else {
                deviceIsDeActive();
            }
        });
        viewModel.fireDataList.observe(MainActivity.this, data -> {
            if (data.size() != 0) setListView(data);
        });
        viewModel.currentState.observe(MainActivity.this, data -> {
            if (data.contains("0")) {
                chairTextView.setText("Chair Is Empty");
                chairState_imgView.setImageDrawable(getDrawable(R.drawable.ic_chair_empty_white));
            } else if (data.contains("1")) {
                chairTextView.setText("Chair Is Occupied");
                chairState_imgView.setImageDrawable(getDrawable(R.drawable.ic_chair_full));
            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void deviceIsDeActive() {
        fourLayout.setBackground(getDrawable(R.color.dark_gray));
        chairState_imgView.setImageDrawable(getDrawable(R.drawable.ic_chair_empty_gray));
        secondLayout.setBackground(getDrawable(R.drawable.round_corner_light_gray_back));
        chairTextView.setText("Device Not Connected To Internet");


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void deviceIsActive() {
        fourLayout.setBackground(getDrawable(R.color.theme_color_dark));
        secondLayout.setBackground(getDrawable(R.drawable.round_corner_yellow_back));

    }

    private void initView() {
        listView = findViewById(R.id.listView_main);
        chairTextView = findViewById(R.id.showChairState_txtV_mainA);
        settingsImgV = findViewById(R.id.settingsIc_mainA);
        chairState_imgView = findViewById(R.id.chairImgView_mainA);

        firstLayout = findViewById(R.id.firstRelativeLayout_main);
        secondLayout = findViewById(R.id.secondLinearLayout_main);
        previousTimeThird_TxtView = findViewById(R.id.thirdTxtView_main);
        fourLayout = findViewById(R.id.fourLinearLayout_main);

        viewModel = new ViewModelProvider(MainActivity.this).get(Main_ViewModel.class);
        viewModel.startTimer();

        viewModel.getData();

    }

    private void setListView( ArrayList<FireBaseDataModel> arrayList ) {
        adapter = new ListView_Adapter(MainActivity.this, R.layout.lsit_view, arrayList,viewModel,MainActivity.this);
        listView.setAdapter(adapter);
    }
}