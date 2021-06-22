package com.example.flashlight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggleButton;
    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://i.giphy.com/media/UPm8BqL6igDUPZ29ik/giphy.webp");


        toggleButton = findViewById(R.id.toggleButton);
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(view -> {
            if (hasCameraFlash) {
                if (flashOn) {
                    flashOn = false;
                    toggleButton.setImageResource(R.drawable.power_off);
                    try {
                        flashLightOff();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    flashOn = true;
                    toggleButton.setImageResource(R.drawable.power_on);
                    try {
                        flashLightOn();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void flashLightOn() throws CameraAccessException {

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager !=null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(MainActivity.this, "FlashLight is ON", Toast.LENGTH_SHORT).show();
    }
    private void flashLightOff() throws CameraAccessException {

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager !=null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
        Toast.makeText(MainActivity.this, "FlashLight is OFF, Nice One", Toast.LENGTH_SHORT).show();
    }
}

