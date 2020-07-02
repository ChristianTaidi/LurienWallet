package com.christian.lurienwallet.demo.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.christian.lurienwallet.demo.R;
import com.christian.lurienwallet.demo.helpers.WalletHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static Credentials userCredentials;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        File walletFile = new File(getActivity().getFilesDir(),"user_wallet");

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        ObjectMapper walletMapper = new ObjectMapper();
        try {
            this.userCredentials = WalletHelper.getCredentials(walletFile);
            TextView publickey = root.findViewById(R.id.text_home);
            publickey.setText(this.userCredentials.getEcKeyPair().getPublicKey().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String encoder = Base64.getEncoder().encodeToString(userCredentials.getEcKeyPair().getPublicKey().toByteArray());
                System.out.println(encoder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return root;
    }
}