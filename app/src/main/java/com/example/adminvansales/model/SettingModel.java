package com.example.adminvansales.model;

public class SettingModel {

    private String ipAddress;
    private String port;


    public SettingModel() {

    }

    public SettingModel(String ipAddress, String port) {
        this.ipAddress = ipAddress;
        this.port = port;
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
