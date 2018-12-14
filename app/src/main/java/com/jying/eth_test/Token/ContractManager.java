package com.jying.eth_test.Token;

import com.jying.eth_test.Contracts.AokeToken2_sol_AokeToken2;
import com.jying.eth_test.Contracts.NewLottery_sol_newLottery;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class ContractManager {
    private static ContractManager manager;
    public static final String TAG = "===debug===";
    private static final String has_eth_pri = "0x2baf6571ae20064242d7472de0531f758567ea3f5a165c89a7d6f8d9e48ba901";
    private static final String has_eth_pub = "0xd439e986f8d7ca72cc1bd53bbe8ca9b0401036e9";
    private static final String infura_url = "https://rinkeby.infura.io/v3/f806430ae87349d490a96e06f2ea8519";
    private static final String private_key = "DED5206446CCAF127ACE7DE199B9E629FF26BC7A0DBD173A169A106616B92EFB";//私钥
    private static final String public_key = "0x5Ef8BE889961Bea484E0b518D6b9D6Aa22aDb32b";//公钥
    private Web3j web3j;
    private static final String contract_address1 = "0xaa797b01f79a3af594669bcae8f95a19c9c9eae2";//代币合约地址

    private static final String NEW_LOTTERY_ADDRESS = "0xd0aa191400faaa9ebacf0bbe94cdb5c4e2bc08f0"; //仿迅雷抽奖合约地址
    private static final String ADMIN_TOKEN_CONTRACT = "0x9dfc9fc7af4e5064b38f11d968300e13aa70ca34";//超级管理员代币合约地址
    private Credentials credentials;
    private NewLottery_sol_newLottery newLottery;//仿迅雷抽奖合约

    public static ContractManager getInstance() {
        if (manager == null) {
            synchronized (ContractManager.class) {
                if (manager == null) {
                    manager = new ContractManager();
                }
            }
        }
        return manager;
    }

    private ContractManager() {
        web3j = Web3j.build(new HttpService(infura_url));
    }

    public NewLottery_sol_newLottery getNewLottery(String pri) {
        credentials = Credentials.create(pri);
        NewLottery_sol_newLottery newLottery = NewLottery_sol_newLottery.load(NEW_LOTTERY_ADDRESS, web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
        return newLottery;
    }

    public AokeToken2_sol_AokeToken2 getAokeToken2() {
        credentials = Credentials.create(private_key);
        AokeToken2_sol_AokeToken2 aokeToken2 = AokeToken2_sol_AokeToken2.load(ADMIN_TOKEN_CONTRACT, web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
        return aokeToken2;
    }
}
