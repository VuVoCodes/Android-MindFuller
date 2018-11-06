package org.rmit.mindfulapp;

public class Excercise {
    public int id;
    public String sname;
    public int duration;
    public String status;

    public Excercise(int id, String sname, int duration, String status) {
        this.id = id;
        this.sname = sname;
        this.duration = duration;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

