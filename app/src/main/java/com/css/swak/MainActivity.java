package com.css.swak;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.css.swak.activity.HomeActivity;
import com.css.swak.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // In order to enable Netlog, a Cronet logging system, enable write permissions.
        // Find more info about Netlog here:
        // https://www.chromium.org/developers/design-documents/network-stack/netlog
        enableWritingPermissionForLogging();

        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onclick(View view) {

        Intent intent;

        switch (view.getId()) {
            case R.id.button:
                intent = new Intent(MainActivity.this, VideoH5Activity.class);
                startActivity(intent);
                break;

            case R.id.button2:
                intent = new Intent(MainActivity.this, SelfActivity.class);
                startActivity(intent);
                break;

            case R.id.tab:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void enableWritingPermissionForLogging() {
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
