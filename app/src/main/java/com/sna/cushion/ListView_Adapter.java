package com.sna.cushion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListView_Adapter extends ArrayAdapter<String> {
    ArrayList<FireBaseDataModel> dataModels = new ArrayList<>();
    public ListView_Adapter( @NonNull Context context, int resource, ArrayList<FireBaseDataModel> fireBaseDataModels ) {
        super(context, resource);
        this.dataModels = fireBaseDataModels;

    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        if(convertView==null){
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

        return convertView;
    }

    @Override
    public int getCount() {
        return dataModels.size();
    }
}
