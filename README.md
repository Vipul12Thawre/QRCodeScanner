# QRCodeScanner

Want to integrate QR-Code scanner in _Android_ :kissing_heart: ? Here is a easiest way to scan qr-code. Just use this Android library, library is made on top of *Google Mobile Vision  API*


## Simple Usages

# Params:
1) pass your surfaceview

2) pass Actvity/Fragment context


      new ScanQRCode(surfaceView, context, new ScanQRCode.OnScanQRCode()
      {
           @Override
           public void onScanResult(String result) 
           {
              //Do what ever you want with result text
               
           }
      );
