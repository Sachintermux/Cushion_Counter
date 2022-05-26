package com.sna.cushion;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class Main_ViewModel extends ViewModel {

    MutableLiveData<String> currentState = new MutableLiveData<>("0");
    MutableLiveData<ArrayList<FireBaseDataModel>> fireDataList = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<String> ssid = new MutableLiveData<>();
    MutableLiveData<String> password = new MutableLiveData<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Chair");

    public void getData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                currentState.setValue(snapshot.child("State").getValue().toString());
                ssid.setValue(Objects.requireNonNull(snapshot.child("SSID").getValue()).toString());
                password.setValue(Objects.requireNonNull(snapshot.child("Password").getValue()).toString());
                ArrayList<FireBaseDataModel> arrayList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    try {
                        if (snapshot1.child("Date").getValue() != null && snapshot1.child("Start_Time").getValue() != null) {
                            String date = snapshot1.child("Date").getValue().toString();
                            String startTime = snapshot1.child("Start_Time").getValue().toString();
                            String endTime = snapshot1.child("Stop_Time").getValue().toString();
                            String difference = snapshot1.child("Diffrence").getValue().toString();
                            FireBaseDataModel fireBaseDataModel = new FireBaseDataModel(date, startTime, endTime, difference);
                            arrayList.add(fireBaseDataModel);
                        }
                    } catch (Exception e) {

                    }
                }
                Collections.sort(arrayList, new Comparator<FireBaseDataModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public int compare( FireBaseDataModel t1, FireBaseDataModel  fireBaseDataModel) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss", Locale.ENGLISH);
                            return LocalDate.parse(t1.getDate() +"-" +t1.getOccupyTime(), formatter)
                                    .compareTo(LocalDate.parse(fireBaseDataModel.getDate()+"-"+fireBaseDataModel.getOccupyTime(), formatter));
                        } catch (DateTimeParseException e) {
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy-HH:mm:ss", Locale.getDefault());
                                return LocalDate.parse(t1.getDate() +"-" +t1.getOccupyTime(), formatter)
                                        .compareTo(LocalDate.parse(fireBaseDataModel.getDate()+"-"+fireBaseDataModel.getOccupyTime(), formatter));
                            } catch (DateTimeParseException e1) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy-HH:mm:ss", Locale.ENGLISH);
                                return LocalDate.parse(t1.getDate() +"-" +t1.getOccupyTime(), formatter)
                                        .compareTo(LocalDate.parse(fireBaseDataModel.getDate()+"-"+fireBaseDataModel.getOccupyTime(), formatter));
                            }
                        }
                    }
                });
                Collections.reverse(arrayList);
                fireDataList.setValue(arrayList);
            }

            @Override
            public void onCancelled( @NonNull DatabaseError error ) {

            }
        });
    }
}
