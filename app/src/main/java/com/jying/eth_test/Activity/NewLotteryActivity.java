package com.jying.eth_test.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jying.eth_test.Adapter.PrizesAdapter;
import com.jying.eth_test.Base.BaseActivity;
import com.jying.eth_test.Bean.PrizeInfo;
import com.jying.eth_test.Contracts.AokeToken2_sol_AokeToken2;
import com.jying.eth_test.Contracts.NewLottery_sol_newLottery;
import com.jying.eth_test.R;
import com.jying.eth_test.Token.ContractManager;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewLotteryActivity extends BaseActivity {
    public static final String TAG = "===debug===";
    private static final String has_eth_pri = "0x2baf6571ae20064242d7472de0531f758567ea3f5a165c89a7d6f8d9e48ba901";
    private static final String has_eth_pub = "0xd439e986f8d7ca72cc1bd53bbe8ca9b0401036e9";
    private static final String infura_url = "https://rinkeby.infura.io/v3/f806430ae87349d490a96e06f2ea8519";
    private static final String private_key = "DED5206446CCAF127ACE7DE199B9E629FF26BC7A0DBD173A169A106616B92EFB";//私钥
    private static final String public_key = "0x5Ef8BE889961Bea484E0b518D6b9D6Aa22aDb32b";//公钥
    private Web3j web3j;
    private static final String contract_address1 = "0xaa797b01f79a3af594669bcae8f95a19c9c9eae2";//新的代币合约地址

    private static final String NEW_LOTTERY_ADDRESS = "0xd0aa191400faaa9ebacf0bbe94cdb5c4e2bc08f0"; //仿迅雷抽奖合约地址
    private static final String ADMIN_TOKEN_CONTRACT = "0x61d0e11e736265f8d29d417403b5a523d6a2fa7e";//超级管理员代币合约地址
    private Credentials credentials;
    private NewLottery_sol_newLottery newLottery;
    private Context context;
    @BindView(R.id.new_lottery_info)
    TextView tv_info;
    @BindView(R.id.new_lottery_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.new_lottery_pro)
    ProgressBar pro;
    @BindView(R.id.new_lottery_getinfo)
    TextView tv_prize;
    private PrizesAdapter prizesAdapter;
    private int[] image = new int[]{
            R.mipmap.icon_huawei_mobile, R.mipmap.icon_xiaomi_bracelet, R.mipmap.icon_qq_sport,
            R.mipmap.icon_gopro_camera, R.mipmap.icon_misfit_flash,
            R.mipmap.icon_qq_sport, R.mipmap.icon_qq_gongzai
    };
    private List<PrizeInfo> prizeInfos = new ArrayList<>();
    private int prizesLen;
    private String cutterUser = "";
    private String cutterPri = "";
    private AokeToken2_sol_AokeToken2 aokeToken2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lottery);
        ButterKnife.bind(this);
        getCutterUser();//获取当前用户
        initRecyclerView();
        context = NewLotteryActivity.this;
        newLottery = ContractManager.getInstance().getNewLottery(private_key);
        aokeToken2 = ContractManager.getInstance().getAokeToken2();
        if (newLottery != null) {
            Toast.makeText(context, "合约加载成功", Toast.LENGTH_SHORT).show();
            loadLottery();
        }
    }

    private void loadLottery() {
        tv_info.setText("");
        tv_info.setText("合约地址：" + NEW_LOTTERY_ADDRESS + "\n当前用户地址：" + cutterUser + "\n");
        getLotteryLength();
        getLotteryInfo(0);
        prizesLen = getPrizesLength(0);
        notifyPrizesInfo(prizesLen);
    }

    private void notifyPrizesInfo(int len) {
        if (len != 0) {
            prizeInfos.clear();
            for (int i = 0; i < len; i++) {
                getPrizesInfo(0, i);
            }
        }
    }

    private void initRecyclerView() {
        prizesAdapter = new PrizesAdapter(NewLotteryActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(prizesAdapter);
    }

    private int getLotteryLength() {
        try {
            BigInteger len = newLottery.getLotteriesLength().sendAsync().get();
            System.out.println(len);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_info.setText(tv_info.getText() + "抽奖项目数量：" + len);
                }
            });
            return len.intValue();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void getLotteryInfo(int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Tuple5 tuple5 = newLottery.getLotteryInfo(BigInteger.valueOf(id)).sendAsync().get();
                    String lotteryName = (String) tuple5.getValue1();//抽奖项目名称
                    BigInteger lotteryTotalNum = (BigInteger) tuple5.getValue3();//抽奖项目奖品总数
                    BigInteger lotteryPrizesSize = (BigInteger) tuple5.getValue5();//抽奖项目的总共奖品数量
                    System.out.println(tuple5.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_info.setText(tv_info.getText() + "\n抽奖项目名称：" + lotteryName + "\n剩余奖品总量：" + lotteryTotalNum + "\n项目奖品总类：" + lotteryPrizesSize);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private int getPrizesLength(int id) {
        try {
            BigInteger len = newLottery.getLotteryPrizesLength(BigInteger.valueOf(id)).sendAsync().get();
            System.out.println(len);
            return len.intValue();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void getPrizesInfo(int id, int prizeID) {
        try {
            Tuple6 tuple6 = newLottery.getLotteryPrizeInfo(BigInteger.valueOf(id), BigInteger.valueOf(prizeID)).sendAsync().get();
            String prizeName = (String) tuple6.getValue1();//奖品名称
            BigInteger prizeTotalNum = (BigInteger) tuple6.getValue2();//奖品总数
            BigInteger prizeRemain = (BigInteger) tuple6.getValue3();//奖品剩余数量
            BigInteger prizeProbability = (BigInteger) tuple6.getValue4();//奖品概率
            double probability = 1.0 / prizeProbability.intValue();
            List<String> winners = (List<String>) tuple6.getValue5();//奖品获得者
            List<BigInteger> winProbArray = (List<BigInteger>) tuple6.getValue6();//奖品中奖数组
            StringBuffer prizeInfo = new StringBuffer(prizeName + "\n总数：" + prizeTotalNum + "\n剩余量：" + prizeRemain + "\n概率：" + probability + "\n");
            StringBuffer winnerInfo = new StringBuffer();
            if (winners.size() == 0) {
                winnerInfo.append("无");
            } else {
                for (String winner : winners) {
                    winnerInfo.append(winner).append("\n");
                }
            }
            prizeInfo.append("中奖数组：");
            for (int i = 0; i < winProbArray.size(); i++) {
                prizeInfo.append(winProbArray.get(i)).append(" ");
            }
            Random random = new Random();
            int index = random.nextInt(7);
            prizeInfos.add(new PrizeInfo(image[index], prizeInfo.toString(), winnerInfo.toString()));
            prizesAdapter.setList(prizeInfos);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.new_lottery_chou})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_lottery_chou:
                pro.setVisibility(View.VISIBLE);
                cost10(cutterUser);
                break;
        }
    }

    public void doingLottery(String from) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransactionReceipt lottery_rece = newLottery.draw(BigInteger.valueOf(0), from).sendAsync().get();
                    List<NewLottery_sol_newLottery.UserDrawPrizeEventResponse> drawList = newLottery.getUserDrawPrizeEvents(lottery_rece);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (drawList.size() != 0) {
                                tv_prize.setText(tv_prize.getText() + "\n交易花费gas：" + lottery_rece.getGasUsed() + "\n抽奖者：" + drawList.get(0).sender + "\n奖品编号：" + drawList.get(0).prizeId);
                            } else {
                                tv_prize.setText(tv_prize.getText() + "\n交易花费gas：" + lottery_rece.getGasUsed() + "\n抽奖者：" + lottery_rece.getFrom() + "\n未中奖");
                            }
                            loadLottery();
                            pro.setVisibility(View.GONE);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void cost10(String from) {
        tv_prize.setText("用户：" + from + "\n正在抽奖...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransactionReceipt transactionReceipt = aokeToken2.transferFrom(from, public_key, BigInteger.valueOf(10)).sendAsync().get();
                    List<AokeToken2_sol_AokeToken2.TransferEventResponse> responses = aokeToken2.getTransferEvents(transactionReceipt);
                    if (responses.size() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewLotteryActivity.this, "交易成功，正在抽奖", Toast.LENGTH_SHORT).show();
                                tv_prize.setText(tv_prize.getText() + "\n用户已消耗10AK...");
                                doingLottery(from);
                            }
                        });
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getCutterUser() {
        SharedPreferences sp = getSharedPreferences("address", MODE_PRIVATE);
        String ad = sp.getString("address", "0");
        String pri = sp.getString("private", "0");
        if (ad.equals("0") || pri.equals("0")) {
            Toast.makeText(NewLotteryActivity.this, "当前钱包不存在", Toast.LENGTH_SHORT).show();
        } else {
            cutterUser = ad;
            cutterPri = pri;
        }
    }
}
