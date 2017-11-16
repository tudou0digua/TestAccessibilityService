package test.cb.com.testaccessibilityservice;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cb.com.testaccessibilityservice.service.AutoSignService;
import test.cb.com.testaccessibilityservice.service.BaseAccessibilityService;

public class MainActivity extends AppCompatActivity {

    private PackageManager mPackageManager;
    private String[] mPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseAccessibilityService.getInstance().init(this);
        mPackageManager = this.getPackageManager();
        mPackages = new String[]{AutoSignService.QIANG_QIANG, AutoSignService.YOU_KU};
    }

    public void goAccess(View view) {
        BaseAccessibilityService.getInstance().goAccess();
    }

    public void goApp(View view) {
        Intent intent = mPackageManager.getLaunchIntentForPackage(AutoSignService.YOU_KU);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void cleanProcess(View view) {
        for (String mPackage : mPackages) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mPackage, null);
            intent.setData(uri);
            startActivity(intent);
        }
    }
}
