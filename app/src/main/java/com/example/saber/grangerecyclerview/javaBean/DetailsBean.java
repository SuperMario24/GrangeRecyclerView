package com.example.saber.grangerecyclerview.javaBean;

import java.io.Serializable;

public class DetailsBean implements Serializable {
    private String name;
    private String tag;
    private boolean isTitle;

    public DetailsBean(String name) {
        this.name = name;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }







}