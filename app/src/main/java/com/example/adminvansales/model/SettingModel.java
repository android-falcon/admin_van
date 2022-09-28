package com.example.adminvansales.model;

public class SettingModel {

    private String ipAddress;
    private String port;

      String import_way;
    String Cono;
int Plan_Type;

    private  int  locationtracker;

    public int getPlan_Type() {
        return Plan_Type;
    }

    public void setPlan_Type(int plan_Type) {
        Plan_Type = plan_Type;
    }

    public int getLocationtracker() {
        return locationtracker;
    }

    public void setLocationtracker(int locationtracker) {
        this.locationtracker = locationtracker;
    }

    public String getCono() {
        return Cono;
    }

    public void setCono(String cono) {
        Cono = cono;
    }

    public SettingModel() {

    }

    public SettingModel(String ipAddress, String port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getImport_way() {
        return import_way;
    }

    public void setImport_way(String import_way) {
        this.import_way = import_way;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
