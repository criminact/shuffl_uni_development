package com.shuffl.niched.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    public String title;
    public String description;
    public int rentalPriceDay;
    public int rentalPriceHour;
    public int rentalPriceWeek;
    public int rentalPriceMonth;
    public List<String> images;
    public String providedBy;
}
