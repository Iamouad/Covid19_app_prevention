package com.project.util;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.project.FormulaireActivity;
import com.project.MainActivity;
import com.project.models.DeviceAppearance;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Util {
    public static void updateUi(AppCompatActivity context, FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    // res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 2);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }

    public static boolean containsDev(List<DeviceAppearance> devices, String macAddr){
        if(devices.size() == 0)
            return false;
        for (DeviceAppearance dev:devices
             ) {
            if(dev.getMacAddress().equals(macAddr))
                return true;
        }
        return false;
    }

    public static DeviceAppearance getDevice(List<DeviceAppearance> devices,String macAddr){

        for (DeviceAppearance dev:devices
        ) {
            if(dev.getMacAddress().equals(macAddr))
                return dev;
        }
        return null;
    }

}
