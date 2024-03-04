package com.example.campusrider.utils;

public class Constant {
    public static String API_BASE_URL = "http://campusriderbd.com/Customer/rider/";
    public static String GET_LOCATION_URL = API_BASE_URL + "view_location.php";
    public static String LOGIN_URL = API_BASE_URL + "login.php";
    public static String IMAGE_URL = "http://campusriderbd.com/media/";
    public static String Registration_URL = API_BASE_URL + "registration.php";
    public static String GET_ORDER_STATUS_URL = API_BASE_URL + "status.php?id=";
    public static String GET_FOOD_ORDER_DETAILS_URL = API_BASE_URL + "order_details.php?type=food&order_id=";
    public static String GET_GROCERY_ORDER_DETAILS_URL = API_BASE_URL + "order_details.php?type=grocery&order_id=";
    public static String UPDATE_FOOD_ORDER_URL = API_BASE_URL + "cangestatus.php?type=food&order_id=";
    public static String UPDATE_GROCERY_ORDER_URL = API_BASE_URL + "cangestatus.php?type=grocery&order_id=";
}
