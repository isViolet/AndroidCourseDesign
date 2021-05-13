package com.example.networkrequest;

import java.util.List;

public class Data
{
    private String ID;

    private String TITLE;

    private String SUBTITLE;

    private String IMAGEURL;

    private String FROMNAME;

    private String SHOWTIME;

    private int RN;

    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
    }
    public void setTITLE(String TITLE){
        this.TITLE = TITLE;
    }
    public String getTITLE(){
        return this.TITLE;
    }
    public void setSUBTITLE(String SUBTITLE){
        this.SUBTITLE = SUBTITLE;
    }
    public String getSUBTITLE(){
        return this.SUBTITLE;
    }
    public void setIMAGEURL(String IMAGEURL){
        this.IMAGEURL = IMAGEURL;
    }
    public String getIMAGEURL(){
        return this.IMAGEURL;
    }
    public void setFROMNAME(String FROMNAME){
        this.FROMNAME = FROMNAME;
    }
    public String getFROMNAME(){
        return this.FROMNAME;
    }
    public void setSHOWTIME(String SHOWTIME){
        this.SHOWTIME = SHOWTIME;
    }
    public String getSHOWTIME(){
        return this.SHOWTIME;
    }
    public void setRN(int RN){
        this.RN = RN;
    }
    public int getRN(){
        return this.RN;
    }
}