package com.atlas.models;

/**
 * Created by Sagar Shedge on 14/6/16.
 */
public class MapEvent {
    public MapEvent(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private Map map;

}
