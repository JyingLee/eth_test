package com.jying.eth_test.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jying.eth_test.Contracts.TokenERC20_sol_TokenERC20;
import com.jying.eth_test.R;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代币合约测试
 */
public class TokenActivity extends AppCompatActivity {

    private static final String infura_url = "https://rinkeby.infura.io/v3/f806430ae87349d490a96e06f2ea8519";
    private static final String private_key = "DED5206446CCAF127ACE7DE199B9E629FF26BC7A0DBD173A169A106616B92EFB";//私钥
    private static final String contract_address = "0xc161eabc4df188c2c7624cf8886e8a1f61924e27";//部署的合约地址
    private static final String public_key = "0x5Ef8BE889961Bea484E0b518D6b9D6Aa22aDb32b";//公钥
    private static final String has_eth_pri = "0x2baf6571ae20064242d7472de0531f758567ea3f5a165c89a7d6f8d9e48ba901";
    private static final String has_eth_pub = "0xd439e986f8d7ca72cc1bd53bbe8ca9b0401036e9";
    private static final String new_contractAK = "0xaa797b01f79a3af594669bcae8f95a19c9c9eae2";//新的代币合约
    private Web3j web3;
    private Credentials credentials;
    private int munGasLimit = 500000;
    private BigInteger gasLimit = new BigInteger(String.valueOf(munGasLimit));
    private TokenERC20_sol_TokenERC20 tokenERC20;
    private static final String TAG = "========debug=======";
    @BindView(R.id.token_status)
    TextView tv_status;
    @BindView(R.id.token_pro)
    ProgressBar progressBar;
    @BindView(R.id.token_contract_detail)
    TextView tv_detail;
    @BindView(R.id.token_check_my_money)
    Button bt_myMoney;
    @BindView(R.id.token_check)
    Button bt_check;
    @BindView(R.id.token_my_money)
    TextView tv_myMoney;
    private String cutterUser = "";
    private String this_pri = "";
    @BindView(R.id.token_get)
    Button bt_get;
    @BindView(R.id.token_check_all_money)
    Button bt_check_all;
    @BindView(R.id.token_all_money)
    TextView tv_allmoney;
    @BindView(R.id.token_transaction)
    TextView tv_transaction;
    @BindView(R.id.token_copy_address)
    Button bt_copy;
    @BindView(R.id.token_to)
    EditText et_to;
    @BindView(R.id.token_send)
    Button bt_send;
    @BindView(R.id.token_paste)
    Button bt_paste;
    @BindView(R.id.token_count)
    EditText et_count;
    @BindView(R.id.token_gas_used)
    TextView tv_gasUsed;
    @BindView(R.id.token_cutter)
    TextView tv_cutter;
    @BindView(R.id.token_burn)
    Button bt_burn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        ButterKnife.bind(this);
        SharedPreferences sp = getSharedPreferences("address", Context.MODE_PRIVATE);
        cutterUser = sp.getString("address", "0");
        this_pri = sp.getString("private", "0");
        loadContract();//加载合约

    }

    private void loadContract() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                credentials = Credentials.create(private_key);
                web3 = Web3j.build(new HttpService(infura_url));
                tokenERC20 = TokenERC20_sol_TokenERC20.load(new_contractAK, web3, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
                try {
                    Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
                    Log.e(TAG, "版本号:" + web3ClientVersion.getWeb3ClientVersion());
                    String contractAddtrss = tokenERC20.getContractAddress();//合约地址
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_status.setText("连接合约成功\n" + "版本号："
                                    + web3ClientVersion.getWeb3ClientVersion()
                                    + "\n连接网络："
                                    + infura_url
                                    + "\n" + "合约地址："
                                    + contractAddtrss
                            );
                            progressBar.setVisibility(View.GONE);
                            tv_cutter.setText("当前钱包地址：" + cutterUser);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @OnClick({R.id.token_check, R.id.token_check_my_money, R.id.token_check_all_money, R.id.token_get,
            R.id.token_copy_address, R.id.token_send, R.id.token_paste, R.id.switch_cutter, R.id.switch_eth, R.id.token_burn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.token_check:
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (tokenERC20 != null) {
                            try {
                                String name = tokenERC20.name().sendAsync().get();
                                String symbol = tokenERC20.symbol().sendAsync().get();
                                String total = String.valueOf(tokenERC20.totalSupply().sendAsync().get());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_detail.setText("代币名称：" + name + "\n简称：" + symbol + "\n发行代币总数：" + total);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });

                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.token_check_my_money:
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (tokenERC20 != null) {
                            try {
                                String cutterAK = String.valueOf(tokenERC20.balanceOf(cutterUser).sendAsync().get());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_myMoney.setText("当前用户AK余额：" + cutterAK);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.token_check_all_money:
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (tokenERC20 != null) {
                            try {
                                String cutterAK = String.valueOf(tokenERC20.balanceOf(public_key).sendAsync().get());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_allmoney.setText("总AK余额：" + cutterAK);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.token_get:
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (tokenERC20 != null) {
                            try {
                                TransactionReceipt transactionReceipt = tokenERC20.transfer(cutterUser, BigInteger.valueOf(100)).sendAsync().get();
                                Log.e(TAG, transactionReceipt.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_transaction.setText("消耗的gas:" + transactionReceipt.getGasUsed()
                                                + "\n交易hash:" + transactionReceipt.getTransactionHash()
                                                + "\n区块高度：" + transactionReceipt.getBlockNumber()
                                                + "\n发送方：" + transactionReceipt.getFrom()
                                                + "\n接收方：" + transactionReceipt.getTo());
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(TokenActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();
                break;
            case R.id.token_copy_address:
                ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(cutterUser);
                Toast.makeText(TokenActivity.this, "地址已复制到剪切板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.token_paste:
                et_to.setText(public_key);
                break;
            case R.id.token_send:
                String to = et_to.getText().toString();
                String count = et_count.getText().toString();
                if (!to.isEmpty() && !count.isEmpty()) {
                    transaction(to, count);
                }
                break;
            case R.id.switch_eth:
                this_pri = has_eth_pri;
                cutterUser = has_eth_pub;
                tv_cutter.setText("当前钱包地址：" + cutterUser);
                Toast.makeText(TokenActivity.this, "已切换", Toast.LENGTH_SHORT).show();
                break;
            case R.id.switch_cutter:
                SharedPreferences sp2 = getSharedPreferences("address", Context.MODE_PRIVATE);
                cutterUser = sp2.getString("address", "0");
                this_pri = sp2.getString("private", "0");
                tv_cutter.setText("当前钱包地址：" + cutterUser);
                if (cutterUser.equals("0") || this_pri.equals("0")) {
                    Toast.makeText(TokenActivity.this, "当前钱包获取失败", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(TokenActivity.this, "已切换", Toast.LENGTH_SHORT).show();
                break;
            case R.id.token_burn:
                if (tokenERC20 != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                TransactionReceipt burnRece = tokenERC20.burn(BigInteger.valueOf(10)).sendAsync().get();
                                TokenERC20_sol_TokenERC20.BurnEventResponse burnEventResponse = tokenERC20.getBurnEvents(burnRece).get(0);
                                if (burnEventResponse != null) {
                                    Log.e(TAG, "消耗的gas:" + burnRece.getGasUsed());
                                    runOnUiThread(() -> {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(TokenActivity.this, "已成功销毁10个代币", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                runOnUiThread(() -> {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(TokenActivity.this, "eth余额不足", Toast.LENGTH_SHORT).show();
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                runOnUiThread(() -> {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(TokenActivity.this, "eth余额不足", Toast.LENGTH_SHORT).show();
                                });
                            }
                        }
                    }).start();
                }
                break;
        }
    }

    private Credentials newCredentials;
    private TokenERC20_sol_TokenERC20 newToken;

    private void transaction(String to, String count) {
        new Thread(() -> {
            runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
            newCredentials = Credentials.create(this_pri);
            newToken = TokenERC20_sol_TokenERC20.load(new_contractAK, web3, newCredentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
            try {
                TransactionReceipt newRec = newToken.approve(cutterUser, BigInteger.valueOf(Long.parseLong(count))).sendAsync().get();
                String gasused = String.valueOf(newRec.getGasUsed());
                Log.e(TAG, gasused);
                TransactionReceipt tranRec = newToken.transferFrom(cutterUser, to, BigInteger.valueOf(Long.parseLong(count))).sendAsync().get();
                String tranGas = String.valueOf(tranRec.getGasUsed());
                Log.e(TAG, tranGas);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        tv_gasUsed.setText("GAS花费:" + gasused + "+" + tranGas);
                        Toast.makeText(TokenActivity.this, "交易成功", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(TokenActivity.this, "eth余额不足", Toast.LENGTH_SHORT).show();
                        tv_gasUsed.setText("");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(TokenActivity.this, "eth余额不足", Toast.LENGTH_SHORT).show();
                        tv_gasUsed.setText("");
                    }
                });
            }
        }).start();
    }
}


