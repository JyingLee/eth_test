package com.jying.eth_test.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jying.eth_test.Bean.LotteryBean;
import com.jying.eth_test.Contracts.AokeToken_sol_AokeToken;
import com.jying.eth_test.Contracts.Lottery_sol_Lottery;
import com.jying.eth_test.Net.RetrofitManager;
import com.jying.eth_test.R;
import com.jying.eth_test.View.LotteryView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class LotteryActivity extends RxAppCompatActivity {

    @BindView(R.id.lotteryview)
    LotteryView lotteryView;
    @BindView(R.id.lottery_pro)
    ProgressBar pro;
    @BindView(R.id.lottery_text)
    TextView tv_result;
    @BindView(R.id.lottery_get)
    Button bt_get;
    public static final String TAG = "===debug===";
    private static final String has_eth_pri = "0x2baf6571ae20064242d7472de0531f758567ea3f5a165c89a7d6f8d9e48ba901";
    private static final String has_eth_pub = "0xd439e986f8d7ca72cc1bd53bbe8ca9b0401036e9";
    private static final String contract_address = "0xc161eabc4df188c2c7624cf8886e8a1f61924e27";//第一次erc20部署的代币合约地址
    private static final String infura_url = "https://rinkeby.infura.io/v3/f806430ae87349d490a96e06f2ea8519";
    private static final String private_key = "DED5206446CCAF127ACE7DE199B9E629FF26BC7A0DBD173A169A106616B92EFB";//私钥
    private static final String public_key = "0x5Ef8BE889961Bea484E0b518D6b9D6Aa22aDb32b";//公钥
    private Web3j web3j;
    private static final String contract_address1 = "0xaa797b01f79a3af594669bcae8f95a19c9c9eae2";//新的代币合约地址
    private static final String contract_lottery = "0x0701c1d741da6f249a6dc356b521937e7910eeda";//抽奖合约
    private String cutterUser = "";
    private Credentials newCredentials;
    private AokeToken_sol_AokeToken newToken;
    private Lottery_sol_Lottery lottery_contract;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        ButterKnife.bind(this);
        cutterUser = public_key;
        newCredentials = Credentials.create(private_key);
        web3j = Web3j.build(new HttpService(infura_url));
        newToken = AokeToken_sol_AokeToken.load(contract_address1, web3j, newCredentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);//加载代币合约
        lottery_contract = Lottery_sol_Lottery.load(contract_lottery, web3j, newCredentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);//加载抽奖合约
        if (newToken != null && lottery_contract != null) {
            Toast.makeText(LotteryActivity.this, "合约加载成功", Toast.LENGTH_SHORT).show();
        }
        lotteryView.setOnClickLotteryListener(new LotteryView.OnClickLotteryListener() {
            @Override
            public void onClick() {
                loadContract();//点击抽奖
            }
        });

        lotteryView.setOnResultListener(new LotteryView.OnResultListaner() { //抽奖结果监听
            @Override
            public void onResult(String result, int number) {
                RetrofitManager.getInstance().getApiService().savePrizes(public_key, 1, "1", result)
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                String text = null;
                                try {
                                    text = responseBody.string();
                                    Toast.makeText(LotteryActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(LotteryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                pro.setVisibility(View.GONE);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

//                if (lottery_contract != null) {
//                    pro.setVisibility(View.VISIBLE);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                TransactionReceipt saveRec = lottery_contract.startLottery(cutterUser, result, BigInteger.valueOf(10), BigInteger.valueOf(number)).sendAsync().get();
//                                List<Lottery_sol_Lottery.SaveCompleteEventResponse> saveList = lottery_contract.getSaveCompleteEvents(saveRec);
//                                if (saveList != null) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(LotteryActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                                            tv_result.setText(tv_result.getText() + "\n保存记录成功");
//                                            pro.setVisibility(View.GONE);
//                                            //用户key[string key]，抽奖次数[int count]，总共花费的ak[string amount]，抽奖时间[string time]，奖品列表[list<String>prizes列表]
//                                        }
//                                    });
//                                }
//                            } catch (ExecutionException e) {
//                                e.printStackTrace();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(LotteryActivity.this, "代币余额不足", Toast.LENGTH_SHORT).show();
//                                        pro.setVisibility(View.GONE);
//                                    }
//                                });
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(LotteryActivity.this, "代币余额不足", Toast.LENGTH_SHORT).show();
//                                        pro.setVisibility(View.GONE);
//                                    }
//                                });
//                            }
//                        }
//                    }).start();
//                }
            }
        });
    }


    private void loadContract() {
        pro.setVisibility(View.VISIBLE);
        tv_result.setText("正在获取随机数...");
        if (lottery_contract == null || newToken == null) return;
        new Thread(() -> {
            try {
                TransactionReceipt randomRec = lottery_contract.getRandom().sendAsync().get();
                List<Lottery_sol_Lottery.SaveRandomEventResponse> randomList = lottery_contract.getSaveRandomEvents(randomRec);
                if (randomList.get(0).number != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LotteryActivity.this, "获取随机数：" + randomList.get(0).number, Toast.LENGTH_SHORT).show();
                            tv_result.setText(tv_result.getText() + "\n成功获取随机数：" + randomList.get(0).number + "\n正在转账...");
                        }
                    });

                    TransactionReceipt tranRec = newToken.transfer(has_eth_pub, BigInteger.valueOf(10)).sendAsync().get();
                    runOnUiThread(() -> {
                        pro.setVisibility(View.GONE);
                        tv_result.setText(tv_result.getText() + "\n转账花费的gas:" + tranRec.getGasUsed() + "\n获取随机数花费的gas:" + randomRec.getGasUsed()
                                + "\n" + "发送方:" + tranRec.getFrom() + "\n" + "接收方:" + tranRec.getTo() + "\n随机数:" + randomList.get(0).number);
                        Toast.makeText(LotteryActivity.this, "交易成功", Toast.LENGTH_SHORT).show();
                        lotteryView.startInvalidate(randomList.get(0).number);
                    });
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LotteryActivity.this, "代币余额不足", Toast.LENGTH_SHORT).show();
                    tv_result.setText("");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LotteryActivity.this, "代币余额不足", Toast.LENGTH_SHORT).show();
                    tv_result.setText("");
                });
            }
        }).start();
    }

    @OnClick({R.id.lottery_get})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lottery_get:
                pro.setVisibility(View.VISIBLE);
                RetrofitManager.getInstance().getApiService().getPrizes(public_key)
                        .compose(this.bindToLifecycle())
                        .subscribe(new Observer<List<LotteryBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<LotteryBean> lotteryBeans) {
                                String prizes = "";
                                for (LotteryBean bean : lotteryBeans) {
                                    if (bean.getPrizes() == null) continue;
                                    prizes = prizes + bean.getPrizes() + "  ";
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(LotteryActivity.this);
                                builder.setMessage("用户：" + cutterUser + "\n抽奖次数：" + lotteryBeans.size() + "\n总花费AK：" + lotteryBeans.size() * 10 + "\n奖品列表:" + prizes);
                                builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.show();
                                pro.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(LotteryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                pro.setVisibility(View.GONE);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
//                if (lottery_contract != null) {
//                    pro.setVisibility(View.VISIBLE);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                TransactionReceipt getRec = lottery_contract.getInfo(cutterUser).sendAsync().get();
//                                List<Lottery_sol_Lottery.SaveCompleteEventResponse> getList = lottery_contract.getSaveCompleteEvents(getRec);
//                                if (getList != null) {
//                                    String user = getList.get(0).man;
//                                    String time = String.valueOf(getList.get(0).time);
//                                    String useAK = String.valueOf(getList.get(0).ak);
//                                    List<BigInteger> prizeList = getList.get(0).numbers;
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            AlertDialog.Builder builder = new AlertDialog.Builder(LotteryActivity.this);
//                                            builder.setMessage("用户：" + user + "\n抽奖次数：" + time + "\n总花费AK：" + useAK);
//                                            builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                            builder.setCancelable(false);
//                                            builder.show();
//                                            pro.setVisibility(View.GONE);
//                                        }
//                                    });
//                                }
//                            } catch (ExecutionException e) {
//                                e.printStackTrace();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(LotteryActivity.this, "代币余额不足", Toast.LENGTH_SHORT).show();
//                                        pro.setVisibility(View.GONE);
//                                    }
//                                });
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(LotteryActivity.this, "代币余额不足", Toast.LENGTH_SHORT).show();
//                                        pro.setVisibility(View.GONE);
//                                    }
//                                });
//                            }
//                        }
//                    }).start();
//                } else {
//                    Toast.makeText(LotteryActivity.this, "合约未加载", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }

}
