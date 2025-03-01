package com.christian.lurienwallet.demo.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.christian.lurienwallet.demo.MainActivity;
import com.christian.lurienwallet.demo.R;
import com.christian.lurienwallet.demo.helpers.WalletHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        shareViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView pubkText = (TextView) getView().findViewById(R.id.config_pubk);
        TextView privText = (TextView) getView().findViewById(R.id.config_privk);
        try {
            //ToDo decode wallet using user password
            WalletFile walletFile = WalletHelper.getWallet(new File(getActivity().getFilesDir(),"user_wallet"));
            privText.setText(WalletHelper.getKeyPair(walletFile).getPrivateKey().toString());
            pubkText.setText(walletFile.getAddress());
        } catch (CipherException e) {
            e.printStackTrace();
        }


        Switch privkSwitch = (Switch) getView().findViewById(R.id.show_pk);

        privkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(privText.getVisibility()==View.GONE){
                    privText.setVisibility(View.VISIBLE);
                }else{
                    privText.setVisibility(View.GONE);
                }
            }
        });

        Button changePassword = (Button) getView().findViewById(R.id.change_pw);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo change password
            }
        });
    }
}