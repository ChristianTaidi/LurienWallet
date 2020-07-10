package contract.model;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class TestLurien extends Contract {
    public static final String BINARY = "6080604052600060045534801561001557600080fd5b5060405161088b38038061088b8339810160408190526100349161033a565b600080546001600160a01b03191633179055805161005990600190602084019061022a565b506003805460018101825560009182526040805160208101918290528390526100949260008051602061086b8339815191529092019161022a565b50600380546001818101835560009290925281546100d79260008051602061086b83398151915290920191906002610100828416150260001901909116046102a8565b506003805460018101825560008290526040805180820190915282815262646e6960e81b602090910190815261011f9260008051602061086b8339815191529092019161022a565b5060038054600181018255600091909152604080518082019091526004808252636e616d6560e01b602090920191825261016a9260008051602061086b83398151915201919061022a565b50600380546001810182556000919091526040805180820190915260158082527f616363657373546573744365727469666963617465000000000000000000000060209092019182526101ce9260008051602061086b83398151915201919061022a565b506003805460018101825560009190915260408051808201909152600e8082526d616363657373546573744d61726b60901b60209092019182526102239260008051602061086b83398151915201919061022a565b5050610404565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061026b57805160ff1916838001178555610298565b82800160010185558215610298579182015b8281111561029857825182559160200191906001019061027d565b506102a492915061031d565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106102e15780548555610298565b8280016001018555821561029857600052602060002091601f016020900482015b82811115610298578254825591600101919060010190610302565b61033791905b808211156102a45760008155600101610323565b90565b60006020828403121561034b578081fd5b81516001600160401b0380821115610361578283fd5b81840185601f820112610372578384fd5b8051925081831115610382578384fd5b604051601f8401601f1916810160200183811182821017156103a2578586fd5b6040528381528184016020018710156103b9578485fd5b6103ca8460208301602085016103d4565b9695505050505050565b60005b838110156103ef5781810151838201526020016103d7565b838111156103fe576000848401525b50505050565b610458806104136000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063379607f51461005157806350a969fc1461007a578063844a8c691461008f578063c8073ab614610099575b600080fd5b61006461005f3660046102d4565b6100ac565b60405161007191906103ff565b60405180910390f35b610082610152565b6040516100719190610419565b610097610158565b005b6100976100a7366004610227565b610192565b600381815481106100b957fe5b600091825260209182902001805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181529350909183018282801561014a5780601f1061011f5761010080835404028352916020019161014a565b820191906000526020600020905b81548152906001019060200180831161012d57829003601f168201915b505050505081565b60045481565b7f7c82e2fb80fb25718600f15c1f3894a2cc333256b7127500a756ec8d646e943260036040516101889190610337565b60405180910390a1565b600254600160a01b900460ff166102065760028054600160a01b6001600160a01b03199091166001600160a01b0384161760ff60a01b19161790556040517fc98ba1d12613485c6f4a30edf54eafb43335a703ad007012da5fd8b06a34702a906101fd9084906103ff565b60405180910390a15b5050565b80356001600160a01b038116811461022157600080fd5b92915050565b60008060408385031215610239578182fd5b823567ffffffffffffffff80821115610250578384fd5b81850186601f820112610261578485fd5b8035925081831115610271578485fd5b6040516020601f8501601f1916820181018481118382101715610292578788fd5b60405284825282850181018910156102a8578687fd5b8481840182840137868186840101528196506102c689828a0161020a565b955050505050509250929050565b6000602082840312156102e5578081fd5b5035919050565b60008151808452815b81811015610311576020818501810151868301820152016102f5565b818111156103225782602083870101525b50601f01601f19169290920160200192915050565b6000602080830181845280855480835260408601915060408482028701019250868552838520855b828110156103f257878503603f190184528154879060018116801561038b57600181146103a8576103dd565b60028204607f16885260ff198216898901526040880192506103dd565b60028204808952858b52898b208b5b828110156103d45781548b82018d01526001909101908b016103b7565b8a018b01945050505b5090955050928501926001918201910161035f565b5092979650505050505050565b60006020825261041260208301846102ec565b9392505050565b9081526020019056fea2646970667358221220ebbedeeaa0cf0a252d7682112de157e6519228693068cea0fa5da2939661a17b64736f6c63430006010033c2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_CLAIMFILLED = "claimFilled";

    public static final String FUNC_CLAIMNUM = "claimNum";

    public static final String FUNC_GETCLAIM = "getClaim";

    public static final Event CLAIMFILLED_EVENT = new Event("ClaimFilled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CLAIMREQUESTED_EVENT = new Event("ClaimRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
    ;

    @Deprecated
    protected TestLurien(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestLurien(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestLurien(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestLurien(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ClaimFilledEventResponse> getClaimFilledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMFILLED_EVENT, transactionReceipt);
        ArrayList<ClaimFilledEventResponse> responses = new ArrayList<ClaimFilledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimFilledEventResponse typedResponse = new ClaimFilledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._filledClaim = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClaimFilledEventResponse> claimFilledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClaimFilledEventResponse>() {
            @Override
            public ClaimFilledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMFILLED_EVENT, log);
                ClaimFilledEventResponse typedResponse = new ClaimFilledEventResponse();
                typedResponse.log = log;
                typedResponse._filledClaim = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClaimFilledEventResponse> claimFilledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMFILLED_EVENT));
        return claimFilledEventFlowable(filter);
    }

    public List<ClaimRequestedEventResponse> getClaimRequestedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMREQUESTED_EVENT, transactionReceipt);
        ArrayList<ClaimRequestedEventResponse> responses = new ArrayList<ClaimRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimRequestedEventResponse typedResponse = new ClaimRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._claimRequested = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClaimRequestedEventResponse> claimRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClaimRequestedEventResponse>() {
            @Override
            public ClaimRequestedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMREQUESTED_EVENT, log);
                ClaimRequestedEventResponse typedResponse = new ClaimRequestedEventResponse();
                typedResponse.log = log;
                typedResponse._claimRequested = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClaimRequestedEventResponse> claimRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMREQUESTED_EVENT));
        return claimRequestedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> claim(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFilled(String _claimJson, String _sender) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMFILLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_claimJson), 
                new org.web3j.abi.datatypes.Address(160, _sender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> claimNum() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLAIMNUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> getClaim() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETCLAIM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TestLurien load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestLurien(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestLurien load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestLurien(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestLurien load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestLurien(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestLurien load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestLurien(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestLurien> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _servicePubK) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_servicePubK)));
        return deployRemoteCall(TestLurien.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TestLurien> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _servicePubK) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_servicePubK)));
        return deployRemoteCall(TestLurien.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLurien> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _servicePubK) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_servicePubK)));
        return deployRemoteCall(TestLurien.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLurien> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _servicePubK) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_servicePubK)));
        return deployRemoteCall(TestLurien.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ClaimFilledEventResponse extends BaseEventResponse {
        public String _filledClaim;
    }

    public static class ClaimRequestedEventResponse extends BaseEventResponse {
        public List<String> _claimRequested;
    }
}
