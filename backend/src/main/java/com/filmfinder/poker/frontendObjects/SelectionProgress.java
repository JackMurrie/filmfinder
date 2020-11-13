package com.filmfinder.poker.frontendObjects;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;

public class SelectionProgress extends frontendObject {
    @Expose
    private Integer command = 1;
    @Expose
    private ArrayList<String> done = new ArrayList<String>();
    @Expose
    private ArrayList<String> stillChoosing = new ArrayList<String>();

    public SelectionProgress() {

    }

    public void addDone(String nickname) {
        done.add(nickname);
    }

    public void addStillChoosing(String nickname) {
        stillChoosing.add(nickname);
    }
}
