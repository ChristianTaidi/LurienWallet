package com.christian.lurienwallet.demo.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class WalletHelper {

    private static String userPwd;
    private static ECKeyPair keyPair;
    private static Web3j web3;

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(672290L);

    public static WalletFile getWallet(File file){
        ObjectMapper walletMapper = new ObjectMapper();
        try {
            return walletMapper.readValue(file,WalletFile.class);
        } catch (IOException e) {
            return null;
        }

    }

    public static ECKeyPair getKeyPair(WalletFile wallet) throws CipherException {
        return Wallet.decrypt(userPwd ,wallet);
    }

    public static void setPwd(String pwd){
        userPwd = pwd;
    }

    public static void init(){
        web3 = Web3j.build(new HttpService("http://172.18.2.111:9545"));
    }

    public static Web3j getWeb3(){
        return web3;
    }

    public static Credentials getCredentials(File filesDir) throws IOException, CipherException {
        return WalletUtils.loadCredentials(userPwd,  filesDir);
    }

    public static BigInteger getGasPrice(){
        return GAS_PRICE;
    }

    public static BigInteger getGasLimit(){
        return GAS_LIMIT;
    }
}
