package com.example.myapplication;

public class Loc {
    private String l1;
    private String l2;
    private String loc_name;
    private int loc_cap;
    private double loc_lon;
    private double loc_lat;

    // Getter 메서드
    public String getL1() {
        return l1;
    }

    public String getL2() {
        return l2;
    }

    public String getLocName() {
        return loc_name;
    }

    public int getLocCap() {
        return loc_cap;
    }

    public double getLocLon() {
        return loc_lon;
    }

    public double getLocLat() {
        return loc_lat;
    }

    // Setter 메서드
    public void setL1(String l1) {
        this.l1 = l1;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public void setLocName(String loc_name) {
        this.loc_name = loc_name;
    }

    public void setLocCap(int loc_cap) {
        this.loc_cap = loc_cap;
    }

    public void setLocLon(double loc_lon) {
        this.loc_lon = loc_lon;
    }

    public void setLocLat(double loc_lat) {
        this.loc_lat = loc_lat;
    }

    // toString 메서드 (선택적)
    @Override
    public String toString() {
        return "Loc{" +
                "l1='" + l1 + '\'' +
                ", l2='" + l2 + '\'' +
                ", loc_name='" + loc_name + '\'' +
                ", loc_cap=" + loc_cap +
                ", loc_lon=" + loc_lon +
                ", loc_lat=" + loc_lat +
                '}';
    }
}
