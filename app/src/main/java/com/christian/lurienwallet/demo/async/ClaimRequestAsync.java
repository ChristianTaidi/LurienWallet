package com.christian.lurienwallet.demo.async;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.christian.lurienwallet.demo.MainActivity;
import com.christian.lurienwallet.demo.helpers.WalletHelper;
import com.christian.lurienwallet.demo.smartcontract.TestLurien;
import com.christian.lurienwallet.demo.ui.claim.ClaimActivity;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ClaimRequestAsync extends AsyncTask {

    MainActivity main;

    List<String> claimData;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            String codeToken = (String)objects[0];
            this.main = (MainActivity) objects[1];
            Credentials credentials = WalletHelper.getCredentials(new File(main.getFilesDir(),"user_wallet"));
            TestLurien remoteContract = TestLurien.load(codeToken, WalletHelper.getWeb3(), credentials, WalletHelper.getGasPrice(), WalletHelper.getGasLimit());
            remoteContract.getClaim().send();
            remoteContract.claimRequestedEventFlowable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST)
                    .subscribe(event ->{
                        //ToDo test the event listening and do it in a separate thread after contract is deployed
                        claimData = new ArrayList<>();
                        for(Object claim:event._claimRequested){
                            claimData.add(claim.toString());
                        }
                        claimData.remove(0);


                        for(String claim:claimData){
                            System.out.println(claim);
                        }

                    });

        }  catch (Exception e) {
            return 1;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        this.main.claimRequest(this.claimData, Integer.valueOf((Integer)o));
        this.cancel(true);
    }
}
