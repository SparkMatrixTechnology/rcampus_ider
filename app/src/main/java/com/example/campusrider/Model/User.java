package com.example.campusrider.Model;

public class User {
    int rider_id;
    String rider_name;
    int rider_phone;
    String rider_email;
    String area,rider_password,rider_image;
    int rider_status;

    public User(int rider_id, String rider_name, int rider_phone, String rider_email, String area, String rider_password, String rider_image, int rider_status) {
        this.rider_id = rider_id;
        this.rider_name = rider_name;
        this.rider_phone = rider_phone;
        this.rider_email = rider_email;
        this.area = area;
        this.rider_password = rider_password;
        this.rider_image = rider_image;
        this.rider_status = rider_status;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public int getRider_phone() {
        return rider_phone;
    }

    public void setRider_phone(int rider_phone) {
        this.rider_phone = rider_phone;
    }

    public String getRider_email() {
        return rider_email;
    }

    public void setRider_email(String rider_email) {
        this.rider_email = rider_email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRider_password() {
        return rider_password;
    }

    public void setRider_password(String rider_password) {
        this.rider_password = rider_password;
    }

    public String getRider_image() {
        return rider_image;
    }

    public void setRider_image(String rider_image) {
        this.rider_image = rider_image;
    }

    public int getRider_status() {
        return rider_status;
    }

    public void setRider_status(int rider_status) {
        this.rider_status = rider_status;
    }
}
