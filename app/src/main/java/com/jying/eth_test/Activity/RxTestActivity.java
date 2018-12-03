package com.jying.eth_test.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.jying.eth_test.Base.RxBus;
import com.jying.eth_test.Bean.MsgEvent;
import com.jying.eth_test.Bean.Person;
import com.jying.eth_test.Bean.Plan;
import com.jying.eth_test.Fragment.RxTestFragment;
import com.jying.eth_test.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxTestActivity extends RxAppCompatActivity {

    public static final String TAG = "===DEBUG===";
    @BindView(R.id.test)
    Button bt_test;
    @BindView(R.id.rx_framelayout)
    FrameLayout frameLayout;
    FragmentManager fragmentManager;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.rx_framelayout, new RxTestFragment());
        transaction.commit();

        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        List<String> stringList1 = new ArrayList<>();
        stringList1.add("a");
        stringList1.add("b");
        List<Plan> planList = new ArrayList<>();
        planList.add(new Plan("plan1", stringList1));
        planList.add(new Plan("plan2", stringList));

        Person person = new Person("person1", planList);
        Observable.fromIterable(person.getPlanList())
                .concatMap(new Function<Plan, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Plan plan) throws Exception {
                        if (plan.getKey().equals("plan1")) {
                            return Observable.fromIterable(plan.getList()).delay(2, TimeUnit.SECONDS);
                        }
                        return Observable.fromIterable(plan.getList());
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, s);
            }
        });

    }

    @OnClick({R.id.test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test:
//                RxBus.getInstance().postSticky(new MsgEvent("1", "test1"));
//                RxBus.getInstance().postSticky(new MsgEvent("3", "test3"));
//                RxBus.getInstance().postSticky(new MsgEvent("4", "test4"));
                System.out.println("a");
                break;
        }
    }
}
