package com.jying.eth_test.Contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class AokeToken2_sol_AokeToken2 extends Contract {
    private static final String BINARY = "60c0604052600460808190527f416f6b650000000000000000000000000000000000000000000000000000000060a090815261003e9160009190610119565b506040805180820190915260028082527f616b000000000000000000000000000000000000000000000000000000000000602090920191825261008391600191610119565b506002805460ff1916905534801561009a57600080fd5b50604051610c42380380610c428339810160409081528151602080840151838501516003849055336000908152600584529485208490559085018051939590949101926100e992850190610119565b5080516100fd906001906020840190610119565b505060048054600160a060020a03191633179055506101b49050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061015a57805160ff1916838001178555610187565b82800160010185558215610187579182015b8281111561018757825182559160200191906001019061016c565b50610193929150610197565b5090565b6101b191905b80821115610193576000815560010161019d565b90565b610a7f806101c36000396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100b357806318160ddd1461013d57806320ec85d61461016457806323b872dd146101a1578063313ce567146101df57806342966c681461020a578063617fba041461022257806370a082311461032157806379cc67901461034257806395d89b4114610366578063a9059cbb1461037b575b600080fd5b3480156100bf57600080fd5b506100c86103a1565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101025781810151838201526020016100ea565b50505050905090810190601f16801561012f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561014957600080fd5b5061015261042f565b60408051918252519081900360200190f35b34801561017057600080fd5b50610185600160a060020a0360043516610435565b60408051600160a060020a039092168252519081900360200190f35b3480156101ad57600080fd5b506101cb600160a060020a0360043581169060243516604435610450565b604080519115158252519081900360200190f35b3480156101eb57600080fd5b506101f461047f565b6040805160ff9092168252519081900360200190f35b34801561021657600080fd5b506101cb600435610488565b34801561022e57600080fd5b50610243600160a060020a036004351661051a565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b8381101561028b578181015183820152602001610273565b50505050905001848103835286818151815260200191508051906020019060200280838360005b838110156102ca5781810151838201526020016102b2565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156103095781810151838201526020016102f1565b50505050905001965050505050505060405180910390f35b34801561032d57600080fd5b50610152600160a060020a0360043516610775565b34801561034e57600080fd5b506101cb600160a060020a0360043516602435610787565b34801561037257600080fd5b506100c861082c565b34801561038757600080fd5b5061039f600160a060020a0360043516602435610886565b005b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104275780601f106103fc57610100808354040283529160200191610427565b820191906000526020600020905b81548152906001019060200180831161040a57829003601f168201915b505050505081565b60035481565b600660205260009081526040902054600160a060020a031681565b600454600090600160a060020a0316331461046a57600080fd5b6104758484846108ac565b5060019392505050565b60025460ff1681565b600454600090600160a060020a031633146104a257600080fd5b336000908152600560205260409020548211156104be57600080fd5b3360008181526005602090815260409182902080548690039055600380548690039055815185815291517fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59281900390910190a2506001919050565b600160a060020a038116600090815260066020908152604091829020825160608082526001830180548383018190529195869586957f0d014829596cc8af06cec7499ae5268808502748191386719b1d4ebf71f86a1f95939460028201946003909201939192839290830191908301906080840190889080156105bc57602002820191906000526020600020905b8154815260200190600101908083116105a8575b505084810383528681815481526020019150805480156105fb57602002820191906000526020600020905b8154815260200190600101908083116105e7575b5050848103825285818154815260200191508054801561063a57602002820191906000526020600020905b815481526020019060010190808311610626575b5050965050505050505060405180910390a1600160a060020a03841660009081526006602090815260409182902060018101805484518185028101850190955280855290936002830193600390930192909185918301828280156106bd57602002820191906000526020600020905b8154815260200190600101908083116106a9575b505050505092508180548060200260200160405190810160405280929190818152602001828054801561070f57602002820191906000526020600020905b8154815260200190600101908083116106fb575b505050505091508080548060200260200160405190810160405280929190818152602001828054801561076157602002820191906000526020600020905b81548152602001906001019080831161074d575b505050505090509250925092509193909250565b60056020526000908152604090205481565b600454600090600160a060020a031633146107a157600080fd5b600160a060020a0383166000908152600560205260409020548211156107c657600080fd5b600160a060020a03831660008181526005602090815260409182902080548690039055600380548690039055815185815291517fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59281900390910190a250600192915050565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104275780601f106103fc57610100808354040283529160200191610427565b600454600160a060020a0316331461089d57600080fd5b6108a83383836108ac565b5050565b6000600160a060020a03831615156108c357600080fd5b600160a060020a0384166000908152600560205260409020548211156108e857600080fd5b600160a060020a0383166000908152600560205260409020548281011161090e57600080fd5b50600160a060020a038083166000818152600560205260408082208054948816835290822080548681039091559290915280548401905501610952838360016109db565b61095e848360006109db565b82600160a060020a031684600160a060020a03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a3600160a060020a038084166000908152600560205260408082205492871682529020540181146109d557fe5b50505050565b600160a060020a0390921660008181526006602090815260408220805473ffffffffffffffffffffffffffffffffffffffff19169093178355600180840180548083018255908452828420429101556002840180548083018255908452828420019490945560039092018054938401815581522001555600a165627a7a723058209f15ed06f80060e1dd74d2528bf41796085bc0bbf5afda5ada378c33253070920029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_ADPOINTS = "adpoints";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_GETRECORD = "getRecord";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURNFROM = "burnFrom";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TRANSFER = "transfer";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BURN_EVENT = new Event("Burn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RECORD_EVENT = new Event("Record", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    @Deprecated
    protected AokeToken2_sol_AokeToken2(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AokeToken2_sol_AokeToken2(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AokeToken2_sol_AokeToken2(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AokeToken2_sol_AokeToken2(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> adpoints(String param0) {
        final Function function = new Function(FUNC_ADPOINTS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(_from),
                new Address(_to),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> burn(BigInteger _value) {
        final Function function = new Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>> getRecord(String _from) {
        final Function function = new Function(FUNC_GETRECORD, 
                Arrays.<Type>asList(new Address(_from)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>>(
                new Callable<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteCall<BigInteger> balanceOf(String param0) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> burnFrom(String _from, BigInteger _value) {
        final Function function = new Function(
                FUNC_BURNFROM, 
                Arrays.<Type>asList(new Address(_from),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(_to),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<AokeToken2_sol_AokeToken2> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken2_sol_AokeToken2.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AokeToken2_sol_AokeToken2> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken2_sol_AokeToken2.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AokeToken2_sol_AokeToken2> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken2_sol_AokeToken2.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AokeToken2_sol_AokeToken2> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken2_sol_AokeToken2.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<BurnEventResponse> getBurnEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(BURN_EVENT, transactionReceipt);
        ArrayList<BurnEventResponse> responses = new ArrayList<BurnEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            BurnEventResponse typedResponse = new BurnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BurnEventResponse> burnEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BurnEventResponse>() {
            @Override
            public BurnEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(BURN_EVENT, log);
                BurnEventResponse typedResponse = new BurnEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BurnEventResponse> burnEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BURN_EVENT));
        return burnEventObservable(filter);
    }

    public List<RecordEventResponse> getRecordEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(RECORD_EVENT, transactionReceipt);
        ArrayList<RecordEventResponse> responses = new ArrayList<RecordEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RecordEventResponse typedResponse = new RecordEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.time = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ak = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.flag = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RecordEventResponse> recordEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RecordEventResponse>() {
            @Override
            public RecordEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(RECORD_EVENT, log);
                RecordEventResponse typedResponse = new RecordEventResponse();
                typedResponse.log = log;
                typedResponse.time = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ak = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.flag = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RecordEventResponse> recordEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECORD_EVENT));
        return recordEventObservable(filter);
    }

    @Deprecated
    public static AokeToken2_sol_AokeToken2 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AokeToken2_sol_AokeToken2(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AokeToken2_sol_AokeToken2 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AokeToken2_sol_AokeToken2(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AokeToken2_sol_AokeToken2 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AokeToken2_sol_AokeToken2(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AokeToken2_sol_AokeToken2 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AokeToken2_sol_AokeToken2(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class TransferEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger value;
    }

    public static class BurnEventResponse {
        public Log log;

        public String from;

        public BigInteger value;
    }

    public static class RecordEventResponse {
        public Log log;

        public List<BigInteger> time;

        public List<BigInteger> ak;

        public List<BigInteger> flag;
    }
}
