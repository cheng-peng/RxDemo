package com.cxp.rxbus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private FrameLayout fl;
    private TextView tv;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.main_fl);
        tv = (TextView) findViewById(R.id.main_tv);

        mContext = this;
        RxBus.get().register(this);

        mMainFragment=new MainFragment();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.main_fl, mMainFragment);
        ft.commit();

    }

    //单击事件
    public void clickLis(View view) {
        FristActivity.startActivity(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Produce
    public String test() {
        return "么么哒！";
    }

    @Produce(
            thread = EventThread.IO,
            tags = {
                    @Tag("list")
            }
    )
    public ArrayList<String> list() {
        ArrayList<String> list = new ArrayList<>();
        list.add("程");
        list.add("小");
        list.add("鹏");
        list.add("。");
        return list;
    }

    @Produce(
            thread = EventThread.IO,
            tags = {
                    @Tag("fragment")
            }
    )
    public String tv() {
        return "这是个Fragment";
    }


    @Subscribe(
            //线程要一致  如果是RxBus.get().post("list") 这种方式 ，这里要使用EventThread.MAINTHREAD
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("main")
            }
    )
    public void main(String str) {
        tv.setText(str);
    }

}
