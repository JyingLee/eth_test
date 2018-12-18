package com.jying.eth_test.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jying.eth_test.Adapter.RecordAdapter;
import com.jying.eth_test.Base.BaseActivity;
import com.jying.eth_test.Bean.RecordBean;
import com.jying.eth_test.Contracts.AokeToken2_sol_AokeToken2;
import com.jying.eth_test.R;
import com.jying.eth_test.Token.ContractManager;
import com.jying.eth_test.View.FloatView;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XingqiuActivity extends BaseActivity {

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
    @BindView(R.id.xingqiu_lottery2)
    Button bt_lottery2;
    AokeToken2_sol_AokeToken2 aokeToken2;
    private String cutterUser;
    private boolean isCosting = false;
    private float notifyNum = 0;
    @BindView(R.id.xingqiu_cutter)
    TextView tv_cutterUser;
    @BindView(R.id.xingqiu_pro)
    ProgressBar pro;
    @BindView(R.id.xingqiu_recyclerview)
    RecyclerView recyclerView;
    private RecordAdapter recordAdapter;
    private List<RecordBean> lists = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xingqiu);
        ButterKnife.bind(this);
        initRecyclerView();
        aokeToken2 = ContractManager.getInstance().getAokeToken2();
        getCutterUser();//获取当前用户
        getRecordList();//获取消耗记录
        setAK();//设置产出奥克
        pullAoke(cutterUser);//从链拉取当前用户奥克
        floatView.setDefaultViewListener(new FloatView.DefaultViewListener() {
            @Override
            public void click(View view) {
                if (!isCosting) {
                    if (notifyNum != 0) {
                        getAoke();
                    } else {
                        showToast("奥克没有上涨~");
                    }
                }
            }
        });
    }

    private void getRecordList() {
        if (lists == null || aokeToken2 == null || recordAdapter == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lists.clear();
                    Tuple3 tuple3 = aokeToken2.getRecord(cutterUser).sendAsync().get();
                    if (tuple3 != null && tuple3.getSize() != 0) {
                        List<BigInteger> times = (List<BigInteger>) tuple3.getValue1();
                        List<BigInteger> aks = (List<BigInteger>) tuple3.getValue2();
                        List<BigInteger> flags = (List<BigInteger>) tuple3.getValue3();
                        for (int i = 0; i < times.size(); i++) {
                            lists.add(new RecordBean(times.get(i), aks.get(i), flags.get(i)));
                        }
                        if (lists.size() == times.size()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recordAdapter.setList(lists);
                                }
                            });
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pullAoke(cutterUser);
        getRecordList();
    }

    private void initRecyclerView() {
        recordAdapter = new RecordAdapter(XingqiuActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(XingqiuActivity.this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(XingqiuActivity.this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(XingqiuActivity.this, R.drawable.division_while));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(recordAdapter);
    }

    private void getAoke() {
        isCosting = true;
        pro.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransactionReceipt transactionReceipt = aokeToken2.transfer(cutterUser, BigInteger.valueOf((long) notifyNum)).sendAsync().get();
                    List<AokeToken2_sol_AokeToken2.TransferEventResponse> responses = aokeToken2.getTransferEvents(transactionReceipt);
                    if (responses.size() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("成功同步奥克");
                                notifyNum = 0;
                                getRecordList();
                            }
                        });
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    isCosting = false;
                    notifyNum = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pro.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();
    }

    private void pullAoke(String address) {
        if (pro == null || aokeToken2 == null || tv_aoke == null) return;
        pro.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BigInteger cutterAoke = aokeToken2.balanceOf(address).sendAsync().get();
                    if (cutterAoke != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_aoke.setText("奥克：" + cutterAoke);
                                sum = sum + cutterAoke.intValue();
                            }
                        });
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pro.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();
    }

    private void getCutterUser() {
        SharedPreferences sp = getSharedPreferences("address", MODE_PRIVATE);
        String ad = sp.getString("address", "0");
        if (ad.equals("0")) {
            Toast.makeText(XingqiuActivity.this, "当前钱包不存在", Toast.LENGTH_SHORT).show();
            tv_cutterUser.setText("当前用户：无");
        } else {
            cutterUser = ad;
            tv_cutterUser.setText("当前用户：" + cutterUser);
        }
    }


    private void setAK() {
        List<Float> list = new ArrayList<>();
        list.add((float) 3.125);
        list.add((float) 2.125);
        list.add((float) 1.264);
        list.add((float) 1.158);
        list.add((float) 2.264);
        list.add((float) 2.064);
        floatView.setList(list);

        floatView.setOnBallClickListener(new FloatView.ballClickListaner() {
            @Override
            public void itemClick(int position, Number value) {
                sum = sum + (float) value;
                notifyNum = notifyNum + (float) value;
                tv_aoke.setText("奥克：" + sum);
            }
        });
    }

    @OnClick({R.id.xingqiu_lottery2, R.id.xingqiu_lottery, R.id.xingqiu_flush})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xingqiu_lottery:
//                RxView.clicks(bt_lottery)
//                        .throttleFirst(2, TimeUnit.SECONDS)
//                        .subscribe(new Consumer<Object>() {
//                            @Override
//                            public void accept(Object o) throws Exception {
//                                startActivity(new Intent(XingqiuActivity.this, LotteryActivity.class));
//                            }
//                        });
                startActivity(new Intent(XingqiuActivity.this, LotteryActivity.class));
                break;
            case R.id.xingqiu_lottery2:
                startActivity(new Intent(XingqiuActivity.this, NewLotteryActivity.class));
                break;
            case R.id.xingqiu_flush:
                pullAoke(cutterUser);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyNum = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        notifyNum = 0;
    }
}
