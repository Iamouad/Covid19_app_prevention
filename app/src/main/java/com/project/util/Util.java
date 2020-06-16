package com.project.util;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.FormulaireActivity;
import com.project.HomeActivity;
import com.project.MainActivity;
import com.project.models.DeviceAppearance;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Util {

    private static FirebaseFirestore mFirestore;
    private boolean test=false;
    public static void updateUi(final AppCompatActivity context, FirebaseUser user){
        /*if(user != null){
            Intent intent = new Intent(context, FormulaireActivity.class);
>>>>>>> d30ef70308b363cb4667ccb762010e939564d638
            context.startActivity(intent);
        }*/
        if(user != null){
            mFirestore= FirebaseFirestore.getInstance();
            mFirestore.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    boolean tested= (boolean) documentSnapshot.get("tested");
                    if(tested){
                        Log.d("testFirebase","lol");
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, FormulaireActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
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
                    //raha kanate 2 darnaha 3
                    res1.deleteCharAt(res1.length() - 3);
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
