package com.christian.lurienwallet.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.christian.lurienwallet.demo.helpers.FeedReaderContract;
import com.christian.lurienwallet.demo.helpers.FeedReaderDBHelper;
import com.christian.lurienwallet.demo.helpers.WalletHelper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Tracker;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendRawTransaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.concurrent.CompletableFuture;

import jnr.a64asm.Register;

public class RegisterActivity extends AppCompatActivity {

    EditText name,emailIn,passwordIn,phone;
    Button register,login;
    FirebaseAuth fAuth;
    private static String walletPath = "main\\res\\wallet";
    private static File wallet = new File(walletPath);
    private FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(this);
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(672290L);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailIn = findViewById(R.id.user_email);
        passwordIn = findViewById(R.id.user_password);
        register = findViewById(R.id.reg_button);
        login = findViewById(R.id.login_btn);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        fAuth = FirebaseAuth.getInstance();
        setupBouncyCastle();


//        if(fAuth.getCurrentUser()!=null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class)
//            startActivity(intent);
//            finish();
//        }


        //Login if the user has an account
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signInWithEmailAndPassword(emailIn.getText().toString().trim(),passwordIn.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            File walletFile = new File(getFilesDir(),"user_wallet");
                            //If there is not a wallet created we proceed to the user register and auto-login
                            if(!walletFile.exists()) {
                                generateKeyPair(passwordIn.getText().toString().trim());
                                Toast.makeText(RegisterActivity.this, "Wallet created", Toast.LENGTH_SHORT).show();

                            }
                            Toast.makeText(RegisterActivity.this, "User logged", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(main);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailIn.getText().toString().trim();
                String password = passwordIn.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    emailIn.setError("Email is requires");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    passwordIn.setError("Password required");
                    return;
                }

                if(password.length() < 6){
                    passwordIn.setError("Password must be at least 6 characters long");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //ToDo create wallet credentials and use symetric encryption for user credentials based on user password
                            ECKeyPair keyPair = null;

                                generateKeyPair(task.getResult().getUser().getUid());
                                Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                        }else{
                                Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                    }
                });

            }
        });
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
                return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private void generateKeyPair(String password){
        ECKeyPair keyPair = null;
        try {
            keyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard(password, keyPair);
            Toast.makeText(RegisterActivity.this, "Wallet created", Toast.LENGTH_SHORT).show();
            Credentials credentials = Credentials.create(ECKeyPair.create( new BigInteger("9d8901bc448adf11b5e08831723f7005bbcfaac0694da44853dccd4bdbe5e7f9",16)));
//            RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(BigInteger.valueOf(200),GAS_PRICE,GAS_LIMIT,keyPair.getPublicKey().toString(),BigInteger.valueOf(10));
//            RawTransaction transaction = RawTransaction.createEtherTransaction(BigInteger.valueOf(200),GAS_PRICE,GAS_LIMIT,keyPair.getPublicKey().toString(),BigInteger.valueOf(10));
//            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,credentials);
//            String hexValue = Numeric.toHexString(signedMessage);
//            System.out.println(hexValue);
//            EthSendTransaction ethSendTransaction = WalletHelper.getWeb3().ethSendRawTransaction(hexValue).sendAsync().get();
//            String filename = walletFile.getAddress();
//            EthGetBalance ethGetBalance = WalletHelper.getWeb3()
//                    .ethGetBalance(keyPair.getPublicKey().toString(), DefaultBlockParameterName.LATEST)
//                    .sendAsync()
//                    .get();
//
//            System.out.println(ethGetBalance.getBalance());
            File wallet = new File(getFilesDir(),"user_wallet");
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            objectMapper.writeValue(wallet,walletFile);
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
