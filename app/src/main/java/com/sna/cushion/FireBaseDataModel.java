package com.sna.cushion;

public class FireBaseDataModel {

    private String date;
    private String occupyTime;
    private String releasingTime;
    private String totalTime;

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public String getOccupyTime() {
        return occupyTime;
    }

    public void setOccupyTime( String occupyTime ) {
        this.occupyTime = occupyTime;
    }

    public String getReleasingTime() {
        return releasingTime;
    }

    public void setReleasingTime( String releasingTime ) {
        this.releasingTime = releasingTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime( String totalTime ) {
        this.totalTime = totalTime;
    }

    public FireBaseDataModel( String date, String occupyTime, String releasingTime, String totalTime ) {
        this.date = date;
        this.occupyTime = occupyTime;
        this.releasingTime = releasingTime;
        this.totalTime = totalTime;
    }
}
