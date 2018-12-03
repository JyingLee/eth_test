package com.jying.eth_test.Contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class Lottery_sol_Lottery extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5061051a806100206000396000f3006080604052600436106100565763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663191dee6f811461005b578063aacc5a17146100cd578063ffdd5cf1146100f4575b600080fd5b34801561006757600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100cb958335600160a060020a03169536956044949193909101919081908401838280828437509497505084359550505060209092013591506101909050565b005b3480156100d957600080fd5b506100e26102a5565b60408051918252519081900360200190f35b34801561010057600080fd5b50610115600160a060020a0360043516610321565b6040518085600160a060020a0316600160a060020a0316815260200180602001848152602001838152602001828103825285818151815260200191508051906020019060200280838360005b83811015610179578181015183820152602001610161565b505050509050019550505050505060405180910390f35b600160a060020a038416600090815260208181526040822080546001810180835591845292829020865191936101cc939101919087019061045d565b5050600160a060020a03841660008181526020818152604080832060018082018054820181556002830180548a01815560039093018054928301815580875285872090920188905594869052935490548251828152938401819052606092840183815285549385018490527f896bf5e75a8c8363d13d9b65d09cd3ad5184908a95ef5b30118822798d87bfd3959294919360808301908490801561028f57602002820191906000526020600020905b81548152602001906001019080831161027b575b505094505050505060405180910390a250505050565b60018054604080514281526c0100000000000000000000000033026020808301919091526034820184905282519182900360540182209385019094556064909206808352905160009391927f9ceb30a43c84efa64d7aaa48f73e7f635abbd27423cd7b4fe41165d7802c459e92908290030190a18091505b5090565b600160a060020a0381166000818152602081815260408083206001810154600282015483518281529485018190526060938501848152600390930180548686018190529697949688968796957f896bf5e75a8c8363d13d9b65d09cd3ad5184908a95ef5b30118822798d87bfd3959493926080830190849080156103c457602002820191906000526020600020905b8154815260200190600101908083116103b0575b505094505050505060405180910390a2600160a060020a0385166000908152602081815260409182902060018101546002820154600390920180548551818602810186019096528086528a95919492939285919083018282801561044757602002820191906000526020600020905b815481526020019060010190808311610433575b5050505050925093509350935093509193509193565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061049e57805160ff19168380011785556104cb565b828001600101855582156104cb579182015b828111156104cb5782518255916020019190600101906104b0565b5061031d926104eb9250905b8082111561031d57600081556001016104d7565b905600a165627a7a723058206c184b3dd3575efc7c1c3a651d5441841513cbe7b8e8dfc3c675db3c22644b770029";

    public static final String FUNC_STARTLOTTERY = "startLottery";

    public static final String FUNC_GETRANDOM = "getRandom";

    public static final String FUNC_GETINFO = "getInfo";

    public static final Event SAVECOMPLETE_EVENT = new Event("saveComplete", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    public static final Event SAVERANDOM_EVENT = new Event("saveRandom", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Lottery_sol_Lottery(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Lottery_sol_Lottery(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Lottery_sol_Lottery(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Lottery_sol_Lottery(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> startLottery(String man, String thing, BigInteger ak, BigInteger number) {
        final Function function = new Function(
                FUNC_STARTLOTTERY, 
                Arrays.<Type>asList(new Address(man),
                new org.web3j.abi.datatypes.Utf8String(thing), 
                new Uint256(ak),
                new Uint256(number)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getRandom() {
        final Function function = new Function(
                FUNC_GETRANDOM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getInfo(String man) {
        final Function function = new Function(
                FUNC_GETINFO, 
                Arrays.<Type>asList(new Address(man)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<SaveCompleteEventResponse> getSaveCompleteEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SAVECOMPLETE_EVENT, transactionReceipt);
        ArrayList<SaveCompleteEventResponse> responses = new ArrayList<SaveCompleteEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SaveCompleteEventResponse typedResponse = new SaveCompleteEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.man = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ak = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.numbers = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SaveCompleteEventResponse> saveCompleteEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SaveCompleteEventResponse>() {
            @Override
            public SaveCompleteEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SAVECOMPLETE_EVENT, log);
                SaveCompleteEventResponse typedResponse = new SaveCompleteEventResponse();
                typedResponse.log = log;
                typedResponse.man = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ak = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.numbers = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SaveCompleteEventResponse> saveCompleteEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SAVECOMPLETE_EVENT));
        return saveCompleteEventObservable(filter);
    }

    public List<SaveRandomEventResponse> getSaveRandomEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SAVERANDOM_EVENT, transactionReceipt);
        ArrayList<SaveRandomEventResponse> responses = new ArrayList<SaveRandomEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SaveRandomEventResponse typedResponse = new SaveRandomEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SaveRandomEventResponse> saveRandomEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SaveRandomEventResponse>() {
            @Override
            public SaveRandomEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SAVERANDOM_EVENT, log);
                SaveRandomEventResponse typedResponse = new SaveRandomEventResponse();
                typedResponse.log = log;
                typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SaveRandomEventResponse> saveRandomEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SAVERANDOM_EVENT));
        return saveRandomEventObservable(filter);
    }

    public static RemoteCall<Lottery_sol_Lottery> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Lottery_sol_Lottery.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Lottery_sol_Lottery> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Lottery_sol_Lottery.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Lottery_sol_Lottery> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Lottery_sol_Lottery.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Lottery_sol_Lottery> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Lottery_sol_Lottery.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static Lottery_sol_Lottery load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lottery_sol_Lottery(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Lottery_sol_Lottery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lottery_sol_Lottery(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Lottery_sol_Lottery load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Lottery_sol_Lottery(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Lottery_sol_Lottery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Lottery_sol_Lottery(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class SaveCompleteEventResponse {
        public Log log;

        public String man;

        public BigInteger time;

        public BigInteger ak;

        public List<BigInteger> numbers;
    }

    public static class SaveRandomEventResponse {
        public Log log;

        public BigInteger number;
    }
}
