package com.project.models;

import android.location.Location;

public class DeviceAppearance {
    Long firstAppearance;
    Long lastAppearance;
    String macAddress;
    Double longitude;
    Double latitude;

    public DeviceAppearance(Long firstAppearance, String macAddress) {
        this.firstAppearance = firstAppearance;
        this.macAddress = macAddress;
    }

    public Long getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(Long firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public Long getLastAppearance() {
        return lastAppearance;
    }

    public void setLastAppearance(Long lastAppearance) {
        this.lastAppearance = lastAppearance;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "DeviceAppearance{" +
                "firstAppearance=" + firstAppearance +
                ", lastAppearance=" + lastAppearance +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
