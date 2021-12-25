package com.ndnt.nhattan_projecttonghop;

public class Song
{
    private String ms,tenbh;
    private int thich;

    public Song(String ms, String tenbh, int thich) {
        this.ms = ms;
        this.tenbh = tenbh;
        this.thich = thich;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getTenbh() {
        return tenbh;
    }

    public void setTenbh(String tenbh) {
        this.tenbh = tenbh;
    }

    public int getThich() {
        return thich;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }
}
