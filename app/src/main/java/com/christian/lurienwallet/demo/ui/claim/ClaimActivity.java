package com.christian.lurienwallet.demo.ui.claim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.christian.lurienwallet.demo.R;
import com.christian.lurienwallet.demo.ui.dialog.LoadingDialog;

public class ClaimActivity extends AppCompatActivity {

    private String codeToken;
    private LoadingDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        codeToken = getIntent().getExtras().getString("codeToken");

        loading= new LoadingDialog(this);
        loading.showDialog();


    }
}
