package com.cxp.rxbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;

/**
 * 文 件 名: FristActivity
 * 创 建 人: CXP
 * 创建日期: 2017-05-25 10:28
 * 描    述: 第一个页面
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class SecondActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv = (TextView) findViewById(R.id.second_tv);
        RxBus.get().register(this);
    }

    //单击事件
    public void clickLis(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe(
            //  如果是RxBus.get().post("list") 这种方式 ，这里要使用EventThread.MAINTHREAD
            thread = EventThread.IO,
            tags = {
                    @Tag("list")
            }
    )
    public void test(ArrayList<String> list) {
        String str="";
        for (String s : list) {
            str+=s;
        }
        tv.setText(str);
    }

    //页面跳转
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }
}
