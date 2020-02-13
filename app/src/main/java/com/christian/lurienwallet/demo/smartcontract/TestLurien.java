package com.christian.lurienwallet.demo.smartcontract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
 * <p>Generated with web3j version 4.5.12.
 */
@SuppressWarnings("rawtypes")
public class TestLurien extends Contract {
    public static final String BINARY = "6080604052600060035534801561001557600080fd5b50600080546001600160a01b031916331781556002805460018101825590825260408051602081019182905283905261006092600080516020610695833981519152909201916101b4565b506002805460018101825560009190915260408051808201909152600380825262646e6960e81b60209092019182526100aa926000805160206106958339815191520191906101b4565b5060028054600181018255600091909152604080518082019091526004808252636e616d6560e01b60209092019182526100f5926000805160206106958339815191520191906101b4565b50600280546001810182556000919091526040805180820190915260158082527f61636365737354657374436572746966696361746500000000000000000000006020909201918252610159926000805160206106958339815191520191906101b4565b506002805460018101825560009190915260408051808201909152600e8082526d616363657373546573744d61726b60901b60209092019182526101ae926000805160206106958339815191520191906101b4565b5061024f565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106101f557805160ff1916838001178555610222565b82800160010185558215610222579182015b82811115610222578251825591602001919060010190610207565b5061022e929150610232565b5090565b61024c91905b8082111561022e5760008155600101610238565b90565b6104378061025e6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063379607f51461005157806350a969fc1461007a578063844a8c691461008f578063c8073ab614610099575b600080fd5b61006461005f3660046102b3565b6100ac565b60405161007191906103de565b60405180910390f35b610082610152565b60405161007191906103f8565b610097610158565b005b6100976100a7366004610206565b610192565b600281815481106100b957fe5b600091825260209182902001805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181529350909183018282801561014a5780601f1061011f5761010080835404028352916020019161014a565b820191906000526020600020905b81548152906001019060200180831161012d57829003601f168201915b505050505081565b60035481565b7f7c82e2fb80fb25718600f15c1f3894a2cc333256b7127500a756ec8d646e943260026040516101889190610316565b60405180910390a1565b600180546001600160a01b0319166001600160a01b0383161790556040517fc98ba1d12613485c6f4a30edf54eafb43335a703ad007012da5fd8b06a34702a906101dd9084906103de565b60405180910390a15050565b80356001600160a01b038116811461020057600080fd5b92915050565b60008060408385031215610218578182fd5b823567ffffffffffffffff8082111561022f578384fd5b81850186601f820112610240578485fd5b8035925081831115610250578485fd5b6040516020601f8501601f1916820181018481118382101715610271578788fd5b6040528482528285018101891015610287578687fd5b8481840182840137868186840101528196506102a589828a016101e9565b955050505050509250929050565b6000602082840312156102c4578081fd5b5035919050565b60008151808452815b818110156102f0576020818501810151868301820152016102d4565b818111156103015782602083870101525b50601f01601f19169290920160200192915050565b6000602080830181845280855480835260408601915060408482028701019250868552838520855b828110156103d157878503603f190184528154879060018116801561036a5760018114610387576103bc565b60028204607f16885260ff198216898901526040880192506103bc565b60028204808952858b52898b208b5b828110156103b35781548b82018d01526001909101908b01610396565b8a018b01945050505b5090955050928501926001918201910161033e565b5092979650505050505050565b6000602082526103f160208301846102cb565b9392505050565b9081526020019056fea26469706673582212209bd6f276b6ce4c970ed3698ec0812126aa19c9c70e787c0f651c6be737dab99d64736f6c63430006010033405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace";

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
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMFILLED_EVENT, transactionReceipt);
        ArrayList<ClaimFilledEventResponse> responses = new ArrayList<ClaimFilledEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMFILLED_EVENT, log);
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
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMREQUESTED_EVENT, transactionReceipt);
        ArrayList<ClaimRequestedEventResponse> responses = new ArrayList<ClaimRequestedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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
                EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMREQUESTED_EVENT, log);
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

    public RemoteFunctionCall<TransactionReceipt> claim(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFilled(String _claimJson, String _sender) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMFILLED, 
                Arrays.<Type>asList(new Utf8String(_claimJson),
                new org.web3j.abi.datatypes.Address(160, _sender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimNum() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMNUM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static RemoteCall<TestLurien> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestLurien.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<TestLurien> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestLurien.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestLurien> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestLurien.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestLurien> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestLurien.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ClaimFilledEventResponse extends BaseEventResponse {
        public String _filledClaim;
    }

    public static class ClaimRequestedEventResponse extends BaseEventResponse {
        public List<String> _claimRequested;
    }
}
