package com.christian.lurienwallet.demo.ui.home;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.IOException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static WalletFile userWallet;

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
            this.userWallet = walletMapper.readValue(walletFile, WalletFile.class);
            TextView publickey = root.findViewById(R.id.text_home);
            publickey.setText(userWallet.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}