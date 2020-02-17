package com.christian.lurienwallet.demo.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

public class BiometricHelper {

    public static boolean isBiometricPropmtEnabled(){
        return (Build.VERSION.SDK_INT>=Build.VERSION_CODES.P);
    }

    public static boolean isSdkVersionSupported(){
        return(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    public static boolean isHardwareSupported(Context context){
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.isHardwareDetected();
    }

    public static boolean isFingerprintAvaliable(Context context){
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.hasEnrolledFingerprints();
    }

    public static boolean isPermisionGranted(Context contxt){
        return ActivityCompat.checkSelfPermission(contxt, Manifest.permission.USE_BIOMETRIC) == PackageManager.PERMISSION_GRANTED;
    }

}
