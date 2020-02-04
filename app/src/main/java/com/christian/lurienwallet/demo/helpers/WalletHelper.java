package com.christian.lurienwallet.demo.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.IOException;

public class WalletHelper {

    private static String userPwd;

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
}
