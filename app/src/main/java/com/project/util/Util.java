package com.project.util;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.FormulaireActivity;
import com.project.HomeActivity;
import com.project.MainActivity;
import com.project.MapsActivity;
import com.project.MapsActivity2;
import com.project.models.DeviceAppearance;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Util {

    private static FirebaseFirestore mFirestore;
    private boolean test=false;
    public static String MacAdd;
    public static void updateUi(final AppCompatActivity context, FirebaseUser user){
       /* if(user != null){
            Intent intent = new Intent(context, HomeActivity.class);
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
                        MacAdd =documentSnapshot.get("macAddress").toString();
                    }
                    else{
                        Intent intent = new Intent(context, FormulaireActivity.class);
                        context.startActivity(intent);
                        MacAdd =documentSnapshot.get("macAddress").toString();
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
                    //kheliha 2 hhhhhh kherbeqtu lina l7sab
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

    //Pass context and latLng to fucntion to open map to the location of contact
    public static void openMap(Context context, LatLng latLng,long firstContact,long lastContact){
        long interv=(long)(lastContact-firstContact)/60;
        Intent intent=new Intent(context, MapsActivity2.class);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(firstContact * 1L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        Log.d("pezones",date);
        intent.putExtra("latitude",latLng.latitude);
        intent.putExtra("longitude",latLng.longitude);
        intent.putExtra("interval",interv);
        intent.putExtra("firstContact",date);//timestamp
        Log.d("failaa","Inside Open Map");
        context.startActivity(intent);
    }

}
