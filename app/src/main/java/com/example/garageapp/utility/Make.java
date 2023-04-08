package com.example.garageapp.utility;

public class Make {

    private int makeId;
    private String makeName;

    public Make(int makeId, String makeName) {
        this.makeId = makeId;
        this.makeName = makeName;
    }

    public int getMakeId() {
        return makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    @Override
    public String toString() {
        return makeName;
    }
}
