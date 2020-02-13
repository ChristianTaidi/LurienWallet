package com.christian.lurienwallet.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.christian.lurienwallet.demo.async.ClaimRequestAsync;
import com.christian.lurienwallet.demo.helpers.FeedReaderDBHelper;
import com.christian.lurienwallet.demo.helpers.WalletHelper;
import com.christian.lurienwallet.demo.ui.dialog.LoadingDialog;
import com.christian.lurienwallet.demo.ui.qrscanner.QRScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.security.Provider;
import java.security.Security;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(this);
    private static WalletFile userWallet;

    private LoadingDialog loading;
    private String codeToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading= new LoadingDialog(this);


        WalletHelper.init();

        FirebaseUser user = fAuth.getCurrentUser();
        File walletFile = new File(getFilesDir(),"user_wallet");
        //If there is not a wallet created we proceed to the user register and auto-login
        if(!walletFile.exists()){

            Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(register);
            finish();

            //If there is a wallet but no user logged in we proceed to login using fingerprint and user-password
       }else if(fAuth.getCurrentUser()==null){
            Intent login = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(login);
            finish();

            //If there is a wallet and a logged user proceed to load the main component
        }else{


            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            FloatingActionButton fab = findViewById(R.id.fab);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToDo Scan QR code
                    Intent scanIntent = new Intent(getApplicationContext(), QRScanActivity.class);
                    startActivityForResult(scanIntent,420);

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
                    Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            });

            setupBouncyCastle();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fAuth.signOut();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    public static WalletFile getWallet(){
        return userWallet;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.codeToken = data.getExtras().getString("codeToken");
        loading.showDialog();
        ClaimRequestAsync claimRequest = new ClaimRequestAsync();
        claimRequest.execute(codeToken,this);
        //ToDo async claim request
    }

    public void claimRequest(List<String> claim,int statusCode){
        loading.hideDialog();
        if(statusCode!=0){
            Toast.makeText(MainActivity.this, "Se ha producido un error de conexión UwU", Toast.LENGTH_SHORT).show();
        }else{
            //ToDo show claim request
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("El servicio requiere los siguientes datos");

            builder.setItems(claim.toArray(new String[0]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("Confirmed");
                }
            });

            builder.setCancelable(false);
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("cancelled");
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        System.out.println(claim);
    }
}
