package com.christian.lurienwallet.demo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.christian.lurienwallet.demo.helpers.FeedReaderContract;
import com.christian.lurienwallet.demo.helpers.FeedReaderDBHelper;
import com.christian.lurienwallet.demo.ui.qrscanner.QRScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.provider.BaseColumns;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Text;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String password = "Lurien";
    private static String walletPath = "main\\res\\wallet";
    private static File wallet = new File(walletPath);
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = fAuth.getCurrentUser();
        if(fAuth.getCurrentUser()==null){
            Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(register);
            finish();
        }else {


            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            FloatingActionButton fab = findViewById(R.id.fab);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToDo Scan QR code
                    Intent scanIntent = new Intent(getApplicationContext(), QRScanActivity.class);
                    startActivity(scanIntent);
                }
            });


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_config, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            Button signOut = findViewById(R.id.sign_out_btn);

            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fAuth.getInstance().signOut();
                    //ToDo go to login activity
                    finish();
                }
            });

            setupBouncyCastle();
            ECKeyPair keyPair = null;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static File getWallet(){
        return wallet;
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
}
