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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;
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
public class NewLottery_sol_newLottery extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051602080611746833981016040525160008054600160a060020a03909216600160a060020a03199092169190911790556116f4806100526000396000f3006080604052600436106100ae5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631398e07681146100b3578063168425821461016d5780631e1b2e0f1461019f57806331be23e4146101b757806348261921146101cc57806371cacc9e146102fa57806376b3c8a8146103265780638ff7229314610392578063b702a879146103aa578063c6bec93a146103e2578063ca00aad3146103fa575b600080fd5b3480156100bf57600080fd5b506100cb600435610458565b60405180806020018660028111156100df57fe5b60ff168152602001858152602001848152602001838152602001828103825287818151815260200191508051906020019080838360005b8381101561012e578181015183820152602001610116565b50505050905090810190601f16801561015b5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34801561017957600080fd5b5061018d6004803560248101910135610526565b60408051918252519081900360200190f35b3480156101ab57600080fd5b5061018d60043561059b565b3480156101c357600080fd5b5061018d6105c6565b3480156101d857600080fd5b506101e76004356024356105cd565b6040518080602001878152602001868152602001858152602001806020018060200184810384528a818151815260200191508051906020019080838360005b8381101561023e578181015183820152602001610226565b50505050905090810190601f16801561026b5780820380516001836020036101000a031916815260200191505b508481038352865181528651602091820191808901910280838360005b838110156102a0578181015183820152602001610288565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156102df5781810151838201526020016102c7565b50505050905001995050505050505050505060405180910390f35b34801561030657600080fd5b50610324600480359060248035908101910135604435606435610797565b005b34801561033257600080fd5b5061033e600435610927565b6040518080602001868152602001858152602001848152602001838152602001828103825287818151815260200191508051906020019080838360008381101561012e578181015183820152602001610116565b34801561039e57600080fd5b5061018d600435610a1c565b3480156103b657600080fd5b506103ce600435600160a060020a0360243516610a5b565b604080519115158252519081900360200190f35b3480156103ee57600080fd5b50610324600435610cd2565b34801561040657600080fd5b5060408051602060046024803582810135601f8101859004850286018501909652858552610324958335953695604494919390910191908190840183828082843750949750610e7c9650505050505050565b600180548290811061046657fe5b60009182526020918290206006919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156104ff5780601f106104d4576101008083540402835291602001916104ff565b820191906000526020600020905b8154815290600101906020018083116104e257829003601f168201915b50505050600183015460028401546003850154600490950154939460ff9092169390925085565b600080548190600160a060020a0316331461054057600080fd5b600180549061055190828101611353565b5060018054600019810190811061056457fe5b600091825260209091206006909102019050610581818585611384565b506001908101805460ff1916905554600019019392505050565b60006001828154811015156105ac57fe5b600091825260209091206005600690920201015492915050565b6001545b90565b6060600080600060608060008060018a8154811015156105e957fe5b90600052602060002090600602019150816005018981548110151561060a57fe5b90600052602060002090600702019050806000018160010154826002015483600301548460040185600601858054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106ca5780601f1061069f576101008083540402835291602001916106ca565b820191906000526020600020905b8154815290600101906020018083116106ad57829003601f168201915b505050505095508180548060200260200160405190810160405280929190818152602001828054801561072657602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610708575b505050505091508080548060200260200160405190810160405280929190818152602001828054801561077857602002820191906000526020600020905b815481526020019060010190808311610764575b5050505050905097509750975097509750975050509295509295509295565b60006107a1611402565b600054600160a060020a031633146107b857600080fd5b60018054889081106107c657fe5b6000918252602082206006909102019250600183015460ff1660028111156107ea57fe5b1461083f576040805160e560020a62461bcd02815260206004820152601a60248201527f6c6f747465727920737461747573206e6f742044656661756c74000000000000604482015290519081900360640190fd5b85858080601f016020809104026020016040519081016040528093929190818152602001838380828437505050928452505050602080820185905260408201859052606082018490526005830180546001810180835560009283529183902084518051939486946007909402909201926108bc9284920190611439565b5060208281015160018301556040830151600283015560608301516003830155608083015180516108f392600485019201906114a7565b5060a0820151805161090f916006840191602090910190611515565b50505060029092018054909401909355505050505050565b6060600080600080600060018781548110151561094057fe5b600091825260209091206006909102016001810154909150819060ff16600281111561096857fe5b6002838101546004850154600586015485546040805160206001841615610100026000190190931696909604601f8101839004830287018301909152808652939492939192918791830182828015610a015780601f106109d657610100808354040283529160200191610a01565b820191906000526020600020905b8154815290600101906020018083116109e457829003601f168201915b50505050509450955095509550955095505091939590929450565b600080600183815481101515610a2e57fe5b60009182526020909120600690910201600181015490915060ff166002811115610a5457fe5b9392505050565b6000806000806000806000600189815481101515610a7557fe5b60009182526020909120600690910201955060018087015460ff166002811115610a9b57fe5b14610af0576040805160e560020a62461bcd02815260206004820152601b60248201527f6c6f747465727920737461747573206973206e6f7420476f696e670000000000604482015290519081900360640190fd5b60038601805460010190819055610b08908990610f72565b9450856004015485811515610b1957fe5b604051919006945084908690600019430140907f469c834c9f25610c4a105862f84c8f5f68e7c1a2707261d18f09982a2073253e90600090a4600092505b6005860154831015610cc15760058601805484908110610b7357fe5b90600052602060002090600702019150600082600201541115610cb6575060005b6006820154811015610cb65760068201805482908110610bb057fe5b9060005260206000200154841415610cae5760048201805460018101825560009182526020808320909101805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a038c169081179091558083526005850190915260408083206000194381014090915560028087018054830190558a01805490910190555185928c92917f07a03f47c875dcb211a1c7a149d664bd7643ba3bbc303ac82630ab01d97674979190a460028601541515610ca557610ca5896040805190810160405280601181526020017f72756e206f7574206f66207072697a6573000000000000000000000000000000815250610e7c565b60019650610cc6565b600101610b94565b600190920191610b57565b600096505b50505050505092915050565b600080548190819081908190600160a060020a03163314610cf257600080fd5b6001805487908110610d0057fe5b6000918252602082206006909102019550600186015460ff166002811115610d2457fe5b14610d79576040805160e560020a62461bcd02815260206004820152601a60248201527f6c6f747465727920737461747573206e6f7420446566616c7574000000000000604482015290519081900360640190fd5b6005850154600010610dd5576040805160e560020a62461bcd02815260206004820152601660248201527f6c6f7474657279207072697a6573206973206e756c6c00000000000000000000604482015290519081900360640190fd5b610dde86611189565b6004860155600093508392505b6005850154831015610e655760058501805484908110610e0757fe5b90600052602060002090600702019150600090505b610e2e856004015483600301546112a2565b811015610e5a576006820180546001818101835560009283526020909220018590559384019301610e1c565b600190920191610deb565b505050506001908101805460ff1916909117905550565b60008054600160a060020a03163314610e9457600080fd5b6001805484908110610ea257fe5b6000918252602090912060016006909202018181018054919350600292909160ff1916908302179055507fe1bc3d3f778e55bf4eeee72c1b3db87070f1f4589ce0f27ef30cf742578e855583836040518083815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610f32578181015183820152602001610f1a565b50505050905090810190601f168015610f5f5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1505050565b6040805160388082526060808301909352600092436000190140927c010000000000000000000000000000000000000000000000000000000086029291908590819084602082016107008038833901905050925060009150600090505b602081101561103757858160208110610fe457fe5b1a60f860020a028383806001019450815181101515610fff57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600101610fcf565b5060005b60148110156110b4578060130360080260020a89600160a060020a031681151561106157fe5b0460f860020a02838380600101945081518110151561107c57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535060010161103b565b5060005b6004811015611120578481600481106110cd57fe5b1a60f860020a0283838060010194508151811015156110e857fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053506001016110b8565b826040518082805190602001908083835b602083106111505780518252601f199092019160209182019101611131565b5181516020939093036101000a600019018019909116921691909117905260405192018290039091209c9b505050505050505050505050565b6000806000606060008060006001888154811015156111a457fe5b9060005260206000209060060201955085600501805490509450846040519080825280602002602001820160405280156111e8578160200160208202803883390190505b509350600092505b8483101561123d576005860180548490811061120857fe5b906000526020600020906007020160030154848481518110151561122857fe5b602090810290910101526001909201916111f0565b83600081518110151561124c57fe5b906020019060200201519150600090505b60018451038110156112975761128d82858360010181518110151561127e57fe5b906020019060200201516112b9565b915060010161125d565b509695505050505050565b600081838115156112af57fe5b0490505b92915050565b60008060008060008587116112ce57866112d0565b855b93508587116112df57856112e1565b865b9250600191505b828211611320576112f9848361132a565b9050828181151561130657fe5b06151561131557809450611320565b6001909101906112e8565b5050505092915050565b600082151561133b575060006112b3565b5081810281838281151561134b57fe5b04146112b357fe5b81548183558181111561137f5760060281600602836000526020600020918201910161137f919061154f565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106113c55782800160ff198235161785556113f2565b828001600101855582156113f2579182015b828111156113f25782358255916020019190600101906113d7565b506113fe9291506115a3565b5090565b60c0604051908101604052806060815260200160008152602001600081526020016000815260200160608152602001606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061147a57805160ff19168380011785556113f2565b828001600101855582156113f2579182015b828111156113f257825182559160200191906001019061148c565b828054828255906000526020600020908101928215611509579160200282015b82811115611509578251825473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039091161782556020909201916001909101906114c7565b506113fe9291506115bd565b8280548282559060005260206000209081019282156113f257916020028201828111156113f257825182559160200191906001019061148c565b6105ca91905b808211156113fe57600061156982826115ee565b60018201805460ff19169055600060028301819055600383018190556004830181905561159a906005840190611635565b50600601611555565b6105ca91905b808211156113fe57600081556001016115a9565b6105ca91905b808211156113fe57805473ffffffffffffffffffffffffffffffffffffffff191681556001016115c3565b50805460018160011615610100020316600290046000825580601f106116145750611632565b601f01602090049060005260206000209081019061163291906115a3565b50565b508054600082556007029060005260206000209081019061163291906105ca91905b808211156113fe57600061166b82826115ee565b60018201600090556002820160009055600382016000905560048201600061169391906116aa565b6116a16006830160006116aa565b50600701611657565b508054600082559060005260206000209081019061163291906115a35600a165627a7a723058206a4863034065a5a799521e91f1dd4a8a104d3f2e846500de2d7682538c8690930029";

    public static final String FUNC_LOTTERIES = "lotteries";

    public static final String FUNC_CREATELOTTERY = "createLottery";

    public static final String FUNC_GETLOTTERYPRIZESLENGTH = "getLotteryPrizesLength";

    public static final String FUNC_GETLOTTERIESLENGTH = "getLotteriesLength";

    public static final String FUNC_GETLOTTERYPRIZEINFO = "getLotteryPrizeInfo";

    public static final String FUNC_ADDLOTTERYPRIZE = "addLotteryPrize";

    public static final String FUNC_GETLOTTERYINFO = "getLotteryInfo";

    public static final String FUNC_GETLOTTERYSTATUS = "getLotteryStatus";

    public static final String FUNC_DRAW = "draw";

    public static final String FUNC_STARTLOTTERY = "startLottery";

    public static final String FUNC_CLOSELOTTERY = "closeLottery";

    public static final Event USERDRAWINFO_EVENT = new Event("UserDrawInfo", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event USERDRAWPRIZE_EVENT = new Event("UserDrawPrize", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event CLOSELOTTERY_EVENT = new Event("CloseLottery", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected NewLottery_sol_newLottery(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NewLottery_sol_newLottery(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NewLottery_sol_newLottery(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NewLottery_sol_newLottery(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>> lotteries(BigInteger param0) {
        final Function function = new Function(FUNC_LOTTERIES, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> createLottery(String _name) {
        final Function function = new Function(
                FUNC_CREATELOTTERY, 
                Arrays.<Type>asList(new Utf8String(_name)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getLotteryPrizesLength(BigInteger _lotteryId) {
        final Function function = new Function(FUNC_GETLOTTERYPRIZESLENGTH, 
                Arrays.<Type>asList(new Uint256(_lotteryId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getLotteriesLength() {
        final Function function = new Function(FUNC_GETLOTTERIESLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple6<String, BigInteger, BigInteger, BigInteger, List<String>, List<BigInteger>>> getLotteryPrizeInfo(BigInteger _lotteryId, BigInteger prizeIndex) {
        final Function function = new Function(FUNC_GETLOTTERYPRIZEINFO, 
                Arrays.<Type>asList(new Uint256(_lotteryId),
                new Uint256(prizeIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<Tuple6<String, BigInteger, BigInteger, BigInteger, List<String>, List<BigInteger>>>(
                new Callable<Tuple6<String, BigInteger, BigInteger, BigInteger, List<String>, List<BigInteger>>>() {
                    @Override
                    public Tuple6<String, BigInteger, BigInteger, BigInteger, List<String>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, BigInteger, BigInteger, BigInteger, List<String>, List<BigInteger>>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                convertToNative((List<Address>) results.get(4).getValue()), 
                                convertToNative((List<Uint256>) results.get(5).getValue()));
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addLotteryPrize(BigInteger lotteryId, String _name, BigInteger _amount, BigInteger _probability) {
        final Function function = new Function(
                FUNC_ADDLOTTERYPRIZE, 
                Arrays.<Type>asList(new Uint256(lotteryId),
                new Utf8String(_name),
                new Uint256(_amount),
                new Uint256(_probability)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>> getLotteryInfo(BigInteger _lotteryId) {
        final Function function = new Function(FUNC_GETLOTTERYINFO, 
                Arrays.<Type>asList(new Uint256(_lotteryId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getLotteryStatus(BigInteger _lotteryId) {
        final Function function = new Function(FUNC_GETLOTTERYSTATUS, 
                Arrays.<Type>asList(new Uint256(_lotteryId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> draw(BigInteger lotteryId, String sender) {
        final Function function = new Function(
                FUNC_DRAW, 
                Arrays.<Type>asList(new Uint256(lotteryId),
                new Address(sender)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> startLottery(BigInteger lotteryId) {
        final Function function = new Function(
                FUNC_STARTLOTTERY, 
                Arrays.<Type>asList(new Uint256(lotteryId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> closeLottery(BigInteger lotteryId, String reason) {
        final Function function = new Function(
                FUNC_CLOSELOTTERY, 
                Arrays.<Type>asList(new Uint256(lotteryId),
                new Utf8String(reason)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<NewLottery_sol_newLottery> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner)));
        return deployRemoteCall(NewLottery_sol_newLottery.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NewLottery_sol_newLottery> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner)));
        return deployRemoteCall(NewLottery_sol_newLottery.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NewLottery_sol_newLottery> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner)));
        return deployRemoteCall(NewLottery_sol_newLottery.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NewLottery_sol_newLottery> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner)));
        return deployRemoteCall(NewLottery_sol_newLottery.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<UserDrawInfoEventResponse> getUserDrawInfoEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(USERDRAWINFO_EVENT, transactionReceipt);
        ArrayList<UserDrawInfoEventResponse> responses = new ArrayList<UserDrawInfoEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UserDrawInfoEventResponse typedResponse = new UserDrawInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.random = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.res = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UserDrawInfoEventResponse> userDrawInfoEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, UserDrawInfoEventResponse>() {
            @Override
            public UserDrawInfoEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(USERDRAWINFO_EVENT, log);
                UserDrawInfoEventResponse typedResponse = new UserDrawInfoEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.random = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.res = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<UserDrawInfoEventResponse> userDrawInfoEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERDRAWINFO_EVENT));
        return userDrawInfoEventObservable(filter);
    }

    public List<UserDrawPrizeEventResponse> getUserDrawPrizeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(USERDRAWPRIZE_EVENT, transactionReceipt);
        ArrayList<UserDrawPrizeEventResponse> responses = new ArrayList<UserDrawPrizeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UserDrawPrizeEventResponse typedResponse = new UserDrawPrizeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.lotteryId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.prizeId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UserDrawPrizeEventResponse> userDrawPrizeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, UserDrawPrizeEventResponse>() {
            @Override
            public UserDrawPrizeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(USERDRAWPRIZE_EVENT, log);
                UserDrawPrizeEventResponse typedResponse = new UserDrawPrizeEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.lotteryId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.prizeId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<UserDrawPrizeEventResponse> userDrawPrizeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERDRAWPRIZE_EVENT));
        return userDrawPrizeEventObservable(filter);
    }

    public List<CloseLotteryEventResponse> getCloseLotteryEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSELOTTERY_EVENT, transactionReceipt);
        ArrayList<CloseLotteryEventResponse> responses = new ArrayList<CloseLotteryEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CloseLotteryEventResponse typedResponse = new CloseLotteryEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._lotteryId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CloseLotteryEventResponse> closeLotteryEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, CloseLotteryEventResponse>() {
            @Override
            public CloseLotteryEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSELOTTERY_EVENT, log);
                CloseLotteryEventResponse typedResponse = new CloseLotteryEventResponse();
                typedResponse.log = log;
                typedResponse._lotteryId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<CloseLotteryEventResponse> closeLotteryEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSELOTTERY_EVENT));
        return closeLotteryEventObservable(filter);
    }

    @Deprecated
    public static NewLottery_sol_newLottery load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NewLottery_sol_newLottery(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NewLottery_sol_newLottery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NewLottery_sol_newLottery(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NewLottery_sol_newLottery load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NewLottery_sol_newLottery(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NewLottery_sol_newLottery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NewLottery_sol_newLottery(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class UserDrawInfoEventResponse {
        public Log log;

        public byte[] hash;

        public BigInteger random;

        public BigInteger res;
    }

    public static class UserDrawPrizeEventResponse {
        public Log log;

        public String sender;

        public BigInteger lotteryId;

        public BigInteger prizeId;
    }

    public static class CloseLotteryEventResponse {
        public Log log;

        public BigInteger _lotteryId;

        public String reason;
    }
}
