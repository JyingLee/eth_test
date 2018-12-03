package com.jying.eth_test.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jying.eth_test.R;
import com.jying.eth_test.View.FloatView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class XingqiuActivity extends RxAppCompatActivity {

    @BindView(R.id.float_view)
    FloatView floatView;
    @BindView(R.id.xingqiu_lottery)
    Button bt_lottery;
    @BindView(R.id.xingqiu_aoke)
    TextView tv_aoke;
    @BindView(R.id.xingqiu_yuanli)
    TextView tv_yuanli;
    public static final String TAG = "===DEBUG===";
    private float sum = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xingqiu);
        ButterKnife.bind(this);
        setAK();

        RxView.clicks(bt_lottery)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(XingqiuActivity.this, LotteryActivity.class));
                    }
                });
    }

    private void setAK() {
        List<Float> list = new ArrayList<>();
        list.add((float) 3.125);
        list.add((float) 0.125);
        list.add((float) 1.264);
        list.add((float) 6.158);
        list.add((float) 1.264);
        floatView.setList(list);

        floatView.setOnBallClickListener(new FloatView.ballClickListaner() {
            @Override
            public void itemClick(int position, Number value) {
                sum = sum + (float) value;
                tv_aoke.setText("奥克：" + sum);
            }
        });
    }

}
