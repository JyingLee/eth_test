package com.jying.eth_test.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jying.eth_test.Base.RxBus;
import com.jying.eth_test.Bean.MsgEvent;
import com.jying.eth_test.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class RxTestFragment extends RxFragment {

    @BindView(R.id.tv_hello)
    TextView tv;
    @BindView(R.id.rx_ed)
    EditText ed;
    public static final String tag = "===debug===";

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        RxBus.getInstance().tObservableSticky(this, MsgEvent.class).subscribe(new Consumer<MsgEvent>() {
            @Override
            public void accept(MsgEvent msgEvent) throws Exception {
                tv.setText(msgEvent.getMsg());
            }
        });

        RxTextView.textChanges(ed).debounce(1, TimeUnit.SECONDS).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                Log.e(tag, "" + charSequence);
            }
        });
        return view;
    }
}
