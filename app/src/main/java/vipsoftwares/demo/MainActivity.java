package vipsoftwares.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import com.vips.qrcodescanner.ScanQRCode;

public class MainActivity extends AppCompatActivity {

    private SurfaceView srufaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srufaceView = (SurfaceView) findViewById(R.id.surfaceView);

        new ScanQRCode(srufaceView, MainActivity.this, new ScanQRCode.OnScanQRCode() {
            @Override
            public void onScanResult(final String displayValue) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, displayValue, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
