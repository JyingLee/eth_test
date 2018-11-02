package com.jying.eth_test.Contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class Data_sol_Data extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040805180820190915260098082527f696e69742064617461000000000000000000000000000000000000000000000060209092019182526100559160009161005b565b506100f6565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061009c57805160ff19168380011785556100c9565b828001600101855582156100c9579182015b828111156100c95782518255916020019190600101906100ae565b506100d59291506100d9565b5090565b6100f391905b808211156100d557600081556001016100df565b90565b610348806101056000396000f3006080604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416633bc5de30811461005b57806347064d6a146100e557806373d4a13a14610133575b600080fd5b34801561006757600080fd5b50610070610148565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100aa578181015183820152602001610092565b50505050905090810190601f1680156100d75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6040805160206004803580820135601f81018490048402850184019095528484526101319436949293602493928401919081908401838280828437509497506101df9650505050505050565b005b34801561013f57600080fd5b506100706101f6565b60008054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156101d45780601f106101a9576101008083540402835291602001916101d4565b820191906000526020600020905b8154815290600101906020018083116101b757829003601f168201915b505050505090505b90565b80516101f2906000906020840190610284565b5050565b6000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561027c5780601f106102515761010080835404028352916020019161027c565b820191906000526020600020905b81548152906001019060200180831161025f57829003601f168201915b505050505081565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106102c557805160ff19168380011785556102f2565b828001600101855582156102f2579182015b828111156102f25782518255916020019190600101906102d7565b506102fe929150610302565b5090565b6101dc91905b808211156102fe57600081556001016103085600a165627a7a723058205bd04ea4e8aa88201e0d55979a12f7c94c39d00d431ebac6a94a6d9da9ccc5590029";

    protected Data_sol_Data(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Data_sol_Data(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> getData() {
        final Function function = new Function("getData", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setData(String str, BigInteger weiValue) {
        final Function function = new Function(
                "setData", 
                Arrays.<Type>asList(new Utf8String(str)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> data() {
        final Function function = new Function("data", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Data_sol_Data> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Data_sol_Data.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Data_sol_Data> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Data_sol_Data.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Data_sol_Data load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Data_sol_Data(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Data_sol_Data load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Data_sol_Data(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
