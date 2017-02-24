package com.ayu.ayusaveapp;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

//在android5.0后才有JobService
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SaveService extends JobService {
    /**
     * Callback则是由工作线程内部传出接收到的消息的回调接口，其他线程通过Handler的sendMessage
     * 发送消息给工作线程后，工作线程就会通过Callback将接收到的消息通知给监听者。
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            JobParameters param = (JobParameters) msg.obj;
            jobFinished(param, true);//任务执行完后记得调用jobFinsih通知系统释放相关资源
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //在新的栈中打开
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
    });

    /**
     *需要重写，开始jobScheduler的方法
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("ayuLiao", "onStartJob is running");
        Message msg = Message.obtain();
        msg.obj = params;
        handler.sendMessage(msg);
        return true;
    }

    /**
     * 停止JobScheduler的方法
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        //清除回调线程和消息
        handler.removeCallbacksAndMessages(null);
        return false;
    }


}
