package com.example.tester;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends Activity {

    ActivityManager mActivityManager;
    ImageView im1;
    private static final int TAKE_IMAGE = 1;
    private static final int SELECTED_PICTURE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // new code here
        initViews();
        im1 = (ImageView) findViewById(R.id.imageView1);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Social.class);
                startActivity(intent);
                overridePendingTransition(R.animator.wheel_spin, R.animator.animation2);
            }
        });

        im1 = (ImageView) findViewById(R.id.imageView2);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Productive.class);
                startActivity(intent);
                overridePendingTransition(R.animator.wheel_spin, R.animator.animation2);
            }
        });
        im1 = (ImageView) findViewById(R.id.imageView3);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Social.class);
                startActivity(intent);
                overridePendingTransition(R.animator.wheel_spin, R.animator.animation2);
            }
        });
        im1 = (ImageView) findViewById(R.id.imageView4);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Productive.class);
                startActivity(intent);
                overridePendingTransition(R.animator.wheel_spin, R.animator.animation2);
            }
        });

        im1 = (ImageView) findViewById(R.id.imageView5);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_IMAGE);

            }

        });

        im1 = (ImageView) findViewById(R.id.imageView6);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent,SELECTED_PICTURE);
//                Intent intent = new Intent(v.getContext(), Gallery.class);
//                startActivity(intent);
//                overridePendingTransition(R.animator.wheel_spin,R.animator.animation2);
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECTED_PICTURE);
            }

        });

        //


      /*  mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = mActivityManager.getRunningAppProcesses();

        String[] activePackages;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activePackages = getActivePackages();
        } else {
            activePackages = getActivePackagesCompat();
        }
        if (activePackages != null) {
            for (String activePackage : activePackages) {
                if (activePackage.equals("com.google.android.calendar")) {
                    //Calendar app is launched, do something
                }
            }
        }
*/
        startService(new Intent(this, AppService.class));

        if (getIntent().getStringExtra("bitmap") == null) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

    }

    RelativeLayout parentView;

    private void initViews() {
        parentView = (RelativeLayout) findViewById(R.id.mylayout);
    }


//new code here//--------------------------------------------------------------------------------------------------

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE && resultCode == RESULT_OK) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            // im1.setImageBitmap(bp);
            Drawable d = new BitmapDrawable(getResources(), bp);
            parentView.setBackground(d);

        }

        if (requestCode == TAKE_IMAGE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "You have not captured image.", Toast.LENGTH_SHORT).show();

        }
        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "You have not selected image.", Toast.LENGTH_SHORT).show();

        }
        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String projection[] = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filepath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bmp = BitmapFactory.decodeFile(filepath);
            Drawable d = new BitmapDrawable(getResources(), bmp);
            parentView.setBackground(d);
        }


    }

    // new code till here//--------------------------------------------------------------------------------------------------

    /*   String[] getActivePackagesCompat() {
           final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
           final ComponentName componentName = taskInfo.get(0).topActivity;
           final String[] activePackages = new String[1];
           activePackages[0] = componentName.getPackageName();
           Log.e("active compat",activePackages[0]+"");
           return activePackages;
       }
   */
   /* String[] getActivePackages() {
        final Set<String> activePackages = new HashSet<String>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
                Log.e("active",processInfo.pkgList+"");
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }
*/
   /* public void startProgress(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=0; i++) {
                    final int value = i;
                    doFakeWork();
                    progress.post(new Runnable() {
                        @Override
                        public void run() {
                            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();
                            for (int i = 0; i < runningAppProcessInfo.size(); i++) {
                                Log.d("hhh",runningAppProcessInfo.get(i).processName);

                                //app[var++]=runningAppProcessInfo.get(i).processName.toString();

                            }
                            text.setText("Updating");
                            progress.setProgress(value);


                          /*  ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                            for (ActivityManager.RunningTaskInfo rsi : am.getRunningTasks(Integer.MAX_VALUE)){
                                Log.d("hhh",rsi.description);

                                //app[var++]=runningAppProcessInfo.get(i).processName.toString();

                            }
                            text.setText("Updating");
                            progress.setProgress(value);

                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    private void doFakeWork() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
