package com.jjcandyjt.trainticketssys.Domain;


import java.util.List;

public class Train {
    private String trainid;
    private int airid;
    private List seatlist;
    private List spotlist;
    public Train(){
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainid='" + trainid + '\'' +
                ", airid=" + airid +
                ", seatlist=" + seatlist +
                ", spotlist=" + spotlist +
                '}';
    }

    public String getTrainid() {
        return trainid;
    }

    public void setTrainid(String trainid) {
        this.trainid = trainid;
    }

    public int getAirid() {
        return airid;
    }

    public void setAirid(int airid) {
        this.airid = airid;
    }

    public List getSeatlist() {
        return seatlist;
    }

    public void setSeatlist(List seatlist) {
        this.seatlist = seatlist;
    }

    public List getSpotlist() {
        return spotlist;
    }

    public void setSpotlist(List spotlist) {
        this.spotlist = spotlist;
    }
}
