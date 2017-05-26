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

/**
 * 文 件 名: FristActivity
 * 创 建 人: CXP
 * 创建日期: 2017-05-25 10:28
 * 描    述: 第一个页面
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class FristActivity extends AppCompatActivity {

    private Context mContext;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        tv = (TextView) findViewById(R.id.first_tv);
        mContext = this;
        RxBus.get().register(this);
    }

    //单击事件
    public void clickLis(View view) {
        SecondActivity.startActivity(mContext);
        finish();
    }

    @Subscribe
    public void test(String str) {
        tv.setText(str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    //页面跳转
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FristActivity.class);
        context.startActivity(intent);
    }
}
