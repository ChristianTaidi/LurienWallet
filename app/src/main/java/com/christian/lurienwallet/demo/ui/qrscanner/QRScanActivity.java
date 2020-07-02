package com.christian.lurienwallet.demo.ui.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.christian.lurienwallet.demo.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRScanActivity extends AppCompatActivity {

    private QRScannerViewModel QRScannerViewModel;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private String codeToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_assert);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);


        initQR();
    }

    public void initQR(){

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        // creo la camara fuente
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .build();
        cameraView.getHolder().addCallback((new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {


                //Verificar permisos de cámara
                if(ActivityCompat.checkSelfPermission(QRScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    //Verificar version de android
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                            ActivityCompat.requestPermissions(QRScanActivity.this ,new String[]{Manifest.permission.CAMERA},0);
                    }
                    return;
                }
                try{
                    cameraSource.start(cameraView.getHolder());

                }catch (IOException ioe){
                    System.out.println("Error Initializing camera");
                    ioe.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        }));

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

            public void release(){
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                int requestCode = getIntent().getExtras().getInt("requestCode");
                if(barcodes.size()>0){
                    codeToken = barcodes.valueAt(0).displayValue.toString();
                    if(requestCode==420){

                        System.out.println(codeToken);

                        Intent data = new Intent();
                        data.putExtra("codeToken",codeToken);
                        setResult(420,data);
                        finish();
                    }
                    if(requestCode==69){
                        System.out.println(codeToken.toString());
                    }
                }
            }
        });
    }
}