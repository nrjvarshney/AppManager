package com.example.tester;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class AppService extends Service {
    Handler mHandler;
    Runnable mRunnable;
    long millis;
    long millisend;
    Button b;
    EditText mess;

    DBHelper db = new DBHelper(this);
    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        millis = System.currentTimeMillis() / 1000;

        mHandler=new Handler();
        mRunnable=new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(mRunnable,1000);
                getTopActivtyFromLolipopOnwards();
            }
        };
        mRunnable.run();



        return super.onStartCommand(intent, flags, startId);
    }


    public void getTopActivtyFromLolipopOnwards(){
        String topPackageName ;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            // We get usage stats for the last 1 seconds
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time -Integer.MAX_VALUE, time);
            // Sort the stats by the last time used
            if(stats != null) {
                SortedMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                }
                if(mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.e("TopPackage Name",topPackageName);
                    //new code here

                 //   db.addContact(new Contact(topPackageName.toString(),0));
                    List<Contact> contacts = db.getAllContacts();
                        int flag=0;
                    for (Contact cn : contacts) {
                        String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,TIMES: " +
                                cn.getTimes()+"\n";
                        //System.out.println(log);
                        if(cn.getName().equals(topPackageName.toString()))
                        {
                            flag=1;
                            db.updateContact(new Contact(cn.getID(),topPackageName.toString(),cn.getTimes()+1));
                        }
                        Log.e("database",log);
                    }
                    if(flag==0)
                        db.addContact(new Contact(topPackageName.toString(),0));

//new code till here

                }else{
                    topPackageName=null;
                }
            }
        }
        else
        {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();
            for (int i = 0; i < runningAppProcessInfo.size(); i++) {
                Log.d("hhh",runningAppProcessInfo.get(i).processName);


            }

        }

    }
}
