package com.sna.cushion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;

public class ListView_Adapter extends ArrayAdapter<String> {
    ArrayList<FireBaseDataModel> dataModels = new ArrayList<>();
    private Main_ViewModel viewModel;
    private LifecycleOwner owner;
    private Context context;

    public ListView_Adapter( @NonNull Context context, int resource, ArrayList<FireBaseDataModel> fireBaseDataModels, Main_ViewModel viewModel, LifecycleOwner owner ) {
        super(context, resource);
        this.dataModels = fireBaseDataModels;
        this.viewModel = viewModel;
        this.owner = owner;
        this.context =context;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lsit_view, parent, false);
        }

        TextView date = convertView.findViewById(R.id.dateTxt_listView),
                occupyTime = convertView.findViewById(R.id.occupyTimeTxt_listView),
                releasingTime = convertView.findViewById(R.id.releasingTimeTxt_listView),
                totalTime = convertView.findViewById(R.id.totalTxt_listView);
        date.setText(dataModels.get(position).getDate());
        occupyTime.setText(dataModels.get(position).getOccupyTime());
        releasingTime.setText(dataModels.get(position).getReleasingTime());
        totalTime.setText(dataModels.get(position).getTotalTime());

        viewModel.test.observe(owner, data -> {
            if (!data) {
                date.setTextColor(context.getResources().getColor(R.color.dark_gray));
                occupyTime.setTextColor(context.getResources().getColor(R.color.dark_gray));
                releasingTime.setTextColor(context.getResources().getColor(R.color.dark_gray));
                totalTime.setTextColor(context.getResources().getColor(R.color.dark_gray));
                System.out.println("Dark Gray");
            } else {
                date.setTextColor(context.getResources().getColor(R.color.theme_color_dark));
                occupyTime.setTextColor(context.getResources().getColor(R.color.theme_color_dark));
                releasingTime.setTextColor(context.getResources().getColor(R.color.theme_color_dark));
                totalTime.setTextColor(context.getResources().getColor(R.color.theme_color_dark));
                System.out.println("Yellow");
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return dataModels.size();
    }
}
