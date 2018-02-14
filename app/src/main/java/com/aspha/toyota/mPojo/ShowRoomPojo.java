package com.aspha.toyota.mPojo;

/**
 * Created by Kajiva Kinsley on 2/7/2018.
 */

public class ShowRoomPojo {
    private String carName   , price;
    private int img;

    public ShowRoomPojo (String carName, int img, String price) {
        this.carName = carName;
        this.img = img;
        this.price = price;
    }

    public String getCarName () {
        return carName;
    }

    public int getImg () {
        return img;
    }

    public String getPrice () {
        return price;
    }
}
