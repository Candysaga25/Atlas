package com.atlas.models;

import java.io.Serializable;

/**
 * Created by Sagar Shedge on 13/6/16.
 */
public class Map implements Serializable{
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmCategoryId() {
        return mCategoryId;
    }

    public void setmCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getMbron() {
        return mbron;
    }

    public void setMbron(String mbron) {
        this.mbron = mbron;
    }

    private int mId;
    private int mCategoryId;
    private String mName;
    private String mTitle;
    private String mImage;
    private String mbron;



}
