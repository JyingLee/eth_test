package com.jying.eth_test.Activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jying.eth_test.Contracts.Data_sol_Data;
import com.jying.eth_test.R;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * hello world测试
 */
public class MainActivity extends AppCompatActivity {

    //    private static final String eth_url = "http://10.0.2.2:7545"; //合约在infura上的URL
    private static final String infura_url = "https://rinkeby.infura.io/v3/f806430ae87349d490a96e06f2ea8519";
    private static final String private_key = "DED5206446CCAF127ACE7DE199B9E629FF26BC7A0DBD173A169A106616B92EFB";//私钥
    private static final String contract_address = "0x9efc557f388b5876109ba9f100a928fff8acd8ff";//部署的合约地址
    private static final String public_key = "0x5Ef8BE889961Bea484E0b518D6b9D6Aa22aDb32b";//公钥
    private static final String has_eth_pri = "0x2baf6571ae20064242d7472de0531f758567ea3f5a165c89a7d6f8d9e48ba901";
    private static final String has_eth_pub = "0xd439e986f8d7ca72cc1bd53bbe8ca9b0401036e9";
    private Web3j web3j;
    private Credentials credentials;
    private int munGasLimit = 21000;
    private BigInteger gasLimit = new BigInteger(String.valueOf(munGasLimit));
    private BigInteger gasPriceWei = BigInteger.valueOf(666);
    private static final String TAG = "========debug========";
    private Data_sol_Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        credentials = Credentials.create(private_key);
//        loadContract();//加载合约
        web3j = Web3j.build(new HttpService(infura_url));
        queryTransaction();
    }


    private void loadContract() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                web3j = Web3j.build(new HttpService(infura_url));
//                TransactionManager transactionManager = new ClientTransactionManager(web3, public_key);
                data = Data_sol_Data.load(contract_address, web3j, credentials, ManagedTransaction.GAS_PRICE, gasLimit);
                try {
                    Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
                    Log.e(TAG, web3ClientVersion.getWeb3ClientVersion());//获取连接客户端的版本号
                    TransactionReceipt transactionReceipt = data.setData("hello Jying!", BigInteger.valueOf(80L)).sendAsync().get();
                    Log.e(TAG, transactionReceipt.getBlockHash());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                RemoteCall remoteCall = data.getData();
                try {
                    String result = (String) remoteCall.sendAsync().get();
                    Log.e(TAG, "调用合约结果：" + result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void queryTransaction() {
        new Thread(() -> {
            List<EthBlock.TransactionResult> txs = null;
            try {
                txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
            } catch (IOException e) {
                e.printStackTrace();
            }
            txs.forEach(tx -> {
                EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();

                Log.e(TAG, transaction.getFrom());
            });
        }).start();
    }
}
