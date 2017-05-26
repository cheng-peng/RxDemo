package com.cxp.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

/**
 * 文 件 名: MainFragment
 * 创 建 人: CXP
 * 创建日期: 2017-05-25 11:14
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class MainFragment extends Fragment {

    private TextView tv;
    private Button bt;

    private String[] str = new String[]{"1", "2", "3", "4", "5", "6"};
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tv = (TextView) view.findViewById(R.id.fragment_main_tv);
        bt = (Button) view.findViewById(R.id.fragment_main_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index==6) {
                    index=0;
                }
                RxBus.get().post("main", str[index]);
                index++;
            }
        });
        RxBus.get().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.get().unregister(this);
    }

    @Subscribe(
            //  如果是RxBus.get().post("list") 这种方式 ，这里要使用EventThread.MAINTHREAD
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("fragment")
            }
    )
    public void fragment(String str) {
        tv.setText(str);
    }
}
