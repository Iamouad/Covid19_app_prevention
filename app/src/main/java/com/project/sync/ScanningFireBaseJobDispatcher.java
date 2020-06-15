package com.project.sync;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class ScanningFireBaseJobDispatcher extends JobService {
    String TAG = "ScanningFireBaseJobDispatcher";
    AsyncTask<Void, Void, Boolean> mAsyncTask;
    BluetoothAdapter mBluetoothAdapter;
    BroadcastReceiver mBroadcastReceiver3;
    boolean result;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {
        ////////////////////
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        result = false;

/////////////////////////////

         mAsyncTask = new AsyncTask<Void, Void, Boolean>() {
             @Override
             protected Boolean doInBackground(Void... voids) {
                 mBroadcastReceiver3 = new BroadcastReceiver() {
                     @Override
                     public void onReceive(Context context, Intent intent) {
                         final String action = intent.getAction();
                         Log.d(TAG, "onReceive: ACTION FOUND.");
                         if (action.equals(BluetoothDevice.ACTION_FOUND)){
                             BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                             ReminderUtilities.addDevice(device, ScanningFireBaseJobDispatcher.this);
                             Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                             result = true;
                         }
                     }
                 };

                   Log.d(TAG, "doInBackground: Looking for unpaired devices.");

                 if(mBluetoothAdapter.isDiscovering()){
                     mBluetoothAdapter.cancelDiscovery();
                     Log.d(TAG, "btnDiscover: Canceling discovery.");
                     IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                     mBluetoothAdapter.startDiscovery();
                     registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);

                 }
                 if(!mBluetoothAdapter.isDiscovering()){
                     mBluetoothAdapter.startDiscovery();
                     IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                     registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);

                 }

                 try {
                     System.out.println("sleeping.....");
                     Thread.sleep(1000*20);
                     unregisterReceiver(mBroadcastReceiver3);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

                 return result;
             }
             @Override
             protected void onPostExecute(Boolean aBoolean) {
                 Log.d(TAG, "onPostExecute: ..................");
                 if(aBoolean){
                     Log.d(TAG, "onPostExecute: found");
                 }
                 else {
                     Log.d(TAG, "onPostExecute: not found");
                 }
                 ReminderUtilities.showStackDevices();
                 jobFinished(job, false);
             }
         };
        mAsyncTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob: ");
        if (mAsyncTask != null)
            mAsyncTask.cancel(true);
        return true;
    }

}
