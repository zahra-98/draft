package com.example.isustain_app.models;

public class points {

    int user_points;
    String user_name, user_id;

    public points(){}

    public points(int user_points, String user_name, String user_id){

        this.user_points=user_points;
        this.user_name=user_name;
        this.user_id=user_id;
    }

    public int getUser_points() {
        return user_points;
    }

    public void setUser_points(int user_points) {
        this.user_points = user_points;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
