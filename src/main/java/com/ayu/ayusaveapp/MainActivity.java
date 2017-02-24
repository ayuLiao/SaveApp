package com.ayu.ayusaveapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ayuLiao", "btnClick is click");
                // jobId每个Job任务的id
                int jobId = 1;
                // 指定你需要执行的JobService
                ComponentName name = new ComponentName(getPackageName(), SaveService.class.getName());

                /**
                 *  满足下面任意一个条件，就会执行JobService中onStartJob()方法中的代码
                 */
                //设置一些条件
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //JobScheduler是Job的调度类，负责执行，取消任务等逻辑，具体看下JobScheduler的获取和类代码。
                    JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    JobInfo jobInfo = new JobInfo.Builder(jobId, name)
                            .setPeriodic(1000)//设置间隔时间
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//设置需要的网络条件，默认NETWORK_TYPE_NONE
//                            .setMinimumLatency(2000)// 设置任务运行最少延迟时间
//                            .setOverrideDeadline(50000)// 设置deadline，若到期还没有达到规定的条件则会开始执行
                            .setRequiresDeviceIdle(false)// 设置手机是否空闲的条件,默认false
                            .setPersisted(true)//设备重启之后你的任务是否还要继续执行
                            .build();
                    jobScheduler.schedule(jobInfo);
                }
            }
        });
    }
}
