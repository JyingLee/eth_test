package com.jying.eth_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jying.eth_test.Contracts.Data_sol_Data;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;
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
    private Web3j web3;
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
//        deployContract();//部署合约
        loadContract();//加载合约
    }

//    private void deployContract() {
//        try {
//            Data_sol_Data data_sol_data=Data_sol_Data.deploy(web3,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT).sendAsync().get();
//            String contractAddress=data_sol_data.getContractAddress();
//            loadContract(contractAddress);//加载合约
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private void loadContract() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                web3 = Web3j.build(new HttpService(infura_url));
//                TransactionManager transactionManager = new ClientTransactionManager(web3, public_key);
                data = Data_sol_Data.load(contract_address, web3, credentials, ManagedTransaction.GAS_PRICE, gasLimit);
                try {
                    Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
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

}
