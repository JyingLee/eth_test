package com.jying.eth_test.Contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
public class AokeToken_sol_AokeToken extends Contract {
    private static final String BINARY = "60c0604052600460808190527f416f6b650000000000000000000000000000000000000000000000000000000060a090815261003e916000919061011c565b506040805180820190915260028082527f616b00000000000000000000000000000000000000000000000000000000000060209092019182526100839160019161011c565b506002805460ff1916601217905534801561009d57600080fd5b506040516109103803806109108339810160409081528151602080840151838501516003849055336000908152600584529485208490559085018051939590949101926100ec9285019061011c565b50805161010090600190602084019061011c565b505060048054600160a060020a03191633179055506101b79050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061015d57805160ff191683800117855561018a565b8280016001018555821561018a579182015b8281111561018a57825182559160200191906001019061016f565b5061019692915061019a565b5090565b6101b491905b8082111561019657600081556001016101a0565b90565b61074a806101c66000396000f3006080604052600436106100b95763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100be578063095ea7b31461014857806318160ddd1461018057806323b872dd146101a7578063313ce567146101d157806342966c68146101fc57806370a082311461021457806379cc67901461023557806395d89b4114610259578063a9059cbb1461026e578063dd62ed3e14610294578063eed7f827146102bb575b600080fd5b3480156100ca57600080fd5b506100d36102d3565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561010d5781810151838201526020016100f5565b50505050905090810190601f16801561013a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561015457600080fd5b5061016c600160a060020a0360043516602435610361565b604080519115158252519081900360200190f35b34801561018c57600080fd5b5061019561038e565b60408051918252519081900360200190f35b3480156101b357600080fd5b5061016c600160a060020a0360043581169060243516604435610394565b3480156101dd57600080fd5b506101e6610403565b6040805160ff9092168252519081900360200190f35b34801561020857600080fd5b5061016c60043561040c565b34801561022057600080fd5b50610195600160a060020a0360043516610484565b34801561024157600080fd5b5061016c600160a060020a0360043516602435610496565b34801561026557600080fd5b506100d3610567565b34801561027a57600080fd5b50610292600160a060020a03600435166024356105c1565b005b3480156102a057600080fd5b50610195600160a060020a03600435811690602435166105d0565b3480156102c757600080fd5b506101956004356105ed565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103595780601f1061032e57610100808354040283529160200191610359565b820191906000526020600020905b81548152906001019060200180831161033c57829003601f168201915b505050505081565b336000908152600660209081526040808320600160a060020a039590951683529390529190912055600190565b60035481565b600160a060020a03831660009081526006602090815260408083203384529091528120548211156103c457600080fd5b600160a060020a03841660009081526006602090815260408083203384529091529020805483900390556103f9848484610617565b5060019392505050565b60025460ff1681565b3360009081526005602052604081205482111561042857600080fd5b3360008181526005602090815260409182902080548690039055600380548690039055815185815291517fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59281900390910190a2506001919050565b60056020526000908152604090205481565b600160a060020a0382166000908152600560205260408120548211156104bb57600080fd5b600160a060020a03831660009081526006602090815260408083203384529091529020548211156104eb57600080fd5b600160a060020a0383166000818152600560209081526040808320805487900390556006825280832033845282529182902080548690039055600380548690039055815185815291517fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59281900390910190a250600192915050565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103595780601f1061032e57610100808354040283529160200191610359565b6105cc338383610617565b5050565b600660209081526000928352604080842090915290825290205481565b600454600090600160a060020a0316331461060757600080fd5b5060038054820190819055919050565b6000600160a060020a038316151561062e57600080fd5b600160a060020a03841660009081526005602052604090205482111561065357600080fd5b600160a060020a0383166000908152600560205260409020548281011161067957600080fd5b50600160a060020a038083166000818152600560209081526040808320805495891680855282852080548981039091559486905281548801909155815187815291519390950194927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a3600160a060020a0380841660009081526005602052604080822054928716825290205401811461071857fe5b505050505600a165627a7a723058203b204c4b36e4bddf63117adab6288528bbc88bcc65ac395c472be63013bf4c320029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURNFROM = "burnFrom";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_ADDAK = "addAk";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BURN_EVENT = new Event("Burn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AokeToken_sol_AokeToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AokeToken_sol_AokeToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AokeToken_sol_AokeToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AokeToken_sol_AokeToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Address(_spender),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<BigInteger> allowance(String param0, String param1) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new Address(param0),
                new Address(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addAk(BigInteger _value) {
        final Function function = new Function(
                FUNC_ADDAK, 
                Arrays.<Type>asList(new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<AokeToken_sol_AokeToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken_sol_AokeToken.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AokeToken_sol_AokeToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken_sol_AokeToken.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AokeToken_sol_AokeToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken_sol_AokeToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AokeToken_sol_AokeToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(initialSupply),
                new Utf8String(tokenName),
                new Utf8String(tokenSymbol)));
        return deployRemoteCall(AokeToken_sol_AokeToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
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

    @Deprecated
    public static AokeToken_sol_AokeToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AokeToken_sol_AokeToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AokeToken_sol_AokeToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AokeToken_sol_AokeToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AokeToken_sol_AokeToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AokeToken_sol_AokeToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AokeToken_sol_AokeToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AokeToken_sol_AokeToken(contractAddress, web3j, transactionManager, contractGasProvider);
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
}
