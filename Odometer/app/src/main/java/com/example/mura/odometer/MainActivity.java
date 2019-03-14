package com.example.mura.odometer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

    private OdometerService odometer;
    private boolean bound = false;
    private final int PERMISSION_REQUEST_CODE = 453;
    private final int NOTIFICATION_ID = 432;


    private ServiceConnection connection = new  ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName companentName, IBinder binder){
            OdometerService.OdometerBinder odometerBinder = (OdometerService.OdometerBinder) binder;
            odometer = odometerBinder.getOdometer();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName){
            bound = false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayDistance();
    }

    private void displayDistance() {
        final TextView distanceView = (TextView) findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if(bound && odometer !=null){
                    distance = odometer.getDistance();
                }
                String distanceStr = String.format(Locale.getDefault(),"%1$,.2f miles",distance);
                distanceView.setText(distanceStr);
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected  void onStart(){
        super.onStart();

        if(ContextCompat.checkSelfPermission(this,OdometerService.PERMISSION_STRING)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{OdometerService.PERMISSION_STRING},
                    PERMISSION_REQUEST_CODE);
        }else{
            Intent intent = new Intent(this,OdometerService.class);
            bindService(intent,connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResult){

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:{
                if (grantResult.length > 0  && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(this,OdometerService.class);
                    bindService(intent,connection,Context.BIND_AUTO_CREATE);
                }else{
                    //оповещение
                    NotificationCompat.Builder  builder = new NotificationCompat.Builder(this)
                            .setSmallIcon(android.R.drawable.ic_menu_compass)
                            .setContentTitle(getResources().getString(R.string.app_name))
                            .setContentText(getResources().getString(R.string.permission_denied))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[] {1000,1000})
                            .setAutoCancel(true);
                    Intent actionIntent = new Intent(this,MainActivity.class);
                    //уведомление
                    PendingIntent actionPendingIntent = PendingIntent.getActivity(
                            this,
                            0,
                            actionIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(actionPendingIntent);

                    //выдача оповещения
                    NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(NOTIFICATION_ID,builder.build());
                }
            }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(bound){
            unbindService(connection);
            bound = false;
        }
    }
}
