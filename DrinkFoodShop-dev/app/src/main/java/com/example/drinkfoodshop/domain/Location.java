package com.example.drinkfoodshop.domain;

public class Location {
    public int Id;
    public String Loc;
    public Location(){
    }
    @Override
    public String toString(){
        return Loc;
    }
    public int getId(){
        return Id;
    }
    public void setId(int id){
        Id = id;
    }
    public String getLoc(){
        return Loc;
    }
    public void setLoc(String loc){
        Loc = loc;
    }
}
