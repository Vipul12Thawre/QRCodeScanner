package com.vips.qrcodescanner;

import android.Manifest;
import android.app.Activity;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;


import java.io.IOException;

/**
 * Created by renjith on 2/23/17.
 */

public class ScanQRCode {

    private final OnScanQRCode onScanQRCode;
    private final SurfaceView cameraView;
    private final Activity activity;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private int width, height;

    public ScanQRCode(SurfaceView cameraView, final Activity activity, OnScanQRCode onScanQRCode) {
        this.onScanQRCode = onScanQRCode;
        this.cameraView = cameraView;
        this.activity = activity;
        initScanner();
    }


    private void initScanner() {

        barcodeDetector =
                new BarcodeDetector.Builder(activity)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        getScreenSize();

        cameraSource = new CameraSource
                .Builder(activity, barcodeDetector)
                .setRequestedPreviewSize(width, height)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    Log.d("qr code text ", barcodes.valueAt(0).displayValue + "");
                    onScanQRCode.onScanResult(barcodes.valueAt(0).displayValue);

                }
            }
        });

    }

    private void getScreenSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    public interface OnScanQRCode {
        void onScanResult(String displayValue);
    }
}
